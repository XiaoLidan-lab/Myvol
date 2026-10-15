# Myvol 志愿服务 App

基于 Java 与 Android 原生界面构建的志愿服务应用，围绕活动参与、团队协作和资讯浏览提供移动端功能，使用 SQLite 保存本地账号与报名记录，并支持接入百度地图。

## 功能模块

| 模块 | 功能 |
| --- | --- |
| 账号管理 | 本地注册、登录与退出 |
| 志愿活动 | 活动列表、详情查看、报名及个人参与记录 |
| 志愿团队 | 团队浏览、加入团队及个人团队记录 |
| 新闻资讯 | 新闻列表与内容详情 |
| 地图服务 | 可选的地图展示、定位及活动地点标记 |

## 技术栈

- **开发语言**：Java
- **应用界面**：Android 原生组件
- **数据存储**：SQLite
- **地图服务**：百度地图 SDK
- **构建工具**：Gradle Wrapper

## 快速开始

### 开发环境

| 配置项 | 版本 |
| --- | --- |
| JDK | 11（Java 源码目标版本为 8） |
| Gradle | 7.4.2 |
| Android Gradle Plugin | 7.3.1 |
| Android SDK | Platform 33 / Build Tools 30.0.3 |
| 运行设备 | Android 7.0（API 24）及以上 |

### 安装运行

1. 使用 Android Studio 打开项目，将 Gradle JDK 设置为 11。
2. 复制 `local.properties.example` 为 `local.properties`，配置本机的 `sdk.dir`。
3. 同步 Gradle，连接设备或启动模拟器，运行 `app` 模块。
4. 首次使用时注册本地账号并登录，进入活动、团队及资讯页面。

首次构建需要联网下载 Gradle 和 Maven 依赖。

### 地图配置

在 `local.properties` 中设置自己的百度地图 Android 应用 Key，也可通过同名环境变量配置；本地配置优先。

```properties
BAIDU_MAP_AK=your_baidu_map_key
```

Key 需匹配应用包名 `com.example.myvol` 和实际安装包签名的 SHA-1，可通过 `./gradlew signingReport` 查看签名信息。配置完成后重新构建应用，真实 Key 和签名私钥应保留在本地。

未配置 Key 时，其他功能仍可使用；地图启用时会请求用户授权，定位功能按系统权限设置运行。

## 构建与测试

```sh
# 构建调试包
./gradlew assembleDebug

# 运行单元测试
./gradlew testDebugUnitTest

# 编译设备测试包
./gradlew assembleDebugAndroidTest

# 连接设备或启动模拟器后运行设备测试
./gradlew connectedDebugAndroidTest
```

Windows 环境使用 `gradlew.bat`。调试 APK 输出路径：

```text
app/build/outputs/apk/debug/app-debug.apk
```

## 数据说明

活动、团队和新闻使用内置示例数据，账号与报名记录存储在设备本地，各设备之间不进行同步。本地账号密码采用明文存储，请使用独立的测试密码。

项目包含的百度 SDK 属于第三方软件，使用与分发请遵守其授权条款。
