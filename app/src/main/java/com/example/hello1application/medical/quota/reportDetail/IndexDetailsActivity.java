package com.example.hello1application.medical.quota.reportDetail;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hello1application.R;
import com.example.hello1application.common.http.Code;
import com.example.hello1application.common.http.OkHttpUtils;
import com.example.hello1application.common.http.model.DosageReviewGson;
import com.example.hello1application.common.http.model.IndexDetailGson;
import com.example.hello1application.medical.medicalRecord.calculateDosage.DosageReview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IndexDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int RecordIndex_Code = 0;

    private final int MSG_OK = 0;

    private Button mBtnRecordIndex;//btn_recordIndex

    private RecyclerView recyclerView;
    private IndexDetailsAdapter indexDetailsAdapter;
    private List<IndexDetails> indexDetails;
    private TextView tv_indexShow;
    private TextView tv_dateShow;
    private TextView tv_index_standard;
    private TextView tv_index_unit;
    private TextView tv_statusShow;
    private TextView tv_reportDetail_name;
    private int recyclerView_status = 0;
    private int reportDetail_type = 0;
    private String reportDetail_name = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_details);

        initView();
        initData();

        indexDetailsAdapter = new IndexDetailsAdapter(this, indexDetails);
        recyclerView.setAdapter(indexDetailsAdapter);

        /**
        try
        {
            OkHttpUtils.getInstance()
                    .setCertificates(getAssets().open("keystore.cer"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }**/
    }

    private void initData(){

        indexDetails = new ArrayList<>();
//        IndexDetails indexDetails1 = new IndexDetails("12","U/L",IndexDetails.STATUS_NORMAL,"0.39~1.0","2019-01-03");
//        IndexDetails indexDetails2 = new IndexDetails("12","U/L",IndexDetails.STATUS_NORMAL,"0.39~1.0","2019-01-04");
//        IndexDetails indexDetails3 = new IndexDetails("12","U/L",IndexDetails.STATUS_High,"0.39~1.0","2019-01-05");
//
//        indexDetails.add(indexDetails1);
//        indexDetails.add(indexDetails2);
//        indexDetails.add(indexDetails3);
    }

    private void initView() {
        // 判断reportDetail_type的数字，从数据库获取数据，或者是缓存
        Intent intent = getIntent ();
        reportDetail_type = intent.getExtras().getInt("reportDetail_type");
        reportDetail_name = intent.getExtras().getString("reportDetail_name");

        recyclerView = findViewById(R.id.listView_index_details);
        tv_indexShow = findViewById(R.id.tv_indexShow);
        tv_dateShow = findViewById(R.id.tv_dateShow);
        mBtnRecordIndex = findViewById(R.id.btn_recordIndex);
        tv_index_standard = findViewById(R.id.tv_index_standard);
        tv_index_unit = findViewById(R.id.tv_index_unit);
        tv_statusShow = findViewById(R.id.tv_statusShow);
        tv_reportDetail_name = findViewById(R.id.tv_reportDetail_name);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBtnRecordIndex.setOnClickListener(IndexDetailsActivity.this);

        tv_reportDetail_name.setText(reportDetail_name);
    }


    /**
     * TODO ② 回调过程2，(B→C)
     * TODO *************IndexDetailsActivity跳转到RecordIndexDetailActivity*********************
     * @param view
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_recordIndex:
                Bundle bundle = new Bundle();
                bundle.putInt("reportDetail_type",reportDetail_type);
                bundle.putString("reportDetail_name",reportDetail_name);

                Intent intent = new Intent(IndexDetailsActivity.this, RecordIndexDetailActivity.class); //从 A类 跳转到 B类
                intent.putExtras(bundle);
                startActivityForResult(intent,RecordIndex_Code);

                Toast.makeText(this, "add", Toast.LENGTH_LONG).show();
                break;

        }
    }

    /**
     *
     * TODO ④ 回调过程4，(C→B)
     * TODO ************** 获取RecordIndexDetailActivity回调的数据******************
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RecordIndex_Code && resultCode == RESULT_OK){
            String dateStr = data.getStringExtra("recordIndex_dateStr");
            String indexStr = data.getStringExtra("recordIndex_indexStr");
            String unitStr = data.getStringExtra("unitStr");
            String minIndex = data.getStringExtra("editMinIndex");
            String maxIndex = data.getExtras().getString("editMaxIndex");

            // 记录指标后，IndexDetailsActivity界面新增一条，并且将返回值传给后端
            addIndexDetail(dateStr,indexStr,unitStr,minIndex,maxIndex);

        }
    }

    /**
     * TODO bundle传递数据给Report..Activity
     * @param status
     * @param date
     * @param index
     * @param unit
     */
    public void backToActivity(int status,String date,String index,String unit){
        Bundle bundle = new Bundle();
        bundle.putInt("recyclerView_status",status);
        bundle.putString("dateStr",date);
        bundle.putString("indexStr",index);
        bundle.putString("unitStr",unit);

        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
    }

    /**
     * TODO 判断指标值 偏高/正常/偏低， 更新“记录指标”后的返回值，将值传给后端，完成后更新到上一个Activity
     * @param dateStr
     * @param indexStr
     * @param unit
     * @param min
     * @param max
     */
    public void addIndexDetail(String dateStr,String indexStr,String unit,String min,String max) {
        if("".equals(dateStr)||"".equals(indexStr)||"".equals(unit)||"".equals(min)||"".equals(max)){
            Toast.makeText(IndexDetailsActivity.this,"返回值获取失败",Toast.LENGTH_LONG).show();
        }

        //判断“偏高”，”偏低“（阴性，阳性暂缓）
        double indexDouble = Double.parseDouble(indexStr);
        double minDouble = Double.parseDouble(min);
        double maxDouble = Double.parseDouble(max);
        if(indexDouble>=minDouble && indexDouble<=maxDouble){
            recyclerView_status = IndexDetails.STATUS_NORMAL;
            tv_statusShow.setText("正常");
        }
        else if(indexDouble<minDouble){
            tv_statusShow.setText("偏低");
            recyclerView_status = IndexDetails.STATUS_Low;
        }
        else if(indexDouble>maxDouble){
            tv_statusShow.setText("偏高");
            recyclerView_status = IndexDetails.STATUS_High;
        }

        String standard = min+"~"+max;
        IndexDetails indexDetails = new IndexDetails(indexStr, unit, recyclerView_status, standard, dateStr,min,max);
        indexDetailsAdapter.addData(indexDetails);

        tv_indexShow.setText(indexStr);
        tv_dateShow.setText(dateStr);
        tv_index_standard.setText(standard);
        tv_index_unit.setText(unit);

        IndexDetailsNetRequest indexDetailsNetRequest = new IndexDetailsNetRequest();
        indexDetailsNetRequest.addIndexDetails(reportDetail_type,reportDetail_name , indexDetails, mHandler);

    }

    final Handler mHandler = new Handler(){
        // 定义处理消息的方法
        public void handleMessage (Message msg) {
        switch(msg.what) {
            case Code.MESSAGE_OK:
                IndexDetailGson indexDetailGson = (IndexDetailGson) msg.obj;
                // 拿到从Record..Activity中获取的值，再准备返回给Report..Activity
                backToActivity(recyclerView_status,indexDetailGson.getIndexDate(),indexDetailGson.getIndexValue(),indexDetailGson.getIndexUnit());

                Log.d("IndexDetailsNetRequest", "ReportIndexThread receive msg:" + msg.obj);
                break;
        }
        }
    };
}
