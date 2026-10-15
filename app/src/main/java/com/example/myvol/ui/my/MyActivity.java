package com.example.myvol.ui.my;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myvol.R;
import com.example.myvol.adapter.ActivityAdapter;
import com.example.myvol.bean.ActivityBean;
import com.example.myvol.ui.DatabaseHelper;
import com.example.myvol.xiangmu.DetailedActivity;
import com.example.myvol.xiangmu.XiangMu;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的活动
 */
public class MyActivity extends AppCompatActivity {

    ListView listView;
    ActivityAdapter adapter;
    private DatabaseHelper databaseHelper;
    List<ActivityBean> mDataList;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        sp = getSharedPreferences("user", MODE_PRIVATE);
        editor = sp.edit();
        listView = (ListView) findViewById(R.id.list_view);
        mDataList=new ArrayList<>();
        databaseHelper = new DatabaseHelper(MyActivity.this);
        mDataList=databaseHelper.selectMyActivity(sp.getString("username", ""));
        adapter=new ActivityAdapter(MyActivity.this);
        adapter.setData(mDataList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityBean activityBean= mDataList.get(position);
                Intent intent = new Intent(MyActivity.this, DetailedActivity.class);
                intent.putExtra("data", activityBean);
//                Bundle bundle = new Bundle();
//
//                bundle.putString("name", name);
//                bundle.putString("detail", detail);
//                bundle.putInt("imageID", imageID);
//                bundle.putString("date", date);
//                bundle.putString("destination", destination);

//                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
}