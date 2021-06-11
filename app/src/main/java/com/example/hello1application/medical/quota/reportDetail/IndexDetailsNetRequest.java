package com.example.hello1application.medical.quota.reportDetail;

import android.os.Message;
import android.util.Log;

import com.example.hello1application.common.http.Code;
import com.example.hello1application.common.http.Constants;
import com.example.hello1application.common.http.OkHttpUtils;
import com.example.hello1application.common.http.model.IndexDetailGson;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class IndexDetailsNetRequest {

    public void addIndexDetails(int indexType , String reportDetail_name, IndexDetails indexDetails, final android.os.Handler handler) {
        Map<String, String> params = Collections.synchronizedMap(new HashMap<String, String>());
        params.put("patientId","101");
        params.put("patientPhonenum","110");
        params.put("indexType",Integer.toString(indexType));
        params.put("indexValue",indexDetails.recyclerView_index);
        params.put("indexUnit",indexDetails.recyclerView_unit);
        params.put("indexStatus",Integer.toString(indexDetails.recyclerView_status));
        params.put("indexStandardMin",indexDetails.recyclerView_min);
        params.put("indexStandardMax",indexDetails.recyclerView_max);
        params.put("indexDate",indexDetails.recyclerView_date);
        params.put("indexName",reportDetail_name);

        Call call = OkHttpUtils.getInstance().post(Constants.REPORT_DETAIL_URL,params);
        call.enqueue(new Callback() { //异步请求
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: "+e.toString()+Thread.currentThread().getName());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                IndexDetailGson indexDetailGson = OkHttpUtils.getGson().fromJson(json, IndexDetailGson.class);
                if(indexDetailGson.getMsg().equals("ok")){
                    Message message = Message.obtain();
                    message.what = Code.MESSAGE_OK;
                    message.obj = indexDetailGson;
                    handler.sendMessage(message);
                }
                // 考虑其他地方登录的问题

                Log.d(TAG, "onResponse: " + json +Thread.currentThread().getName());
            }
        });
    }
}
