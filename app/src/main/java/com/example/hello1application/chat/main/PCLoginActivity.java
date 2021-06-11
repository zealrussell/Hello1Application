/*
 * Copyright (c) 2020 WildFireChat. All rights reserved.
 */

package com.example.hello1application.chat.main;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.hello1application.R;
import com.example.hello1application.chat.login.model.PCSession;


import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.kit.WfcBaseActivity;
import cn.wildfire.chat.kit.user.UserViewModel;
import cn.wildfirechat.client.Platform;

public class PCLoginActivity extends WfcBaseActivity {
    private String token;
    private boolean isConfirmPcLogin = false;
    @BindView(R.id.confirmButton)
    Button confirmButton;
    @BindView(R.id.descTextView)
    TextView descTextView;

    private Platform platform;

    @Override
    protected void beforeViews() {
        token = getIntent().getStringExtra("token");
        isConfirmPcLogin = getIntent().getBooleanExtra("isConfirmPcLogin", false);
        int tmp = getIntent().getIntExtra("platform", 0);
        platform = Platform.platform(tmp);
        if (!isConfirmPcLogin && TextUtils.isEmpty(token)) {
            finish();
        }
    }

    @Override
    protected int contentLayout() {
        return R.layout.pc_login_activity;
    }

    @Override
    protected void afterViews() {
        descTextView.setText("允许 " + platform.getPlatFormName() + " 登录");
        if (isConfirmPcLogin) {
            confirmButton.setEnabled(true);
        } else {
            scanPCLogin(token);
        }
    }

    @OnClick(R.id.confirmButton)
    void confirmPCLogin() {
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        confirmPCLogin(token, userViewModel.getUserId());
    }

    @OnClick(R.id.cancelButton)
    void cancelPCLogin() {
        com.example.hello1application.chat.AppService.Instance().cancelPCLogin(token, new com.example.hello1application.chat.AppService.PCLoginCallback() {
            @Override
            public void onUiSuccess() {
                if (isFinishing()) {
                    return;
                }
                finish();
            }

            @Override
            public void onUiFailure(int code, String msg) {
                if (isFinishing()) {
                    return;
                }
                finish();
            }
        });
    }

    private void scanPCLogin(String token) {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
            .content("处理中")
            .progress(true, 100)
            .build();
        dialog.show();

        com.example.hello1application.chat.AppService.Instance().scanPCLogin(token, new com.example.hello1application.chat.AppService.ScanPCCallback() {
            @Override
            public void onUiSuccess(PCSession pcSession) {
                if (isFinishing()) {
                    return;
                }
                dialog.dismiss();
                if (pcSession.getStatus() == 1) {
                    confirmButton.setEnabled(true);
                } else {
                    Toast.makeText(PCLoginActivity.this, "status: " + pcSession.getStatus(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onUiFailure(int code, String msg) {
                if (isFinishing()) {
                    return;
                }
                Toast.makeText(PCLoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void confirmPCLogin(String token, String userId) {
        com.example.hello1application.chat.AppService.Instance().confirmPCLogin(token, userId, new com.example.hello1application.chat.AppService.PCLoginCallback() {
            @Override
            public void onUiSuccess() {
                if (isFinishing()) {
                    return;
                }
                Toast.makeText(PCLoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onUiFailure(int code, String msg) {
                if (isFinishing()) {
                    return;
                }
                Toast.makeText(PCLoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
