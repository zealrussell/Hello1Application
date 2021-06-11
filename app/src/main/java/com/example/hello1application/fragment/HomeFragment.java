package com.example.hello1application.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hello1application.R;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    //1. Fragment中onCreate类似于Activity.onCreate，在其中可初始化除了view之外的一切；
    //2. onCreateView是创建该fragment对应的视图，其中需要创建自己的视图并返回给调用者；
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //让碎片加载一个布局
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        //initData(view);
        return view;
    }
}
