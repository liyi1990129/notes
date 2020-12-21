package com.drools.common.constrant;

public enum SysbolEnum {
    S1("1","=="),
    S2("2","!="),
    S3("3","<="),
    S4("4","<"),
    S5("5",">="),
    S6("6",">");

    public String code;
    public String name;

    SysbolEnum(String code,String name){
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(String code){
        for (SysbolEnum con : SysbolEnum.values()) {
            if (con.getCode().equals(code)) {
                return con.getName();
            }
        }
        return "";
    }


    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
