package com.example.myvol.ui.team;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myvol.R;
import com.example.myvol.adapter.TeamAdapter;
import com.example.myvol.bean.ActivityBean;
import com.example.myvol.bean.TeamBean;
import com.example.myvol.ui.DatabaseHelper;
import com.example.myvol.xiangmu.DetailedActivity;

import java.util.ArrayList;
import java.util.List;

public class TeamFragment extends Fragment {
    int[] img = {R.drawable.mian, R.drawable.yu, R.drawable.nan,};
    String[] title = {"John Doe", "Jane Smith", "Tom Johnson"};
    String[] time = {"2023-4-23", "2023-05-03", "2023-05-07"};
    String[] address = {"123 Main Street, City", "456 Park Avenue, Town", "789 Broadway, Village"};
    int[] suncount = {4, 7, 2};
    int[] activityCount = {1, 2, 3};
    String[] activityName = {"关爱老人", "打扫卫生", "整理共享单车"};
    String[] slogan = {"just do it", "just do it", "just do it"};
    String[] admin = {"张三", "李四", "王五"};

    private DatabaseHelper databaseHelper;
    ListView listView;
    TeamAdapter adapter;
    List<TeamBean> mDataList;
//    private ArrayAdapter<TeamMember> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_team, container, false);
        mDataList = new ArrayList<>();
        listView = root.findViewById(R.id.list_view);
        databaseHelper = new DatabaseHelper(getActivity());
        if (databaseHelper.selectTeam().size() <= 0) {
            for (int i = 0; i < img.length; i++) {
                TeamBean teamBean = new TeamBean();
                teamBean.setTitle(title[i]);
                teamBean.setActivityCount(activityCount[i]);
                teamBean.setAddress(address[i]);
                teamBean.setAdmin(admin[i]);
                teamBean.setActivityName(title[i]);
                teamBean.setImg(i);
                teamBean.setTime(time[i]);
                teamBean.setSumCount(suncount[i]);
                teamBean.setActivityName(activityName[i]);
                teamBean.setSlogan(slogan[i]);
                databaseHelper.insertTeam(teamBean);
//                mDataList.add(teamBean);
            }
        }
        mDataList = databaseHelper.selectTeam();
        for (int i = 0; i < mDataList.size(); i++) {
            int count = databaseHelper.selectJoinCount(mDataList.get(i).getId() + "").size();
            mDataList.get(i).setCount(count);
        }
        adapter = new TeamAdapter(getActivity());
        adapter.setData(mDataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TeamBean activityBean = mDataList.get(position);
                Intent intent = new Intent(getActivity(), TeamDetailActivity.class);
                intent.putExtra("data", activityBean);
                startActivity(intent);
            }
        });
        return root;
    }
}
