package com.example.hello1application.medical.quota.reportDetail;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hello1application.R;

import java.util.ArrayList;
import java.util.List;

public class ReportDetailsAdapter extends RecyclerView.Adapter<ReportDetailsAdapter.ReportViewHolder> {

    private List<ReportDetail> reportDetails = new ArrayList<>();
    private Context mContext;
    private int colorRed, colorGreen;
    private IReportDetailItem interfaceIReportDetailItem;
    private int position = 0;

    static class ReportViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout_recyclerView;
        TextView tvReportName;
        TextView tvTime;
        TextView tvStatus;
        TextView tvNumber;
        TextView tvUnit;

        public ReportViewHolder(View itemView) {
            super(itemView);
            layout_recyclerView = itemView.findViewById(R.id.layout_recyclerView);
            tvReportName = itemView.findViewById(R.id.report_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvNumber = itemView.findViewById(R.id.tv_number);
            tvUnit = itemView.findViewById(R.id.tv_unit);
        }
    }

    // 构造方法，注册内容、集合以及接口
    public ReportDetailsAdapter(Context mContext, List<ReportDetail> reportDetails,IReportDetailItem iReportDetailItem) {
        colorRed = ContextCompat.getColor(mContext, R.color.colorRed);
        colorGreen = ContextCompat.getColor(mContext, R.color.colorGreen);
        if (reportDetails != null) {
            this.reportDetails.addAll(reportDetails);
        }
        if(iReportDetailItem != null){
            this.interfaceIReportDetailItem = iReportDetailItem;
        }
    }

    // 接口，让ReportDetailCollectionActivity实现，再由此适配器调用
    public interface IReportDetailItem{
        void onClickReportItem(ReportDetail reportDetail);
    }

    /***
     * 指标集合的点击跳转
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_report_detail,parent,false);
        final ReportViewHolder holder = new ReportViewHolder(view);

        holder.layout_recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = holder.getAdapterPosition();

                ReportDetail reportDetail = reportDetails.get(position);
                if(interfaceIReportDetailItem != null){
                    interfaceIReportDetailItem.onClickReportItem(reportDetail);

                }
            }
        });

        return holder;
    }

    /***
     * 修改指标集合页面的值
     * @param holder
     * @param position 点击的位置
     */
    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        if (position == RecyclerView.NO_POSITION) {
            return;
        }
        ReportDetail reportDetail = this.reportDetails.get(position);
        if (reportDetail != null) {
            if (reportDetail.title != null) {
                holder.tvReportName.setText(reportDetail.title);
            }
            if (reportDetail.time != null) {
                holder.tvTime.setText(reportDetail.time);
                holder.tvTime.setVisibility(View.VISIBLE);
            } else {
                holder.tvTime.setVisibility(View.GONE);
            }
            if (reportDetail.status == ReportDetail.STATUS_NORMAL) {
                holder.tvStatus.setText("正常");
                holder.tvStatus.setTextColor(colorGreen);
                holder.tvStatus.setVisibility(View.VISIBLE);
            } else if (reportDetail.status == reportDetail.STATUS_Low) {
                holder.tvStatus.setText("偏低");
                holder.tvStatus.setTextColor(colorRed);
                holder.tvStatus.setVisibility(View.VISIBLE);
            } else if (reportDetail.status == reportDetail.STATUS_High) {
                holder.tvStatus.setText("偏高");
                holder.tvStatus.setTextColor(colorRed);
                holder.tvStatus.setVisibility(View.VISIBLE);
            }else if (reportDetail.status == reportDetail.STATUS_NEGATIVE) {
                holder.tvStatus.setText("阴性");
                holder.tvStatus.setTextColor(colorGreen);
                holder.tvStatus.setVisibility(View.VISIBLE);
            }else if (reportDetail.status == reportDetail.STATUS_POSITIVE) {
                holder.tvStatus.setText("阳性");
                holder.tvStatus.setTextColor(colorRed);
                holder.tvStatus.setVisibility(View.VISIBLE);
            } else {
                holder.tvStatus.setVisibility(View.GONE);
            }
            if (reportDetail.result != null) {
                holder.tvNumber.setText(reportDetail.result);
                holder.tvNumber.setVisibility(View.VISIBLE);
            } else {
                holder.tvNumber.setVisibility(View.GONE);
            }
            if (reportDetail.unit != null) {
                holder.tvUnit.setText(reportDetail.unit);
                holder.tvUnit.setVisibility(View.VISIBLE);
            } else {
                holder.tvUnit.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return reportDetails.size();
    }


    public void updateData(ReportDetail reportDetail){
        if (reportDetail != null){
            this.reportDetails.set(position,reportDetail);
            this.notifyDataSetChanged();
        }
    }
}
