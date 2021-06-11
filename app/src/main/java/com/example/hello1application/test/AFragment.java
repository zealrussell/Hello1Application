package com.example.hello1application.test;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hello1application.R;

public class AFragment extends Fragment {

    private TextView textView_id1;
    //private Activity aActivity;

    public static AFragment newInstance(String str){
        AFragment aFragment = new AFragment();

        Bundle bundle = new Bundle();
        bundle.putString("snake",str);
        aFragment.setArguments(bundle);
        return aFragment;
    }

    @Nullable
    @Override
    //1. Fragment中onCreate类似于Activity.onCreate，在其中可初始化除了view之外的一切；
    //2. onCreateView是创建该fragment对应的视图，其中需要创建自己的视图并返回给调用者；
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment_a,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView_id1 = view.findViewById(R.id.tv_id1);
        if(getActivity() != null){
            if(getArguments() != null){
                textView_id1.setText(getArguments().getString("snake"));
            }
        }else{

        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //aActivity = (AActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 取消异步
    }
}
