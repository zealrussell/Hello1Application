package com.example.hello1application.medical.quota.reportDetail;

public class ReportDetail {

    public static final int STATUS_NORMAL = 1;
    //public static final int STATUS_ABNORMAL = 2;
    public static final int STATUS_High = 2;
    public static final int STATUS_Low = 3;
    public static final int STATUS_NEGATIVE = 4; //阴性
    public static final int STATUS_POSITIVE = 5; //阳性

    public ReportDetail() {
    }

    public ReportDetail(String title,int type, String time, int status, String result, String unit) {
        this.title = title;
        this.type = type;
        this.time = time;
        this.status = status;
        this.result = result;
        this.unit = unit;
    }

    public int type;
    public String title;
    public String time;
    /**
     * STATUS_NORMAL正常，STATUS_ABNORMAL异常
     */
    public int status;
    public String result;
    public String unit;


}
