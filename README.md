# Myvol 志愿服务 App

本科实习 / 课程设计 Android 项目，使用 Java、Android 原生界面、SQLite 和百度地图 SDK。

## 功能

- 本地账号注册、登录和退出。
- 志愿活动列表、详情、报名及我的活动。
- 志愿团队列表、详情、加入及我的团队。
- 新闻列表和详情。
- 可选的百度地图、定位和活动地点标记。

账号、报名记录保存在每台设备自己的 SQLite 数据库中，活动、新闻和团队使用内置示例数据。本项目没有服务端，设备之间不会同步数据。

## 开发环境

- JDK 11（Gradle 运行环境；Java 源码目标版本为 8）。
- Gradle 7.4.2，已通过 Wrapper 固定版本。
- Android Gradle Plugin 7.3.1。
- Android SDK Platform 33、Build Tools 30.0.3。
- Android 7.0 / API 24 或以上的设备或模拟器。

环境版本参考 [Android 插件 7.3 官方兼容说明](https://developer.android.com/build/releases/agp-7-3-0-release-notes)。请将 Android Studio 的 Gradle JDK 设为 11；不要直接使用系统默认 JDK 25。Java / Gradle 支持关系见 [Gradle 官方兼容表](https://docs.gradle.org/current/userguide/compatibility.html)。

## 本地运行

1. 在 Android Studio 中打开本目录。
2. 复制 `local.properties.example` 为 `local.properties`，设置自己电脑的 `sdk.dir`；Android Studio 也可以自动生成 SDK 路径。
3. 可选：在 `local.properties` 中填写 `BAIDU_MAP_AK=你的百度地图Android应用Key`。也支持环境变量 `BAIDU_MAP_AK`，本地配置优先。
4. 同步 Gradle，运行 `app`。首次使用先注册本地账号，再登录。

地图 Key 需要与应用包名 `com.example.myvol`、实际安装包签名的 SHA-1 匹配。可使用 `./gradlew signingReport` 查看签名信息。请自行申请 Key，勿把真实 Key 或签名私钥提交到仓库。

未填写地图 Key 时，应用仍可构建和使用活动、团队、新闻、账号功能；地图页显示不可用提示。填写 Key 后需要重新构建。启用地图前会询问用户同意，再由系统请求定位权限；拒绝定位仍可浏览地图。

命令行构建（先确保 `JAVA_HOME` 指向 JDK 11）：

```sh
./gradlew assembleDebug
./gradlew testDebugUnitTest
```

Windows 使用 `gradlew.bat`。首次构建需要联网下载 Gradle 和 Maven 依赖。调试 APK 位于 `app/build/outputs/apk/debug/app-debug.apk`。

## 验证

```sh
# 编译设备测试 APK
./gradlew assembleDebugAndroidTest
# 连接设备或启动模拟器后执行
./gradlew connectedDebugAndroidTest
```

`JoinLookupTest` 使用内存数据库验证活动 / 团队关联 ID 与报名行 ID 不同时，仍能按对象和用户名正确判断报名；不会修改用户的本地数据库。

手工回归流程：首次启动 → 注册 → 登录 → 报名活动 → 再次报名 → 查看我的活动 → 加入团队 → 再次加入 → 查看我的团队 → 退出并重新登录。配置有效 Key 后，额外验证地图授权、拒绝授权及反复切换地图页。

## 本次修复

- 为登录、注册、我的活动、我的团队补齐 Manifest 注册。
- 修正重复报名判断使用的活动 / 团队关联字段，并释放查询游标。
- 移除源码内的地图 Key，通过本地配置或环境变量注入。
- 未配置 Key 时跳过地图 SDK 初始化；初始化失败时保留其他功能。
- 定位仅请求粗略 / 精确位置权限，收到授权结果后才启动。
- 地图资源在 Fragment 视图销毁时释放，取消后台任务并忽略过期结果。
- 地址解析放入后台线程，将坐标转换为百度地图坐标系。
- 清除未使用且引用不存在页面的旧导航模板。
- Gradle 调整为与现有 Android 插件配套的 7.4.2，去除重复 JAR 声明。

## GitHub 归档

以本目录作为仓库根目录，提交源码、资源、Gradle Wrapper 和必要的 `app/libs` SDK 库。`.gitignore` 已排除本地配置、IDE 缓存、构建目录、APK 和签名私钥。实习报告、答辩照片等不需要用于构建，建议单独保管。

`app/libs` 中的百度 SDK 属于第三方软件，使用及分发应遵循其授权条款；项目未擅自为这些文件添加开源许可。

## 项目限制

这是教学演示项目。账号密码目前按原设计明文保存在本地 SQLite 中，请勿使用真实常用密码；用于真实业务前需要另行设计身份认证与服务端。现有百度 SDK 版本较旧，地图功能需要有效 Key、网络及目标设备上的实际验证。地址解析还依赖设备提供的 Geocoder 服务。

## 本次验证结果（2026-10-15）

- JDK 11 + Gradle 7.4.2 下，`assembleDebug`、`testDebugUnitTest`、`assembleDebugAndroidTest`、`lintDebug` 均成功。
- 现有模板单元测试 1 项通过；Android 9 / API 28 模拟器设备测试 3 项通过，包含 2 项新增的活动 / 团队报名查询回归测试。
- 界面操作已验证：新安装进入登录页、注册、登录、活动详情、重复报名不增加人数、我的活动、我的团队、未配置 Key 时的地图提示。
- Lint 无 Error / Fatal，仍有 193 项 Warning，主要包括硬编码界面文字、无障碍描述、图片资源位置、未使用资源和依赖版本提示；本次没有全面重做原有界面。
- 未使用真实地图 Key，因此尚未实测地图鉴权、真实定位和地点标记；需要配置自己的 Key 后在目标设备补验。
