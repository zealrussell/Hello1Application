package com.example.hello1application.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hello1application.R;
import com.example.hello1application.medical.BasicInfoActivity;
import com.example.hello1application.medical.medicalRecord.PreMedicalActivity;
import com.example.hello1application.medical.medicalRecord.TestActivity;
import com.example.hello1application.medical.medicalRecord.calculateDosage.DosageReviewActivity;
import com.example.hello1application.medical.medicalRecord.record.PostMedicalRecordActivity;
import com.example.hello1application.medical.quota.reportDetail.ReportDetailCollectionActivity;

public class ManagementFragment extends Fragment implements View.OnClickListener {
    private LinearLayout layoutPersonalInfo,layoutPreOperationRecord,layoutPostOperationRecord,layoutIndexRecord,layoutResourceDB,layoutReviewAblationOfBenignNodules,layoutDrugDosageReview;

    @Nullable
    @Override
    //1. Fragment中onCreate类似于Activity.onCreate，在其中可初始化除了view之外的一切；
    //2. onCreateView是创建该fragment对应的视图，其中需要创建自己的视图并返回给调用者；
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //让碎片加载一个布局
        View view = inflater.inflate(R.layout.fragment_management,container,false);
        initData(view);
        return view;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }
//
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        //aActivity = (AActivity) context;
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        // 取消异步
//    }

    @Override
    public void onClick(View v) {
        initView(v);
    }

    private void initData(View view) {
        layoutPersonalInfo = view.findViewById(R.id.layout_personalInfo);
        layoutPreOperationRecord = view.findViewById(R.id.layout_pre_operation_record);
        layoutPostOperationRecord = view.findViewById(R.id.layout_post_operation_record);
        layoutDrugDosageReview = view.findViewById(R.id.layout_drugDosage_review);
        layoutIndexRecord = view.findViewById(R.id.layout_index_record);
        layoutReviewAblationOfBenignNodules = view.findViewById(R.id.layout_review_ablationOfBenignNodules);
        layoutResourceDB = view.findViewById(R.id.layout_resourceDB);

        layoutPersonalInfo.setOnClickListener(this);
        layoutPreOperationRecord.setOnClickListener(this);
        layoutPostOperationRecord.setOnClickListener(this);
        layoutDrugDosageReview.setOnClickListener(this);
        layoutIndexRecord.setOnClickListener(this);
        layoutReviewAblationOfBenignNodules.setOnClickListener(this);
        layoutResourceDB.setOnClickListener(this);
    }

    private void initView(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.layout_personalInfo:
                intent = new Intent(getActivity(), BasicInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_pre_operation_record:
                intent = new Intent(getActivity(), PreMedicalActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_post_operation_record:
                intent = new Intent(getActivity(), PostMedicalRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_drugDosage_review:
                intent = new Intent(getActivity(), DosageReviewActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_index_record:
                intent = new Intent(getActivity(), ReportDetailCollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_review_ablationOfBenignNodules:
                intent = new Intent(getActivity(), TestActivity.class);
                startActivity(intent);
                break;
//            case R.id.layout_resourceDB:
//                intent = new Intent(getActivity(),BasicInfoActivity.class);
//                startActivity(intent);
//                break;
        }
    }
}
