package com.example.myvol.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myvol.R;
import com.example.myvol.xiangmu.ListAdapter;
import com.example.myvol.xiangmu.XiangMu;

import java.util.ArrayList;
import java.util.List;

public class ActivityViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ActivityViewModel() {
        mText = new MutableLiveData<>();
       // mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
