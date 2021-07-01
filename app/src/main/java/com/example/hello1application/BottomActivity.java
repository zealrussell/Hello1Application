package com.example.hello1application;

import android.content.Intent;
import android.os.Bundle;

import com.example.hello1application.chat.main.SplashActivity;
import com.example.hello1application.fragment.HomeFragment;
import com.example.hello1application.fragment.ManagementFragment;
import com.example.hello1application.fragment.MessageFragment;
import com.example.hello1application.fragment.PersonalCentralFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

public class BottomActivity extends AppCompatActivity{
    private TextView mTextMessage;
    private FragmentManager mFragmentManager;
    private Fragment managementFragment,messageFragment,personalCentralFragment,homeFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //分发事件
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    initFragment(fragmentTransaction);
                    setHomeFragment(fragmentTransaction);
                    return true;

                case R.id.navigation_dashboard:
                    initFragment(fragmentTransaction);
                    setManagementFragment(fragmentTransaction);
                    return true;

                case R.id.navigation_notifications:
                    //initFragment(fragmentTransaction);
                    //setMessageFragment(fragmentTransaction);
                    Intent intent = new Intent(); //从 A类 跳转到 B类
                    intent.setClass(BottomActivity.this, SplashActivity.class);
                    startActivity(intent);
                    return true;

                case R.id.navigation_personalCenter:

                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // 加载默认的fragment
        initDefaultFragment();
    }

    //初始化默认fragment的加载
    private void initDefaultFragment() {

        //获取管理者
        mFragmentManager = getSupportFragmentManager();
        //默认显示第一条
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if(managementFragment!=null){
            fragmentTransaction.hide(managementFragment);
        }
        if (managementFragment == null) {
            managementFragment = new ManagementFragment();
            fragmentTransaction.add(R.id.frameLayout, managementFragment);
        } else {
            fragmentTransaction.show(managementFragment);
        }
        fragmentTransaction.commit();

    }

    // TODO **管家**
    private void setManagementFragment(FragmentTransaction fragmentTransaction){
        if (managementFragment == null) {
            managementFragment = new ManagementFragment();
            fragmentTransaction.add(R.id.frameLayout, managementFragment);
        } else {
            fragmentTransaction.show(managementFragment);
        }
        //提交事件
        fragmentTransaction.commit();
    }

    // TODO **Home**
    private void setHomeFragment(FragmentTransaction fragmentTransaction){
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            fragmentTransaction.add(R.id.frameLayout, homeFragment);
        } else {
            fragmentTransaction.show(homeFragment);
        }
        //提交事件
        fragmentTransaction.commit();
    }

    // TODO **消息**
    private void setMessageFragment(FragmentTransaction fragmentTransaction){
        if (messageFragment == null) {
            messageFragment = new MessageFragment();
            fragmentTransaction.add(R.id.frameLayout, messageFragment);
        } else {
            fragmentTransaction.show(messageFragment);
        }
        //提交事件
        fragmentTransaction.commit();
    }

    // TODO **个人中心**
    private void setPersonalCentralFragment(FragmentTransaction fragmentTransaction){
        if (personalCentralFragment == null) {
            personalCentralFragment = new PersonalCentralFragment();
            fragmentTransaction.add(R.id.frameLayout, personalCentralFragment);
        } else {
            fragmentTransaction.show(personalCentralFragment);
        }
        //提交事件
        fragmentTransaction.commit();
    }

    private void initFragment(FragmentTransaction fragmentTransaction){
        //如果当前对象不等于null的话，那就隐藏他，不然会造成页面叠加现象
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (managementFragment != null) {
            fragmentTransaction.hide(managementFragment);
        }
        if (personalCentralFragment != null) {
            fragmentTransaction.hide(personalCentralFragment);
        }
        if (messageFragment != null) {
            fragmentTransaction.hide(messageFragment);
        }
    }
}
