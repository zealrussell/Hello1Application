package com.example.hello1application.test.listenerAndCallback;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hello1application.R;

public class EventActivity1 extends AppCompatActivity {

    private Button mBtnEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event1);

        mBtnEvent = findViewById(R.id.btn_event1);
        // 1.内部类实现监听
        mBtnEvent.setOnClickListener(new OnClickItem()); //如果是EventActivity1本身实现接口，传的就是this

        // 2.匿名内部类实现监听
        mBtnEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.btn_event1:
                        // 点击后的事件
                        break;
                }
            }
        });

        // 3.事件源所在的类实现————EventActivity1去实现View.OnclickListener（我最常用的写法）
        //mBtnEvent.setOnClickListener(EventActivity1.this);
    }

    // 1. 内部类实现监听
    public class OnClickItem implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_event1:
                    // 点击后的事件
                    break;
            }
        }
    }
}
