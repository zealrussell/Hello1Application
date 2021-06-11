package com.example.hello1application.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hello1application.R;

import cn.wildfire.chat.kit.Config;
import cn.wildfirechat.remote.ChatManager;

public class MainActivity extends AppCompatActivity {
    private Button mvbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ChatManager.init(getApplication(), Config.IM_SERVER_HOST);
        ChatManager chatManager = ChatManager.Instance();
        chatManager.startLog();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wfc_main);
        mvbtn = findViewById(R.id.wvbtn);
        OnClick onClick = new OnClick();
        mvbtn.setOnClickListener(onClick);

    }
    class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.wvbtn:
                    Toast.makeText(com.example.hello1application.chat.MainActivity.this,"Toast",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(com.example.hello1application.chat.MainActivity.this,WebViewActivity.class);
                    break;
                case R.id.wv:
                    Toast toastCenter = Toast.makeText(getApplicationContext(),"ToastCenter",Toast.LENGTH_LONG);
                    toastCenter.setGravity(Gravity.LEFT,0,0);
                    toastCenter.show();
            }
        }
    }
}