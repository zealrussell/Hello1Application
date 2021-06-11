package com.example.hello1application.medical.medicalRecord.calculateDosage;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.hello1application.common.http.Code;
import com.example.hello1application.common.http.Constants;
import com.example.hello1application.common.http.OkHttpUtils;
import com.example.hello1application.common.http.model.DosageReviewGson;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class DosageReviewNetRequest {

    public void getDosageReview(String patientId, final Handler handler){
        Map<String, String> hashMap = Collections.synchronizedMap(new HashMap<String, String>());
        hashMap.put("patientId",patientId);

        Call call = OkHttpUtils.getInstance().get(Constants.Dosage_Review_URL,hashMap);
        call.enqueue(new Callback() { //异步请求
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: "+e.toString());
                Log.e(TAG, Thread.currentThread().getName());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 获取后端传来的 用药剂量、复查时间
                String json = response.body().string();
                DosageReviewGson dosageReviewGson = OkHttpUtils.getGson().fromJson(json, DosageReviewGson.class);
                if(dosageReviewGson.getMsg().equals("ok")){
                    Message message = Message.obtain();
                    message.what = Code.MESSAGE_OK;
                    message.obj = dosageReviewGson;
                    handler.sendMessage(message);
                }

                Log.d(TAG, "onResponse: " + json +Thread.currentThread().getName());
            }
        });
    }
}
