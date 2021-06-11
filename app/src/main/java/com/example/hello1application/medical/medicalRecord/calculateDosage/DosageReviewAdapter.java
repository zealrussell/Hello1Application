package com.example.hello1application.medical.medicalRecord.calculateDosage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hello1application.R;

import java.util.Vector;

public class DosageReviewAdapter extends RecyclerView.Adapter<DosageReviewAdapter.DosageReviewHolder>{
    private Vector<DosageReview> dosageReviewList = new Vector<>();
    private Context mContext;
    private IDosageReview iDosageReview;

    public DosageReviewAdapter(Context mContext,Vector<DosageReview> dosageReviewList,IDosageReview iDosageReview) {
        this.mContext = mContext;
        if(dosageReviewList != null){
            this.dosageReviewList.addAll(dosageReviewList);
        }
        if(iDosageReview != null){
            this.iDosageReview = iDosageReview;
        }
    }

    // 1.建立本适配器的Holder，目的是获得.xml的控件
    static class DosageReviewHolder extends RecyclerView.ViewHolder{
        LinearLayout addDosageReview;
        TextView tvAssessmentDate,tvDosageAdaptCase,tvReviewTime;

        public DosageReviewHolder(@NonNull View itemView) {
            super(itemView);
            addDosageReview = itemView.findViewById(R.id.layout_dosage_review);
            tvAssessmentDate = itemView.findViewById(R.id.tv_assessment_date);
            tvDosageAdaptCase = itemView.findViewById(R.id.tv_dosage_adapt_case);
            tvReviewTime = itemView.findViewById(R.id.tv_review_time);
        }
    }

    // 2.建立接口和方法，目的是让Activity实现，以获取这一行list的点击事件
    public interface IDosageReview{
        void onClickDosageReview(DosageReview dosageReview);
    }

    // 3.重写onCreateViewHolder方法，目的是获得.xml（一行list）的具体设计页面，并且获得点击事件
    @NonNull
    @Override
    public DosageReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_dosage_review,parent,false);
        final DosageReviewAdapter.DosageReviewHolder holder = new DosageReviewAdapter.DosageReviewHolder(view);

        holder.addDosageReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition(); //点击的位置
                DosageReview dosageReview = dosageReviewList.get(position);

                if(iDosageReview != null){
                    iDosageReview.onClickDosageReview(dosageReview);

                }

                Toast.makeText(view.getContext(), "you clicked view" + dosageReview.tv_assessment_date, Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DosageReviewHolder holder, int position) {
        if (position == RecyclerView.NO_POSITION) {
            return;
        }
        DosageReview dosageReview = dosageReviewList.get(position);

        if (dosageReview != null) {
            if (dosageReview.tv_assessment_date != null) {
                holder.tvAssessmentDate.setText(dosageReview.tv_assessment_date);
            }
            if (dosageReview.tv_dosage_adapt_case != null) {
                holder.tvDosageAdaptCase.setText(dosageReview.tv_dosage_adapt_case);
            }
            if (dosageReview.tv_review_time != null) {
                holder.tvReviewTime.setText(dosageReview.tv_review_time);
                //holder.tvReviewTime.setVisibility(View.VISIBLE);可显与不可显
            }
        }
    }

    @Override
    public int getItemCount() {
        return dosageReviewList.size();
    }

    public void addData(DosageReview dosageReview){
        if (dosageReview != null){
            this.dosageReviewList.add(dosageReview);
            this.notifyDataSetChanged();
        }
    }
}
