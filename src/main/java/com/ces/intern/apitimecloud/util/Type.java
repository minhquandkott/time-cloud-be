package com.ces.intern.apitimecloud.util;

public enum Type {

    BUG(0),
    FEATURE(1),
    APPROVE(2),
    OTHERS(3);

    private Integer value;

    Type(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
