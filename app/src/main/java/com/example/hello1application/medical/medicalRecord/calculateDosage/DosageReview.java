package com.example.hello1application.medical.medicalRecord.calculateDosage;

public class DosageReview {
    public String tv_assessment_date; //化验单上的日期（当前检查的日期，不是记录的日期）
    public String tv_dosage_adapt_case; //用药调整方案
    public String tv_review_time; //复查时间

    public DosageReview(String tv_assessment_date, String tv_dosage_adapt_case, String tv_review_time) {
        this.tv_assessment_date = tv_assessment_date;
        this.tv_dosage_adapt_case = tv_dosage_adapt_case;
        this.tv_review_time = tv_review_time;
    }

    public String getTv_assessment_date() {
        return tv_assessment_date;
    }

    public void setTv_assessment_date(String tv_assessment_date) {
        this.tv_assessment_date = tv_assessment_date;
    }

    public String getTv_dosage_adapt_case() {
        return tv_dosage_adapt_case;
    }

    public void setTv_dosage_adapt_case(String tv_dosage_adapt_case) {
        this.tv_dosage_adapt_case = tv_dosage_adapt_case;
    }

    public String getTv_review_time() {
        return tv_review_time;
    }

    public void setTv_review_time(String tv_review_time) {
        this.tv_review_time = tv_review_time;
    }
}
