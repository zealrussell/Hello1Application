package com.example.hello1application.medical.medicalRecord;

import com.example.hello1application.R;

public enum NameIndexEnum {
    Num1(R.id.sideEffect_lowSick1,"①"),
    Num2(R.id.sideEffect_lowSick1,"②")

    ;
    private String name;
    private int index;

    NameIndexEnum(int index,String name) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
