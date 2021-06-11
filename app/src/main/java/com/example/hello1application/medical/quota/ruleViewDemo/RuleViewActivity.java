package com.example.hello1application.medical.quota.ruleViewDemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hello1application.R;

public class RuleViewActivity extends AppCompatActivity {

    private TextView tvhorizontal;
    private RulerView horizontalRullView;
    private VerticalRulerView verticalRullView;
    private TextView tVertical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruler_view);
        initRuleView();
        initVerticalRuleView();
    }


    private void initRuleView() {
        tvhorizontal = (TextView)findViewById(R.id.tv_horizontal);
        horizontalRullView = (RulerView)findViewById(R.id.rulerView_horizontal);

        horizontalRullView.setMin(0);     //设置刻度尺最小值
        horizontalRullView.setMax(300);   //设置刻度尺最大值
        horizontalRullView.setInterval(5);    //设置刻度尺的间距
        horizontalRullView.setNumber(40);     //设置刻度尺第一次显示的位置
        horizontalRullView.setTextOffset(40);     //根据显示的数字自主调节刻度尺数字的左右位置

        horizontalRullView.setRuleListener(new AllRulerCallback() {
            @Override
            public void onRulerSelected(int length, int value) {
                //获得即时显示的数字
                tvhorizontal.setText(String.valueOf(value));
            }
        });
    }

    private void initVerticalRuleView() {
        tVertical = (TextView)findViewById(R.id.tv_vertical);
        verticalRullView = (VerticalRulerView) findViewById(R.id.rulerView_vertical);


        verticalRullView.setMin(100);   //设置刻度尺最小值
        verticalRullView.setMax(250);   //设置刻度尺最大值
        verticalRullView.setNumber(170);    //设置刻度尺第一次显示的位置
        verticalRullView.setInterval(10);   //设置刻度尺的间距
        verticalRullView.setTextOffset(20); //根据显示的数字自主调节刻度尺数字的左右位置
        verticalRullView.setRuleListener(new AllRulerCallback() {
            @Override
            public void onRulerSelected(int length, int value) {
                //获得即时显示的数字
                tVertical.setText(String.valueOf(value));
            }
        });


    }

}
