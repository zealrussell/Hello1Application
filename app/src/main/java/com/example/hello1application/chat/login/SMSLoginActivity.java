/*
 * Copyright (c) 2020 WildFireChat. All rights reserved.
 */

package com.example.hello1application.chat.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.hello1application.R;
import com.example.hello1application.chat.login.model.LoginResult;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.wildfire.chat.kit.ChatManagerHolder;
import cn.wildfire.chat.kit.WfcBaseNoToolbarActivity;

public class SMSLoginActivity extends WfcBaseNoToolbarActivity {
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.phoneNumberEditText)
    EditText phoneNumberEditText;
    @BindView(R.id.authCodeEditText)
    EditText authCodeEditText;
    @BindView(R.id.requestAuthCodeButton)
    TextView requestAuthCodeButton;

    private String phoneNumber;

    @Override
    protected int contentLayout() {
        return R.layout.login_activity_sms;
    }

    @Override
    protected void afterViews() {
        setStatusBarTheme(this, false);
        setStatusBarColor(R.color.white);
    }

    @OnTextChanged(value = R.id.phoneNumberEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void inputPhoneNumber(Editable editable) {
        String phone = editable.toString().trim();
        if (phone.length() == 11) {
            requestAuthCodeButton.setEnabled(true);
        } else {
            requestAuthCodeButton.setEnabled(false);
            loginButton.setEnabled(false);
        }
    }

    @OnTextChanged(value = R.id.authCodeEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void inputAuthCode(Editable editable) {
        if (editable.toString().length() > 2) {
            loginButton.setEnabled(true);
        }
    }

    @OnClick(R.id.loginButton)
    void login() {
        String phoneNumber = phoneNumberEditText.getText().toString().trim();
        String authCode = authCodeEditText.getText().toString().trim();

        loginButton.setEnabled(false);
        MaterialDialog dialog = new MaterialDialog.Builder(this)
            .content("?????????...")
            .progress(true, 100)
            .cancelable(false)
            .build();
        dialog.show();


        com.example.hello1application.chat.AppService.Instance().smsLogin(phoneNumber, authCode, new com.example.hello1application.chat.AppService.LoginCallback() {
            @Override
            public void onUiSuccess(LoginResult loginResult) {
                if (isFinishing()) {
                    return;
                }
                dialog.dismiss();
                //????????????token???clientId?????????????????????????????????getClientId?????????clientId??????????????????clientId??????token?????????connect???????????????????????????????????????clientId????????????token????????????????????????
                ChatManagerHolder.gChatManager.connect(loginResult.getUserId(), loginResult.getToken());
                SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
                sp.edit()
                    .putString("id", loginResult.getUserId())
                    .putString("token", loginResult.getToken())
                    .apply();
                Intent intent = new Intent(com.example.hello1application.chat.login.SMSLoginActivity.this, com.example.hello1application.chat.main.MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onUiFailure(int code, String msg) {
                if (isFinishing()) {
                    return;
                }
                Toast.makeText(com.example.hello1application.chat.login.SMSLoginActivity.this, "???????????????" + code + " " + msg, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                loginButton.setEnabled(true);
            }
        });
    }

    private final Handler handler = new Handler();

    @OnClick(R.id.requestAuthCodeButton)
    void requestAuthCode() {
        requestAuthCodeButton.setEnabled(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {
                    requestAuthCodeButton.setEnabled(true);
                }
            }
        }, 60 * 1000);

        Toast.makeText(this, "???????????????...", Toast.LENGTH_SHORT).show();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();

        com.example.hello1application.chat.AppService.Instance().requestAuthCode(phoneNumber, new com.example.hello1application.chat.AppService.SendCodeCallback() {
            @Override
            public void onUiSuccess() {
                Toast.makeText(com.example.hello1application.chat.login.SMSLoginActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUiFailure(int code, String msg) {
                Toast.makeText(com.example.hello1application.chat.login.SMSLoginActivity.this, "?????????????????????: " + code + " " + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
