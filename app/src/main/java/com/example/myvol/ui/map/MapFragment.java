package com.example.myvol.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.example.myvol.MainActivity;
import com.example.myvol.R;
import com.example.myvol.bean.ActivityBean;
import com.example.myvol.ui.DatabaseHelper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MapFragment extends Fragment {
    private MapView mapView;
    private BaiduMap baiduMap;
    private LocationClient locationClient;
    private BitmapDescriptor markerIcon;
    private ExecutorService geocodingExecutor;
    private boolean firstLocation = true;

    private final ActivityResultLauncher<String[]> locationPermission = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                if (mapView == null || !isAdded()) return;
                if (hasLocationPermission()) {
                    startLocation();
                } else {
                    Toast.makeText(requireContext(), "未获得定位权限，仍可浏览活动地图", Toast.LENGTH_LONG).show();
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (!MainActivity.mapSdkReady) {
            TextView message = new TextView(requireContext());
            message.setPadding(32, 48, 32, 32);
            message.setText("地图服务尚未启用或暂不可用。其他功能可正常使用。");
            return message;
        }
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = view.findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        firstLocation = true;
        if (hasLocationPermission()) {
            startLocation();
        } else {
            // Android 12+ requires requesting fine and coarse location together.
            locationPermission.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION});
        }
        loadActivityMarkers(view);
        return view;
    }

    private boolean hasLocationPermission() {
        Context context = requireContext();
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocation() {
        if (mapView == null || locationClient != null || !hasLocationPermission()) return;
        try {
            LocationClient.setAgreePrivacy(true);
            locationClient = new LocationClient(requireContext().getApplicationContext());
            locationClient.registerLocationListener(new BDAbstractLocationListener() {
                @Override
                public void onReceiveLocation(BDLocation location) {
                    if (location == null || mapView == null || baiduMap == null) return;
                    int type = location.getLocType();
                    if (type != BDLocation.TypeGpsLocation && type != BDLocation.TypeNetWorkLocation
                            && type != BDLocation.TypeOffLineLocation) return;
                    baiduMap.setMyLocationData(new MyLocationData.Builder()
                            .accuracy(location.getRadius()).direction(location.getDirection())
                            .latitude(location.getLatitude()).longitude(location.getLongitude()).build());
                    if (firstLocation) {
                        firstLocation = false;
                        baiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(
                                new LatLng(location.getLatitude(), location.getLongitude()), 15));
                    }
                }
            });
            LocationClientOption option = new LocationClientOption();
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
            option.setCoorType("bd09ll");
            option.setScanSpan(0);
            option.setIsNeedAddress(true);
            locationClient.setLocOption(option);
            baiduMap.setMyLocationEnabled(true);
            locationClient.start();
        } catch (Exception e) {
            Log.e("MapFragment", "无法启动定位", e);
            if (locationClient != null) locationClient.stop();
            locationClient = null;
            Toast.makeText(requireContext(), "定位暂不可用，可继续浏览地图", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadActivityMarkers(View ownerView) {
        Context context = requireContext().getApplicationContext();
        // Geocoding may use the network; keep it off the main thread.
        geocodingExecutor = Executors.newSingleThreadExecutor();
        geocodingExecutor.execute(() -> {
            List<ActivityBean> activities;
            try (DatabaseHelper database = new DatabaseHelper(context)) {
                activities = database.selectActivity();
            }
            if (!Geocoder.isPresent()) return;
            Geocoder geocoder = new Geocoder(context);
            for (ActivityBean activity : activities) {
                if (Thread.currentThread().isInterrupted()) return;
                try {
                    List<Address> addresses = geocoder.getFromLocationName(activity.getAddress(), 1);
                    if (addresses == null || addresses.isEmpty()) continue;
                    Address address = addresses.get(0);
                    // Android Geocoder uses WGS84; the Baidu map uses BD09.
                    LatLng position = new CoordinateConverter().from(CoordinateConverter.CoordType.GPS)
                            .coord(new LatLng(address.getLatitude(), address.getLongitude())).convert();
                    ownerView.post(() -> {
                        // Discard results belonging to a destroyed or replaced fragment view.
                        if (getView() != ownerView || mapView == null || baiduMap == null) return;
                        if (markerIcon == null) {
                            Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.dingwei);
                            Bitmap scaled = Bitmap.createScaledBitmap(original,
                                    Math.max(1, original.getWidth() / 4), Math.max(1, original.getHeight() / 4), false);
                            markerIcon = BitmapDescriptorFactory.fromBitmap(scaled);
                            if (scaled != original) original.recycle();
                        }
                        baiduMap.addOverlay(new MarkerOptions().position(position)
                                .icon(markerIcon).title(activity.getTitle()));
                    });
                } catch (IOException | IllegalArgumentException e) {
                    Log.w("MapFragment", "活动地址解析失败", e);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) mapView.onResume();
    }

    @Override
    public void onPause() {
        if (mapView != null) mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        if (geocodingExecutor != null) {
            geocodingExecutor.shutdownNow();
            geocodingExecutor = null;
        }
        if (locationClient != null) {
            locationClient.stop();
            locationClient = null;
        }
        if (baiduMap != null) {
            baiduMap.setMyLocationEnabled(false);
            baiduMap.clear();
        }
        if (mapView != null) {
            mapView.onDestroy();
            mapView = null;
        }
        baiduMap = null;
        if (markerIcon != null) {
            markerIcon.recycle();
            markerIcon = null;
        }
        super.onDestroyView();
    }
}
