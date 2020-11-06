package com.stu.drools.rest;

import com.alibaba.fastjson.JSON;
import com.stu.drools.model.fact.RuleExecutionObject;
import com.stu.drools.model.fact.RuleExecutionResult;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Test {
    //换行符
    private static final String lineSeparator = System.getProperty("line.separator");

    public static void main(String[] args) throws Exception {


        Map<String,Object> map = new HashMap<>();
        map.put("name","张三");
        map.put("age",13);
        map.put("sex","man");
        map.put("clsName","1");
        RuleExecutionObject object = new RuleExecutionObject();
        Class<?> stuClass = Class.forName("com.stu.drools.entity.Student");
        //获取一个对象
        Object obj = stuClass.getConstructor().newInstance();

        for (String key : map.keySet()) {
            Field f = stuClass.getDeclaredField(key);
            Object v = map.get(key);
            System.out.println(f);
            f.setAccessible(true);//暴力反射，解除私有限定
            f.set(obj, v);
        }

        object.addFactObject(obj);


        //全局对象
        RuleExecutionResult result = new RuleExecutionResult();
        object.setGlobal("_result",result);

        String ruleStr = getRules1("test.drl");
        System.out.println(ruleStr);
        RuleExecutionObject result1 = Engine.excute(ruleStr, object, "test");

        System.out.println("******************结果************************");
        System.out.println(JSON.toJSONString(result1));
    }


    public static String getRules(String fileName) throws IOException {
        String str = "";
        String path = "G://2020/day14_drools/src/main/resources/rules/"+fileName;
        File file = new File(path);
        InputStream in = new FileInputStream(file);
        try {
            byte b[] = new byte[1024];
            int len = 0;
            int temp=0;
            while((temp=in.read())!=-1){    //当没有读取完时，继续读取
                b[len]=(byte)temp;
                len++;
            }
            str = new String(b,0,len);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(in != null) {in.close();}
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return str;

    }
    public static String getRules1(String fileName) throws IOException {
        String str = "";
        String path = "F://git/dev_drools/src/main/resources/rules/"+fileName;
        File file = new File(path);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
            && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file "
                + file.getName());
        }
        fi.close();
        return new String(buffer);
    }
}
