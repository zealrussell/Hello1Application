package com.example.hello1application.medical.medicalRecord.record;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.hello1application.common.http.Code;
import com.example.hello1application.common.http.Constants;
import com.example.hello1application.common.http.OkHttpUtils;
import com.example.hello1application.common.http.model.InsertGson;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class PostMedicalRecordNetRequest {
    public void addPostMedicalRecord(PostMedicalRecord postMedicalRecord, final Handler handler){
        Map<String, String> hashMap = Collections.synchronizedMap(new HashMap<String, String>());
        hashMap.put("patientId","101");
        hashMap.put("patientPhonenum","110");

        String dtc = postMedicalRecord.getDtc();
        String sideEffect = postMedicalRecord.getSideEffect();
        String operationDate = postMedicalRecord.getOperationDate();
        String medicalCaseType = postMedicalRecord.getMedicalCaseType();
        String medicalCaseName = postMedicalRecord.getMedicalCaseName();
        String operationDescription = postMedicalRecord.getOperationDescription();
        String operationWay = postMedicalRecord.getOperationWay();

        if(dtc == null){
            hashMap.put("dtc","");
        }else{
            hashMap.put("dtc",dtc);
        }
        if(sideEffect == null){
            hashMap.put("sideEffect","");
        }else{
            hashMap.put("sideEffect",sideEffect);
        }

        hashMap.put("operationDate",operationDate==null?"":operationDate);
        hashMap.put("medicalcaseType",medicalCaseType==null?"":medicalCaseType);
        hashMap.put("medicalcaseName",medicalCaseName==null?"":medicalCaseName);
        hashMap.put("operationDescription",operationDescription==null?"":operationDescription);
        hashMap.put("operationWay",operationWay==null?"":operationWay);

        Call call = OkHttpUtils.getInstance().post(Constants.Medical_CASE__URL,hashMap);
        call.enqueue(new Callback() { //异步请求
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: "+e.toString()+Thread.currentThread().getName());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                InsertGson insertGson = OkHttpUtils.getGson().fromJson(json,InsertGson.class);
                if(insertGson.getMsg().equals("ok")){
                    Message message = Message.obtain();
                    message.what = Code.MESSAGE_OK;
                    message.obj = insertGson;
                    handler.sendMessage(message);
                }
                Log.d(TAG, "onResponse: " +json+Thread.currentThread().getName());
            }
        });
    }


}
