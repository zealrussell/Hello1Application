package com.example.hello1application.medical.quota.reportDetail;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hello1application.R;

import java.util.ArrayList;
import java.util.List;

public class IndexDetailsAdapter extends RecyclerView.Adapter<IndexDetailsAdapter.IndexDetailsViewHolder> {

    private List<IndexDetails> indexDetails = new ArrayList<>();
    private Context mContext;
    private int colorRed, colorGreen;

    static class IndexDetailsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout addIndexListView;
        TextView listView_index;
        TextView listView_unit;
        TextView listView_status;
        TextView listView_standard;
        TextView listView_date;

        public IndexDetailsViewHolder(View itemView) {
            super(itemView);
            addIndexListView = itemView.findViewById(R.id.addIndexListView);
            listView_index = itemView.findViewById(R.id.listView_index);
            listView_unit = itemView.findViewById(R.id.listView_dosage);
            listView_status = itemView.findViewById(R.id.listView_status);
            listView_standard = itemView.findViewById(R.id.listView_standard);
            listView_date = itemView.findViewById(R.id.listView_date);
        }
    }

    public IndexDetailsAdapter(Context mContext, List<IndexDetails> indexDetails) {
        colorRed = ContextCompat.getColor(mContext, R.color.colorRed);
        colorGreen = ContextCompat.getColor(mContext, R.color.colorGreen);
        if (indexDetails != null) {
            this.indexDetails.addAll(indexDetails);
        }
    }

    /***
     * 指标集合的点击跳转
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public IndexDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recyclerview_index_detail,parent,false);
        final IndexDetailsViewHolder holder = new IndexDetailsViewHolder(view);

        holder.addIndexListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                IndexDetails indexDetail = indexDetails.get(position);
                Intent intent = null;
                switch (position){
                    case 1:
                        intent=new Intent(view.getContext(),IndexDetailsActivity.class);
                        view.getContext().startActivity(intent);
                        break;
                }

                Toast.makeText(view.getContext(), "you clicked view" + indexDetail.recyclerView_index, Toast.LENGTH_SHORT).show();
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
    public void onBindViewHolder(@NonNull IndexDetailsViewHolder holder, int position) {
        if (position == RecyclerView.NO_POSITION) {
            return;
        }
        IndexDetails indexDetail = this.indexDetails.get(position);

        if (indexDetail != null) {
            if (indexDetail.recyclerView_index != null) {
                holder.listView_index.setText(indexDetail.recyclerView_index);
            }
            if (indexDetail.recyclerView_date != null) {
                holder.listView_date.setText(indexDetail.recyclerView_date);
                //holder.listView_date.setVisibility(View.VISIBLE);
            }
            if(indexDetail.recyclerView_unit != null){
                holder.listView_unit.setText(indexDetail.recyclerView_unit);
            }
            if(indexDetail.recyclerView_standard != null){
                holder.listView_standard.setText(indexDetail.recyclerView_standard);
            }
            if (indexDetail.recyclerView_status == indexDetail.STATUS_NORMAL) {
                holder.listView_status.setText("正常");
                holder.listView_status.setTextColor(colorGreen);
                holder.listView_status.setVisibility(View.VISIBLE);
            } else if (indexDetail.recyclerView_status == indexDetail.STATUS_Low) {
                holder.listView_status.setText("偏低");
                holder.listView_status.setTextColor(colorRed);
                holder.listView_status.setVisibility(View.VISIBLE);
            } else if (indexDetail.recyclerView_status == indexDetail.STATUS_High) {
                holder.listView_status.setText("偏高");
                holder.listView_status.setTextColor(colorRed);
                holder.listView_status.setVisibility(View.VISIBLE);
            }else if (indexDetail.recyclerView_status == indexDetail.STATUS_NEGATIVE) {
                holder.listView_status.setText("阴性");
                holder.listView_status.setTextColor(colorGreen);
                holder.listView_status.setVisibility(View.VISIBLE);
            }else if (indexDetail.recyclerView_status == indexDetail.STATUS_POSITIVE) {
                holder.listView_status.setText("阳性");
                holder.listView_status.setTextColor(colorRed);
                holder.listView_status.setVisibility(View.VISIBLE);
            } else {
                holder.listView_status.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return indexDetails.size();
    }

    public void addData(IndexDetails indexDetail){
        if (indexDetail != null){
            this.indexDetails.add(indexDetail);
            this.notifyDataSetChanged();
        }
    }


}
