package com.example.myvol;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.common.BaiduMapSDKException;
import com.example.myvol.databinding.ActivityMainBinding;
import com.example.myvol.ui.my.LoginActivity;

public class MainActivity extends AppCompatActivity {
    public static boolean mapSdkReady = false;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        if (TextUtils.isEmpty(user.getString("username", ""))) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        mapSdkReady = false;
        SharedPreferences privacy = getSharedPreferences("privacy", MODE_PRIVATE);
        if (BuildConfig.BAIDU_MAP_ENABLED && !privacy.getBoolean("baidu_map_consent", false)) {
            new AlertDialog.Builder(this)
                    .setTitle("启用地图服务")
                    .setMessage("地图功能使用百度地图和定位 SDK。启用后 SDK 将联网提供地图服务；获得定位权限后，可使用位置信息进行定位。是否同意启用？选择暂不启用仍可使用活动、团队和新闻功能。")
                    .setPositiveButton("同意启用", (dialog, which) -> {
                        privacy.edit().putBoolean("baidu_map_consent", true).apply();
                        initializeMap();
                        showMainContent();
                    })
                    .setNegativeButton("暂不启用", (dialog, which) -> showMainContent())
                    .setCancelable(false)
                    .show();
        } else {
            if (BuildConfig.BAIDU_MAP_ENABLED) initializeMap();
            showMainContent();
        }
    }

    private void initializeMap() {
        try {
            SDKInitializer.setAgreePrivacy(getApplicationContext(), true);
            SDKInitializer.initialize(getApplicationContext());
            SDKInitializer.setCoordType(CoordType.BD09LL);
            mapSdkReady = true;
        } catch (BaiduMapSDKException e) {
            Log.e("MainActivity", "地图 SDK 初始化失败", e);
        }
    }

    private void showMainContent() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}
