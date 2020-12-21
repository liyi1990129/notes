package com.drools.util;

import com.drools.common.constrant.SysbolEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：规则工具类
 */
public class RuleUtils {


    /**
     * 字符串为 null 或者为  "" 时返回 true
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * 字符串不为 null 而且不为  "" 时返回 true
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 方法说明: 截取两个$之间的内容
     *
     * @param str 字符串
     */
    public static List<String> getConditionParamBetweenChar(String str) {

        Matcher m = Pattern.compile("\\$(.*?)\\$").matcher(str);
        List<String> list = new ArrayList<>();
        while (m.find()) {
            list.add(m.group(1));
        }
        return list;
    }

    /**
     * 方法说明:截取两个@之间的内容
     */
    public static List<String> getValueBetweenChar(String str) {

        Matcher m = Pattern.compile("\\@(.*?)\\@").matcher(str);
        List<String> list = new ArrayList<>();
        while (m.find()) {
            list.add(m.group(1));
        }
        return list;
    }

    /**
     * 方法说明: 截取两个#之间的内容
     */
    public static List<String> getActionParamBetweenChar(String str) {
        Matcher m = Pattern.compile("\\#(.*?)\\#").matcher(str);
        List<String> list = new ArrayList<>();
        while (m.find()) {
            list.add(m.group(1));
        }
        return list;
    }

    /**
     * 方法说明: 截取两个#之间的内容
     */
    public static String getParamBetweenChar(String str, String charStr) {
        Matcher m = Pattern.compile("\\" + charStr + "(.*?)\\" + charStr + "").matcher(str);
        String value = null;
        while (m.find()) {
            value = m.group(1);
        }
        return value;
    }

    /**
     * 方法说明: 如果是整数或者小数，则返回true，否则返回false
     */
    public static boolean checkStyleOfString(String str) {
        return str.matches("^(\\-|\\+)?\\d+(\\.\\d+)?$");
    }

    /**
     * 方法说明:得到 > >= < <= == != 后面的字符串
     */
    public static String getConditionOfVariable(String str) {

        String[] arr = str.split(">(=)?|<(=)?|={1,2}|!=");
        if (arr.length < 2) {
            return "";
        }
        return arr[1];
    }
    /**
     * 方法说明:得到 > >= < <= == != 后面的字符串加引号
     */
    public static String getConditionAddMarks(String str,String type) {

        String[] arr = str.split(">(=)?|<(=)?|={1,2}|!=");
        if (arr.length < 2) {
            return "";
        }
        if(arr[1].indexOf("\"")>0 || arr[1].indexOf("\'")>0){
            return arr[0] + SysbolEnum.getNameByCode(type) + arr[1];
        }else{

            String result = arr[0] + SysbolEnum.getNameByCode(type)+"\""+arr[1]+"\"";
            return result;
        }
    }

    /**
     * 方法说明:检查是否包含 + - × / % 如果包含返回true
     */
    public static boolean checkContainOfOperator(String str) {
        return str.contains("+") || str.contains("-") || str.contains("*") ||
                str.contains("/") || str.contains("%");
    }

    /**
     * 方法说明:判断当前字符串是否包含 特殊字符 信息,如果包含返回true
     */
    public static boolean checkContainOfOperator(String str, String charStr) {
        return str.contains(charStr);
    }

    /**
     * 方法说明: 根据属性得到其get方法名
     */
    public static String getMethodByProperty(String property) {
        //为防止原参数发生变化，此处单独创建变量接收
        String tempStr = property;
        if (RuleUtils.isNotEmpty(property)) {
            tempStr = tempStr.substring(0, 1).toUpperCase() + tempStr.substring(1);
            return "get" + tempStr + "()";
        }
        return null;
    }

    /**
     * 方法说明: 根据属性得到其set方法名
     */
    public static String setMethodByProperty(String property) {
        //为防止原参数发生变化，此处单独创建变量接收
        String tempStr = property;
        if (RuleUtils.isNotEmpty(property)) {
            tempStr = tempStr.substring(0, 1).toUpperCase() + tempStr.substring(1);
            return "set" + tempStr;
        }
        return null;
    }

    public static void main(String[] args) {
        String name = "money";
        System.out.println(name);
        System.out.println("\n");
        System.out.println(name);
    }
}
