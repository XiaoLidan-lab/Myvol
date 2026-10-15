package com.example.myvol.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myvol.R;
import com.example.myvol.adapter.ActivityAdapter;
import com.example.myvol.bean.ActivityBean;
import com.example.myvol.databinding.FragmentActivityBinding;
import com.example.myvol.ui.DatabaseHelper;
import com.example.myvol.xiangmu.DetailedActivity;
import com.example.myvol.xiangmu.ListAdapter;
import com.example.myvol.xiangmu.XiangMu;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.chrono.MinguoDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ActivityFragment extends Fragment {
    int[] img = {R.drawable.no1, R.drawable.no2, R.drawable.no3,};
    String[] title = {"关爱老人", "打扫卫生", "整理共享单车"};
    String[] content = {"开展进社区关爱老人的活动", "开展打扫社区卫生的活动", "开展整理共享单车的活动"};
    String[] time = {"2023-4-23", "2023-05-03", "2023-05-07"};
    String[] address = {"四川省成都市郫都区", "四川省眉山市东坡区", "四川省成都市温江区"};
    String[] createTeam = {"John Doe", "Jane Smith", "Tom Johnson"};
    private FragmentActivityBinding binding;
    ListView listView;
    ActivityAdapter adapter;
    private DatabaseHelper databaseHelper;
    List<ActivityBean> mDataList;

    /*ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiangmulist);
        //第二步：绑定控件
        listView = (ListView) findViewById(R.id.list_view);
        //第三步：准备数据
        List<XiangMu> xianglist = new ArrayList<>();
        for (int i = 0; i <2 ; i++) {
            XiangMu no1 = new XiangMu(R.drawable.no1, "菠萝", "¥16.9 元/KG");
            xianglist.add(no1);
            XiangMu no2 = new XiangMu(R.drawable.no2, "芒果","¥29.9 元/kg");
            xianglist.add(no2);
            XiangMu no3 = new XiangMu(R.drawable.no3, "石榴","¥15元/kg");
            xianglist.add(no3);
        }
        //第四步：设计每一个列表项的子布局
        //第五步：定义适配器 控件 -桥梁-数据
        ListAdapter adapter=new ListAdapter(XiangMuList.this,R.layout.xiang_item,xianglist);
        listView.setAdapter(adapter);
    }*/
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ActivityViewModel homeViewModel =
                new ViewModelProvider(this).get(ActivityViewModel.class);

        binding = FragmentActivityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //第三步：准备数据
        listView = (ListView) view.findViewById(R.id.list_view);
        List<XiangMu> xianglist = new ArrayList<>();
        mDataList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(getActivity());
        if (databaseHelper.selectActivity().size() <= 0) {
            for (int i = 0; i < img.length; i++) {
//            XiangMu no1 = new XiangMu(R.drawable.no1, "关爱老人", "开展进社区关爱老人的活动", "2023-4-23","四川省成都市郫都区");
//            xianglist.add(no1);
//            XiangMu no2 = new XiangMu(R.drawable.no2, "打扫卫生","开展打扫社区卫生的活动","2023-5-3","四川省眉山市东坡区");
//            xianglist.add(no2);
//            XiangMu no3 = new XiangMu(R.drawable.no3, "整理共享单车","开展整理共享单车的活动","2023-5-7","四川省成都市温江区");
//            xianglist.add(no3);

                ActivityBean activityBean = new ActivityBean();
                activityBean.setImg(i);
                activityBean.setAddress(address[i]);
                activityBean.setContent(content[i]);
                activityBean.setSumcount(5);
                activityBean.setTitle(title[i]);
                activityBean.setTime(time[i]);
                activityBean.setCreateTeam(createTeam[i]);
                databaseHelper.insertActivity(activityBean);
//            mDataList.add(activityBean);
            }
        }
        mDataList = databaseHelper.selectActivity();
        adapter = new ActivityAdapter(getActivity());
        adapter.setData(mDataList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityBean activityBean = mDataList.get(position);
                Intent intent = new Intent(getContext(), DetailedActivity.class);
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

        //第四步：设计每一个列表项的子布局
        //第五步：定义适配器 控件 -桥梁-数据
//        ListAdapter adapter = new ListAdapter(getContext(), R.layout.xiang_item, xianglist);
//
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                ListView listView = (ListView) parent;
//
//                XiangMu xiangmu = (XiangMu) listView.getItemAtPosition(position);
//
//                String name = xiangmu.getName();
//                String detail = xiangmu.getDetail();
//                int imageID = xiangmu.getImageID();
//                String date = xiangmu.getDate();
//                String destination = xiangmu.getDestination();
//
//                Intent intent = new Intent(getContext(), DetailedActivity.class);
//
//                Bundle bundle = new Bundle();
//
//                bundle.putString("name", name);
//                bundle.putString("detail", detail);
//                bundle.putInt("imageID", imageID);
//                bundle.putString("date", date);
//                bundle.putString("destination", destination);
//
//                intent.putExtras(bundle);
//
//                startActivity(intent);
//
//                //FruitList.this.finish();
//
//            }
//
//        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

