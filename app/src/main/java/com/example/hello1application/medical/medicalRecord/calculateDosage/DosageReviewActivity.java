package com.example.hello1application.medical.medicalRecord.calculateDosage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hello1application.R;
import com.example.hello1application.common.http.Code;
import com.example.hello1application.common.http.model.DosageReviewGson;
import com.example.hello1application.medical.medicalRecord.dialogUtils.DosageReviewDialog;

import java.util.Vector;

public class DosageReviewActivity extends AppCompatActivity implements
        View.OnClickListener,DosageReviewAdapter.IDosageReview, DosageReviewDialog.OnCenterItemClickListener{

    Button mBtnDosageRecord;
    RecyclerView rvDosageReview;
    DosageReviewAdapter dosageReviewAdapter;
    Vector<DosageReview> dosageReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosage_generator);

        //Intent intent = getIntent ();
        //HashMap<String,String> hashMap = (HashMap<String,String>)intent.getExtras().getSerializable("postRecordInfo");
        initData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_dosage_record:
                addDosageReview();
                break;
        }
    }

    private void initData(){
        mBtnDosageRecord = findViewById(R.id.btn_dosage_record);
        rvDosageReview = findViewById(R.id.rv_dosage_review);

        mBtnDosageRecord.setOnClickListener(this);
        rvDosageReview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        dosageReviews = new Vector<>();
        dosageReviewAdapter = new DosageReviewAdapter(this, dosageReviews,this);
        rvDosageReview.setAdapter(dosageReviewAdapter);
    }

    public void addDosageReview(){
        // 网络请求，从数据库获取结果
        String patientId = "101";
        DosageReviewNetRequest dosageReviewNetRequest = new DosageReviewNetRequest();
        dosageReviewNetRequest.getDosageReview(patientId,mHandler);
    }

    final Handler mHandler = new Handler(){
        // 定义处理消息的方法
        public void handleMessage (Message msg) {
            switch(msg.what) {
                case Code.MESSAGE_OK:
                    DosageReviewGson dosageReviewGson = (DosageReviewGson)msg.obj;
                    String assessmentDate = dosageReviewGson.getNow();
                    String adaptCase = dosageReviewGson.getDosage();
                    String reviewTime = dosageReviewGson.getReview();
                    DosageReview dosageReview = new DosageReview(assessmentDate,adaptCase,reviewTime);
                    dosageReviewAdapter.addData(dosageReview);

                    Log.d("DosageReviewNetRequest", "msg:" + msg.obj);
                    break;
            }
        }
    };

    // 点击recyclerView的某一行后
    @Override
    public void onClickDosageReview(DosageReview dosageReview) {
        String dosage_adapt_case = dosageReview.tv_dosage_adapt_case;
        String review_time = dosageReview.tv_review_time;
        if(!"".equals(dosage_adapt_case) && !"".equals(review_time)){
            clickRvDosage(dosage_adapt_case,review_time);
        }
    }


    // TODO *********************** dialog弹窗 ***********************
    private void clickRvDosage(String dosage_adapt_case,String review_time){
        DosageReviewDialog dosageReviewDialog;
        View contentView = View.inflate(this,R.layout.dialog_dosage_review,null);

        //实例化自定义的dialog, contentView就是R.layout.dialog_tnm
        dosageReviewDialog = new DosageReviewDialog(this,contentView,new int[]{R.id.tv_adapt_dosage,R.id.tv_review_time_show,R.id.btn_dosage_sure},dosage_adapt_case,review_time);
        //绑定点击事件
        dosageReviewDialog.setOnCenterItemClickListener(this);
        //显示
        dosageReviewDialog.show();
        //调用点击函数
    }
    @Override
    public void OnCenterItemClick(DosageReviewDialog dialog, View view) {

    }
}
