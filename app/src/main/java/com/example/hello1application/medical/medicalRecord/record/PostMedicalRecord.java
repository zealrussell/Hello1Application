package com.example.hello1application.medical.medicalRecord.record;

public class PostMedicalRecord {
    public String medicalCaseType;
    public String medicalCaseName;
    public String operationDate;
    public String operationWay;
    public String operationDescription;
    public String dtc;
    public String sideEffect;

    public String getDtc() {
        return dtc;
    }

    public void setDtc(String dtc) {
        this.dtc = dtc;
    }

    public String getSideEffect() {
        return sideEffect;
    }

    public void setSideEffect(String sideEffect) {
        this.sideEffect = sideEffect;
    }

    public String getMedicalCaseType() {
        return medicalCaseType;
    }

    public void setMedicalCaseType(String medicalCaseType) {
        this.medicalCaseType = medicalCaseType;
    }

    public String getMedicalCaseName() {
        return medicalCaseName;
    }

    public void setMedicalCaseName(String medicalCaseName) {
        this.medicalCaseName = medicalCaseName;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public String getOperationWay() {
        return operationWay;
    }

    public void setOperationWay(String operationWay) {
        this.operationWay = operationWay;
    }

    public String getOperationDescription() {
        return operationDescription;
    }

    public void setOperationDescription(String operationDescription) {
        this.operationDescription = operationDescription;
    }
}
