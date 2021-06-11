package com.example.hello1application.medical;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hello1application.R;
import com.example.hello1application.medical.medicalRecord.record.PostMedicalRecordActivity;

import java.util.ArrayList;
import java.util.List;

public class BasicInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private AlertDialog.Builder builder;
    Button btn_sure_basicInfo;
    private TextView tx_otherConditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);

        //LinearLayout layout_otherConditions = findViewById(R.id.layout_otherConditions);
        tx_otherConditions = findViewById(R.id.tx_otherConditions);
        btn_sure_basicInfo = findViewById(R.id.btn_sure_basicInfo);


        tx_otherConditions.setOnClickListener(this);
        btn_sure_basicInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tx_otherConditions:
                setOtherConditions();
                break;
            case R.id.btn_sure_basicInfo:
                setBtn_sure_basicInfo();
                break;
        }
    }

    /**
     * 其他疾病/情况，多选框
     */
    private void setOtherConditions(){
        tx_otherConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<Integer> choice = new ArrayList<>();

                final String[] items = {"心血管", "骨质疏松", "妊娠期"};
                //默认都未选中
                boolean[] isSelect = {false, false, false, false};

                builder = new AlertDialog.Builder(BasicInfoActivity.this).setIcon(R.mipmap.ic_launcher)
                        .setTitle("多选dialog")
                        .setMultiChoiceItems(items, isSelect, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                if (b) {
                                    choice.add(i);
                                } else {
                                    choice.remove(i);
                                }

                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                StringBuilder str = new StringBuilder();

                                for (int j = 0; j < choice.size(); j++) {
                                    str.append(items[choice.get(j)]+";");
                                }

                                tx_otherConditions.setText(str);

                                Toast.makeText(BasicInfoActivity.this, "你选择了" + str, Toast.LENGTH_LONG).show();

                            }
                        });

                builder.create().show();
            }
        });
    }

    /**
     * 确定按钮
     */
    private void setBtn_sure_basicInfo(){
        btn_sure_basicInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到ButtonActivity演示界面
                Intent intent = new Intent(BasicInfoActivity.this, PostMedicalRecordActivity.class); //从 A类 跳转到 B类
                startActivity(intent);
                Toast.makeText(BasicInfoActivity.this, "返回主界面", Toast.LENGTH_LONG).show();
            }
        });
    }
}
