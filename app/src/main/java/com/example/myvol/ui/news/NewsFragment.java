package com.example.myvol.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myvol.adapter.ActivityAdapter;
import com.example.myvol.adapter.Newsdapter;
import com.example.myvol.bean.ActivityBean;
import com.example.myvol.bean.NewsBean;
import com.example.myvol.bean.TeamBean;
import com.example.myvol.databinding.FragmentMapBinding;
import com.example.myvol.databinding.FragmentNewsBinding;
import com.example.myvol.ui.DatabaseHelper;
import com.example.myvol.ui.news.NewsViewModel;
import com.example.myvol.xiangmu.DetailedActivity;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private FragmentNewsBinding binding;

    Newsdapter adapter;
    private DatabaseHelper databaseHelper;
    List<NewsBean> mDataList;
    String[] title = {"新闻111111", "新闻22222", "新闻33333"};
    String[] content = {"新闻111111新闻111111新闻111111新闻111111新闻111111新闻111111新闻111111新闻111111新闻111111新闻111111新闻111111新闻111111新闻111111新闻111111", "新闻22222新闻22222新闻22222新闻22222新闻22222新闻22222新闻22222新闻22222新闻22222新闻22222新闻22222新闻22222新闻22222新闻22222", "新闻33333新闻33333新闻33333新闻33333新闻33333新闻33333新闻33333新闻33333新闻33333新闻33333新闻33333新闻33333新闻33333新闻33333新闻33333"};
    String[] time = {"2023-4-23", "2023-05-03", "2023-05-07"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewsViewModel NewsViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mDataList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(getActivity());
        if (databaseHelper.selectNews().size()<=0) {
            for (int i = 0; i < title.length; i++) {
                NewsBean newsBean = new NewsBean();
                newsBean.setContent(content[i]);
                newsBean.setTime(time[i]);
                newsBean.setTitle(title[i]);
//            mDataList.add(newsBean);
                databaseHelper.insertNews(newsBean);
            }
        }
        mDataList = databaseHelper.selectNews();
        adapter = new Newsdapter(getActivity());
        adapter.setData(mDataList);
        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsBean activityBean = mDataList.get(position);
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                intent.putExtra("data", activityBean);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
