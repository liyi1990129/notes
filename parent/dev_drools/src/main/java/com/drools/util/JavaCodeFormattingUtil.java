package com.drools.util;

import com.google.googlejavaformat.java.Formatter;

/* *
 * 代码格式化 
 * @author ly
 * @modifyTime 2020/11/26 15:24:00
 */
public class JavaCodeFormattingUtil {
    public static String tryFormat(String code) {
        String newCode = "";
        try{
            newCode = new Formatter().formatSource(code);
        }catch (Exception e){
            e.printStackTrace();
        }
        return newCode;
    }
}
