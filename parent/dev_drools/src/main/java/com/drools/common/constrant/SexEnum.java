package com.drools.common.constrant;

import lombok.Data;


public enum SexEnum {
    MAN("man","男"),
    FEMALE("female","女");

    public String code;
    public String name;

    SexEnum(String code,String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
