package com.example.hello1application.medical.quota.reportDetail;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hello1application.R;

import java.util.ArrayList;
import java.util.List;

public class ReportDetailCollectionActivity extends AppCompatActivity implements ReportDetailsAdapter.IReportDetailItem {

    public static final int IndexList_Code = 0;

    private RecyclerView recyclerView_report_details;
    private ReportDetailsAdapter reportDetailsAdapter;
    private List<ReportDetail> reportDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail_collection);
        initView();
        initData();

        reportDetailsAdapter = new ReportDetailsAdapter(this, reportDetails, this);
        recyclerView_report_details.setAdapter(reportDetailsAdapter);
    }

    private void initData() {
        reportDetails = new ArrayList<>();
        /**
         * 游离三碘甲状原氨酸FT3（     ）
         * 游离四碘甲妆原氨酸Ft4（     ）
         * 促甲状腺激素TSH（      ）
         * Tg（       ）
         * TgAb
         * TRAB
         * TPOAB
         * 甲状腺摄碘率检查
         * */
        ReportDetail reportDetail1 = new ReportDetail("甲状腺球蛋白Tg",1, null, 0, "____", "____");
        ReportDetail reportDetail2 = new ReportDetail("抗甲状腺球蛋白抗体TgAb",2, "23小时前", ReportDetail.STATUS_High, "9", "IU/ml");
        ReportDetail reportDetail3 = new ReportDetail("促甲状腺激素TSH",3, null, 0, "____", "____");
        ReportDetail reportDetail4 = new ReportDetail("促甲状腺激素受体抗体TRAb",4, null, ReportDetail.STATUS_NORMAL, "3.00", "IU/ml");
        ReportDetail reportDetail5 = new ReportDetail("游离三碘甲状腺原氨酸FT3",5, null, 0, "____", "____");
        ReportDetail reportDetail6 = new ReportDetail("游离三碘甲状腺原氨酸FT4",6, null, 0, "____", "____");
        ReportDetail reportDetail7 = new ReportDetail("甲状腺过氧化物酶抗体TPOAb",7, null, 0, "____", "____");
        reportDetails.add(reportDetail1);
        reportDetails.add(reportDetail2);
        reportDetails.add(reportDetail3);
        reportDetails.add(reportDetail4);
        reportDetails.add(reportDetail5);
        reportDetails.add(reportDetail6);
        reportDetails.add(reportDetail7);

    }

    private void initView() {
        recyclerView_report_details = findViewById(R.id.recyclerView_report_details);
        recyclerView_report_details.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

    }


    /**
     * ① 回调过程1，(A→B)
     * 跳转到IndexDetailsActivity,传递指标类型（如type==1是TgAb,type==2是TSH）
     * @param reportDetail
     */
    @Override
    public void onClickReportItem(ReportDetail reportDetail) {
        Bundle bundle = new Bundle();
        bundle.putInt("reportDetail_type",reportDetail.type); // 点击的是哪一个入口（TSH，还是Tg等等）
        bundle.putString("reportDetail_name",reportDetail.title);

        Intent intent = new Intent(ReportDetailCollectionActivity.this,IndexDetailsActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,IndexList_Code);
    }

    /**
     * ⑤ 回调过程5，(B→A)
     * 接受IndexDetailsActivity的数据（实际是RecordIndexDetail的，经IndexDetailsActivity处理后）并处理
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IndexList_Code && resultCode == RESULT_OK){
            String indexStr = data.getExtras().getString("indexStr");
            int recyclerView_status = data.getExtras().getInt("recyclerView_status");
            String unitStr = data.getExtras().getString("unitStr");
            String dateStr = data.getExtras().getString("dateStr");
            ReportDetail reportDetail = new ReportDetail();
            reportDetail.result = indexStr;
            reportDetail.status = recyclerView_status;
            reportDetail.time = dateStr;
            reportDetail.unit = unitStr;

            reportDetailsAdapter.updateData(reportDetail);
        }
    }
}
