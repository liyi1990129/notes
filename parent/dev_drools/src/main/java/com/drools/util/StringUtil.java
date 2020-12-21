package com.drools.util;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述：字符串工具
 */
public class StringUtil {

    public static final String SPERATION_SYMBOL = "、";

    /**
     * @param list 要进行判断的数据集合
     * @return 不为空返回true，为空返回false
     * 方法说明：判断list集合是否为空
     */
    public static boolean listIsNotNull(List list) {
        return ((null != list) && (list.size() > 0));
    }

    /**
     * @param src 要进行判断的字符串
     * @return 为空返回true，不为空为false
     * 方法说明：判断字符串是否为空
     */
    public static boolean strIsNull(String src) {
        return (null == src) || "null".equals(src) || (src.trim().length() <= 0) || "".equals(src);
    }

    /**
     * 方法说明: 判断字符串是否不为空
     *
     * @param src 要进行判断的字符串
     * @return 为空返回false，不为空为true
     */
    public static boolean strIsNotNull(String src) {

        return !strIsNull(src);
    }

    /**
     * 方法说明: 将字符串转换为指定类型的list集合
     *
     * @param str   要转换的字符串
     * @param clazz 对象类型
     * @param <X>   泛型
     * @return 返回转换后的list集合
     */
    @SuppressWarnings("unchecked")
    public static <X> List<X> parseStrToList(String str, Class<X> clazz) {
        String[] a = str.split(",");
        List<X> b = new ArrayList<>();
        for (String anA : a) {
            Long c = Long.parseLong(anA);
            b.add((X) c);
        }
        return b;
    }


    /**
     * 方法说明: 根据日期获取当前年份
     *
     * @param date 日期
     */
    public static String getCurrentYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(date);
    }


    /**
     * 检查字符串组成的数组是否包含指定字符串
     *
     * @param s 字符串组成的数组，用“、”分隔
     * @param c
     * @return 包含指定字符串，返回true，否则返回false
     */
    public static boolean isContain(final String s, final String c) {
        String[] array = s.split(SPERATION_SYMBOL);
        for (int i = 0, len = array.length; i < len; i++) {
            if (array[i].trim().length() > 0 && array[i].trim().equals(c)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 检查字符串组成的数组是否不包含指定字符串
     *
     * @param s 字符串组成的数组，用“、”分隔
     * @param c
     * @return 包含指定字符串，返回false，否则返回true
     */
    public static boolean isNotContain(final String s, final String c) {
        String[] array = s.split(SPERATION_SYMBOL);
        for (int i = 0, len = array.length; i < len; i++) {
            if (array[i].trim().length() > 0 && array[i].trim().equals(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断数组是否包含指定的字符串
     *
     * @param s
     * @param c
     * @return
     */
    public static boolean isContainOne(final String[] s, final String c) {
        if (s == null || c == null || s.length == 0) {
            return false;
        }

        for (int i = 0, len = s.length; i < len; i++) {
            if (s[i] != null && s[i].trim().equals(c.trim()))
                return true;
        }

        return false;
    }

    /**
     * 检查2个数组是否有相同字符串
     *
     * @param s
     * @param c
     * @return
     */
    public static boolean isContains(final String[] s, final String[] c) {
        if (s == null || c == null) {
            return false;
        }

        for (int i = 0, len = s.length; i < len; i++) {
            for (int j = 0, len2 = c.length; j < len2; j++) {
                if (s[i] != null && c[j] != null && s[i].trim().length() > 0 && c[j].trim().length() > 0 && s[i].trim().equals(c[j].trim())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 伤病代码类似于
     *
     * @param s 如[A0800]
     * @param c 如[A08]
     * @return
     */
    public static boolean isCheckIllCodeContainsLike(final String[] s, final String c) {
        return isCheckIllCodeContainsLike(s, c.split(SPERATION_SYMBOL));
    }

    /**
     * 伤病代码类似于
     *
     * @param s 如[A0800]
     * @param c 如[A08]
     * @return
     */
    public static boolean isCheckIllCodeContainsLike(final String[] s, final String[] c) {
        if (s == null || c == null) {
            return false;
        }

        for (int i = 0, len = s.length; i < len; i++) {
            for (int j = 0, len2 = c.length; j < len2; j++) {
                if (s[i] != null && s[i].trim().length() > 0 && c[j] != null && c[j].trim().length() > 0
                        && isIllcodeContain(s[i].trim(), c[j].trim())) {
                    return true;
                }
            }
        }

        return false;
    }



    /**
     * liangdan 2014-06-15 add
     * 检查字符串组成的数组是否包含指定字符串
     *
     * @param s 字符串组成的数组，用“、”分隔
     * @param c 等级
     * @return 包含指定字符串，返回true，否则返回false
     */
    public static boolean isContain3(final String[] s, final String c) {
        //获取内存中按等级区分的伤病代码
        //Map illcodeList = (HashMap)SelectAll.servletConfig.getAttribute("illLevel");
        Map illcodeList = new HashMap();
        String content = (String) illcodeList.get(c);

        return isCheckIllCodeContainsLike(s, content.split(SPERATION_SYMBOL));
    }
    /**
     * 检查字符串组成的数组是否包含指定数组的任意一个
     *
     * @param s 数组
     * @param c 用"、"隔开的字符串
     * @return
     */
    public static boolean isContain(final String[] s,final String c){
        if(s == null || c == null || s.length == 0){
            return false;
        }
        return isContains(s, c.split(SPERATION_SYMBOL));
    }
    /**
     * 检查数组中的字符串是否与后面用、分隔的字符串数组有相等或startsWith
     *
     * @param s
     * @param c
     * @return
     */
    public static boolean isContainsEqualOrLike(final String[] s,final String c){
        return isContainsEqualOrLike(s,c.split(SPERATION_SYMBOL));
    }

    /**
     * 检查2个用“、”分隔开的字符串中的字符串是否有相等或startsWith
     *
     * @param s 如A08
     * @param c 如A0800
     * @return
     */
    public static boolean isContainsEqualOrLike(final String s,final String c){
        return isContainsEqualOrLike(s.split(SPERATION_SYMBOL),c);
    }


    /**
     * 检查2个数组中的字符串是否有相等或startsWith
     *
     * @param s 如[A08]
     * @param c 如[A0800]
     * @return
     */
    public static boolean isContainsEqualOrLike(final String[] s,final String[] c){
        if(s == null || c == null){
            return false;
        }

        for(int i=0,len = s.length;i<len;i++){
            for(int j=0,len2 = c.length;j<len2;j++){
                if(s[i] != null && s[i].trim().length() > 0
                    && c[j] != null
                    && c[j].trim().length() > 0
                    && (c[j].trim().startsWith(s[i].trim()) || c[j].trim().equals(s[i].trim()))){
                    return true;
                }
            }
        }

        return false;
    }



    /**
     * 判断2个字符串数组是否都在指定的用“、”分隔字符串组成的字符串数组内
     *
     * @param s 数组
     * @param c 数组
     * @param content 每个字符串表示一个用“、”分隔的数组
     * @return
     */
    public static boolean isContain(final String[] s,final String[] c,String[] content){
        if(s == null || c == null || content == null)
            return false;

        for(int a1=0,len1 = content.length;a1<len1;a1++){
            List list = new ArrayList();

            String[] array = content[a1].split(SPERATION_SYMBOL);

            for(int a2=0,len2 = array.length;a2<len2;a2++){
                list.add(array[a2]);
            }

            for(int a3=0,len3 = s.length;a3<len3;a3++){
                for(int a4=0,len4 = c.length;a4<len4;a4++){
                    if(s[a3] != null && s[a3].trim().length() > 0 && c[a4] != null && c[a4].trim().length() > 0
                        && list.contains(s[a3]) && list.contains(c[a4])){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     *  判断2个字符串数组是否都在指定的用“、”分隔字符串组成的字符串数组内
     *
     * @param s
     * @param c
     * @param cnt
     * @return
     */
    public static boolean isContain(final String[] s,final String[] c,String cnt){
        if(s == null || c == null || cnt == null || cnt.trim().length() <= 0)
            return false;

        String[] content = cnt.split(SPERATION_SYMBOL);
        for(int a1=0,len1 = content.length;a1<len1;a1++){
            for(int a3=0,len3 = s.length;a3<len3;a3++){
                for(int a4=0,len4 = c.length;a4<len4;a4++){
                    if(s[a3] != null && s[a3].trim().length() > 0 && c[a4] != null && c[a4].trim().length() > 0
                        && content[a1] != null && content[a1].trim().length() > 0 && content[a1].equals(s[a3]) && content[a1].equals(c[a4])){
                        return true;
                    }
                }
            }
        }

        return false;
    }


    /**
     * 判断2个字符串数组是否都在指定的用“、”分隔字符串组成的字符串数组内
     * 判断自定义伤病代码是否同一组(不校验同类同组)
     * @param s
     * @param c
     * @param contents 用“、”分隔字符串
     * @return
     */
    public static boolean isCheckSameIllCodeGroup(final String[] s,final String[] c,String contents){
        if(s == null || c == null || contents == null || contents.trim().length() <= 0)
            return false;

        String[] content = contents.split(SPERATION_SYMBOL);

        for(int a1=0,len1 = content.length;a1<len1;a1++){
            for(int a3=0,len3 = s.length;a3<len3;a3++){
                for(int a4=0,len4 = c.length;a4<len4;a4++){
                    if(s[a3] != null && s[a3].trim().length() > 0 && c[a4] != null && c[a4].trim().length() > 0
                        && content[a1] != null && content[a1].trim().length() > 0
                        && (isIllcodeContain(s[a3].trim(), content[a1].trim())
                        && isIllcodeContain(c[a4].trim(), content[a1].trim()))){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检查国际伤病代码对应是否在指定范围内
     *
     * @param s 案件中的国际伤病代码
     * @param c 决策表配置的伤病代码
     * @return 包含指定字符串，返回true，否则返回false
     */
    public static boolean isIllcodeContain(final String s,final String c){

        //国际伤病代码
        if (c.indexOf("-")== -1){
            //最底层
            if(c.length()>=7 && s.trim().length() > 0 && s.trim().startsWith(c)){
                return true;
            }
            //分层 最底层上一层
            if(c.length()<=3 && s.trim().length() > 0 && s.trim().startsWith(c)){
                return true;
            }
            //分层 最底层上一层 特殊显示
            if((c.length() == 4 || c.length() == 5) && s.trim().length() > 0 && s.trim().startsWith(c.substring(0, 3))){
                return true;
            }

        } else {
            //以“-”为范围的配置
            String[] array = c.split("-");
            String codeFrom = array[0].trim();
            String codeTo = array[1].trim();
            if (codeFrom.substring(0, 1).equals(codeTo.substring(0, 1))){
                int from = Integer.parseInt(codeFrom.substring(1, 3));
                int to = Integer.parseInt(codeTo.substring(1, 3));
                for (int i = from; i <= to; i++){
                    String strCode = String.valueOf(i);
                    String temp = "";
                    for(int tt=0;tt<2-strCode.length();tt++){
                        temp+='0';
                    }
                    strCode=codeFrom.substring(0, 1)+temp+strCode;
                    if (s.trim().length() > 0 && s.trim().startsWith(strCode)){
                        return true;
                    }
                }
            } else {
                String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                int indexFrom = str.indexOf(codeFrom.substring(0, 1));
                int indexTo = str.indexOf(codeTo.substring(0, 1));
                for (int i = indexFrom; i <= indexTo; i++){
                    if (i == indexFrom){
                        if (s.substring(0, 1).trim().equals(str.substring(indexFrom, indexFrom+1))){
                            int from = Integer.parseInt(codeFrom.substring(1, 3));
                            int index = Integer.parseInt(s.substring(1, 3));
                            if (index >= from){
                                return true;
                            }
                        }
                    } else if (i == indexTo){
                        if (s.substring(0, 1).trim().equals(str.substring(indexTo, indexTo+1))){
                            int to = Integer.parseInt(codeTo.substring(1, 3));
                            int index = Integer.parseInt(s.substring(1, 3));
                            if (index <= to){
                                return true;
                            }
                        }
                    } else {
                        String strRf = str.substring(i, i+1);
                        if (s.substring(0, 1).trim().equals(strRf)){
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * 自定义伤病代码指定长度为同一组（请录入伤病代码长度（数字））
     * @param s
     * @param c
     * @param contents
     * @return
     */
    public  static boolean  isEqualILLCodeLength(final String[] s,final String[] c,int contents){
        if(s == null || c == null || contents <= 0)
            return false;

        if (contents>= 4)contents += 1;
        for(int i=0;i<s.length;i++){
            if (!s[i].trim().equals("") && s[i].length() >= contents) {
                String sL = s[i].substring(0, contents);
                for(int j=0;j<c.length;j++){
                    if (!c[j].trim().equals("") && c[j].length() >= contents) {
                        String cL = c[j].substring(0, contents);
                        if(sL.equals(cL)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    /**
     * 判断2个字符串数组是否都在或开头在指定的用“、”分隔字符串组成的字符串数组内
     * 判断自定义伤病代码是否同一组
     * @param s
     * @param c
     * @param content 用“、”分隔字符串
     * @return
     */
    public static boolean isCheckAllSameIllCodeGroup(final String[] s,final String[] c,String content){

        if(s == null || c == null || content == null || content.trim().length() <= 0)
            return false;

        String[] array = content.split(SPERATION_SYMBOL);

        for(int i=0;i<s.length;i++){
            for(int j=0;j<c.length;j++){
                boolean sflag = false;
                boolean cflag = false;
                for(int k=0;k<array.length;k++){
                    if(s[i] != null && s[i].trim().length() > 0
                        && c[j] != null
                        && c[j].trim().length() > 0){
                        if (isIllcodeContain(s[i].trim(), array[k].trim())){
                            sflag = true;
                        }
                        if (isIllcodeContain(c[j].trim(), array[k].trim())){
                            cflag = true;
                        }
                    }

                    if (sflag && cflag){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 检查历史伤病代码是否包括
     *
     * @param s 如[A08]
     * @param c 如[A0800]
     * @return
     */
    public static boolean isCheckHtryIllCodeContains(final String[] s,final String c){
        return isCheckHtryIllCodeContains(s, c.split(SPERATION_SYMBOL));
    }

    /**
     * 检查历史伤病代码是否包括
     *
     * @param s 如[A08]
     * @param c 如[A0800]
     * @return
     */
    public static boolean isCheckHtryIllCodeContains(final String[] s,final String[] c){
        if(s == null || c == null){
            return false;
        }

        for(int i=0,len = s.length;i<len;i++){
            for(int j=0,len2 = c.length;j<len2;j++){
                if(s[i] != null && s[i].trim().length() > 0
                    && c[j] != null
                    && c[j].trim().length() > 0
                    && isIllcodeContain(s[i].trim(), c[j].trim())){
                    return true;
                }
            }
        }

        return false;
    }
}
