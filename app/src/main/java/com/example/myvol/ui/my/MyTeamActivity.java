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
import com.example.myvol.adapter.TeamAdapter;
import com.example.myvol.bean.ActivityBean;
import com.example.myvol.bean.TeamBean;
import com.example.myvol.ui.DatabaseHelper;
import com.example.myvol.ui.team.TeamDetailActivity;
import com.example.myvol.xiangmu.DetailedActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的团队
 */
public class MyTeamActivity extends AppCompatActivity {
    ListView listView;
    TeamAdapter adapter;
    private DatabaseHelper databaseHelper;
    List<TeamBean> mDataList;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team);
        sp = getSharedPreferences("user", MODE_PRIVATE);
        editor = sp.edit();
        listView = (ListView) findViewById(R.id.list_view);
        mDataList=new ArrayList<>();
        databaseHelper = new DatabaseHelper(MyTeamActivity.this);
        mDataList=databaseHelper.selectMyTeam(sp.getString("username", ""));
        for (int i = 0; i < mDataList.size(); i++) {
            int count = databaseHelper.selectJoinCount(mDataList.get(i).getId() + "").size();
            mDataList.get(i).setCount(count);
        }
        adapter=new TeamAdapter(MyTeamActivity.this);
        adapter.setData(mDataList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TeamBean activityBean= mDataList.get(position);
                Intent intent = new Intent(MyTeamActivity.this, TeamDetailActivity.class);
                intent.putExtra("data", activityBean);
                startActivity(intent);
            }
        });
    }
}