package com.example.hello1application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hello1application.common.pay.OrderDetailsViewActivity;
import com.example.hello1application.medical.medicalRecord.calculateDosage.DosageReviewActivity;
import com.example.hello1application.medical.quota.reportDetail.RecordIndexDetailActivity;
import com.example.hello1application.common.DialogActivity;
import com.example.hello1application.common.FullscreenActivity;
import com.example.hello1application.common.login.ui.login.LoginActivity;
import com.example.hello1application.medical.medicalRecord.record.PostMedicalRecordActivity;
import com.example.hello1application.medical.quota.reportDetail.IndexDetailsActivity;
import com.example.hello1application.medical.quota.reportDetail.ReportDetailCollectionActivity;
import com.example.hello1application.test.AActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_dialog = findViewById(R.id.btn_dialog);
        Button btn_Entrance = findViewById(R.id.btn_Entrance);
        //Button btn_BasicInfo = findViewById(R.id.btn_BasicInfo);
        Button btn_PickTime = findViewById(R.id.btn_index_details);
        Button btn_bottomActivity = findViewById(R.id.btn_bottomActivity);
        Button btn_fullscreenActivity = findViewById(R.id.btn_fullscreenActivity);
        Button btn_rulerView = findViewById(R.id.btn_loginActivity);
        Button btn_bottomDialog = findViewById(R.id.btn_bottomDialogActivity);
        Button btn_reportDetailActivity = findViewById(R.id.btn_reportDetailActivity);
        Button btn_fragment = findViewById(R.id.btn_fragment);
        Button btn_preMedical = findViewById(R.id.btn_preMedicalActivity);
        Button btn_fragmentA = findViewById(R.id.btn_fragmentAAA);

        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到ButtonActivity演示界面
                Intent intent = new Intent(MainActivity.this, DialogActivity.class); //从 A类 跳转到 B类
                startActivity(intent);
            }
        });
        btn_Entrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到ButtonActivity演示界面
                Intent intent = new Intent(MainActivity.this, PostMedicalRecordActivity.class); //从 A类 跳转到 B类
                startActivity(intent);
            }
        });
        btn_PickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到ButtonActivity演示界面
                Intent intent = new Intent(MainActivity.this, IndexDetailsActivity.class); //从 A类 跳转到 B类
                startActivity(intent);
            }
        });
        btn_bottomActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BottomActivity.class); //从 A类 跳转到 B类
                startActivity(intent);
            }
        });
        btn_fullscreenActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FullscreenActivity.class); //从 A类 跳转到 B类
                startActivity(intent);
            }
        });
        btn_rulerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class); //从 A类 跳转到 B类
                startActivity(intent);
            }
        });
        btn_bottomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecordIndexDetailActivity.class); //从 A类 跳转到 B类
                startActivity(intent);
            }
        });
        btn_reportDetailActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ReportDetailCollectionActivity.class); //从 A类 跳转到 B类
                startActivity(intent);
            }
        });
        btn_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AActivity.class); //从 A类 跳转到 B类
                startActivity(intent);
            }
        });
        btn_preMedical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OrderDetailsViewActivity.class); //从 A类 跳转到 B类
                startActivity(intent);
            }
        });
        btn_fragmentA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DosageReviewActivity.class); //从 A类 跳转到 B类
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_dialog://最普通dialog
                Toast.makeText(MainActivity.this, "dialog入口", Toast.LENGTH_LONG).show();
                break;
        }
    }

}
