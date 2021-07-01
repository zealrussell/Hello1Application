package com.example.hello1application.chat;

import android.content.Context;
import android.os.Handler;

import androidx.multidex.MultiDexApplication;

public class BaseApp extends MultiDexApplication{
    private static Context mContext;//上下文
    private static long mMainThreadId;//主线程id
    private static Handler mHandler;//主线程Handler

    @Override
    public void onCreate() {
        super.onCreate();

        //对全局属性赋值
        mContext = getApplicationContext();
        mMainThreadId = android.os.Process.myTid();
        mHandler = new Handler();
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        BaseApp.mContext = mContext;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

}
