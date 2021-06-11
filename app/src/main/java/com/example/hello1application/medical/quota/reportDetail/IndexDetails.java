package com.example.hello1application.medical.quota.reportDetail;

public class IndexDetails {

    public static final int STATUS_NORMAL = 1;
    public static final int STATUS_High = 2;
    public static final int STATUS_Low = 3;
    public static final int STATUS_NEGATIVE = 4; //阴性
    public static final int STATUS_POSITIVE = 5; //阳性


    public IndexDetails(String recyclerView_index, String recyclerView_unit, int recyclerView_status, String recyclerView_standard, String recyclerView_date, String recyclerView_min, String recyclerView_max) {
        this.recyclerView_index = recyclerView_index;
        this.recyclerView_unit = recyclerView_unit;
        this.recyclerView_status = recyclerView_status;
        this.recyclerView_standard = recyclerView_standard;
        this.recyclerView_date = recyclerView_date;
        this.recyclerView_min = recyclerView_min;
        this.recyclerView_max = recyclerView_max;
    }


    // 指标的数字
    public String recyclerView_index;
    // 指标的单位
    public String recyclerView_unit;
    // 状态（“正常”，“异常”）
    public int recyclerView_status;
    // 标准值
    public String recyclerView_standard;
    // 日期
    public String recyclerView_date;
    // 最小值
    public String recyclerView_min;
    // 最大值
    public String recyclerView_max;


    public String getRecyclerView_min() {
        return recyclerView_min;
    }

    public void setRecyclerView_min(String recyclerView_min) {
        this.recyclerView_min = recyclerView_min;
    }

    public String getRecyclerView_max() {
        return recyclerView_max;
    }

    public void setRecyclerView_max(String recyclerView_max) {
        this.recyclerView_max = recyclerView_max;
    }

    public static int getStatusNormal() {
        return STATUS_NORMAL;
    }

    public static int getSTATUS_High() {
        return STATUS_High;
    }

    public static int getSTATUS_Low() {
        return STATUS_Low;
    }

    public static int getStatusNegative() {
        return STATUS_NEGATIVE;
    }

    public static int getStatusPositive() {
        return STATUS_POSITIVE;
    }

    public String getRecyclerView_index() {
        return recyclerView_index;
    }

    public void setRecyclerView_index(String recyclerView_index) {
        this.recyclerView_index = recyclerView_index;
    }

    public String getRecyclerView_unit() {
        return recyclerView_unit;
    }

    public void setRecyclerView_unit(String recyclerView_unit) {
        this.recyclerView_unit = recyclerView_unit;
    }

    public int getRecyclerView_status() {
        return recyclerView_status;
    }

    public void setRecyclerView_status(int recyclerView_status) {
        this.recyclerView_status = recyclerView_status;
    }

    public String getRecyclerView_standard() {
        return recyclerView_standard;
    }

    public void setRecyclerView_standard(String recyclerView_standard) {
        this.recyclerView_standard = recyclerView_standard;
    }

    public String getRecyclerView_date() {
        return recyclerView_date;
    }

    public void setRecyclerView_date(String recyclerView_date) {
        this.recyclerView_date = recyclerView_date;
    }
}
