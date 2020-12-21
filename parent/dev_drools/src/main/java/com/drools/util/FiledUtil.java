package com.drools.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.drools.common.annotation.ActionMethodName;
import com.drools.common.DroolsConstants;
import com.drools.common.annotation.FieldMethodName;
import com.drools.common.annotation.FieldName;
import com.drools.model.RuleInterface;
import com.drools.vo.ClassVo;
import com.drools.vo.PropertyVo;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class FiledUtil {
    public static final  String CREATEREQ = "1";//建档
    public static final  String CONFIRMREQ = "2";//资料确认

    public static void main(String[] args) throws Exception {
        Map<String,Object> map =thirdApi("1");
        System.out.println(JSON.toJSONString(map));
    }

    public static Map<String,Object> thirdApi(String type) throws Exception {
        Map<String,Object> map = new HashMap<>();
        if(CREATEREQ.equals(type)){
            //请求报文
            Class<?> clazz1 = (Class<?>) Class.forName("com.drools.common.inters.createapi.CreateReq");
            List<PropertyVo> vos1 = getReqVo(clazz1);
            //返回报文
            Class<?> clazz2 = (Class<?>) Class.forName("com.drools.common.inters.createapi.CreateRes");
            List<PropertyVo> vos2 = getReqVo(clazz2);
            map.put("req",vos1);
            map.put("res",vos2);
        }else if(CONFIRMREQ.equals(type)){
            //请求报文
            Class<?> clazz1 = (Class<?>) Class.forName("com.drools.common.inters.confirmapi.ConfirmReq");
            List<PropertyVo> vos1 = getReqVo(clazz1);
            //返回报文
            Class<?> clazz2 = (Class<?>) Class.forName("com.drools.common.inters.confirmapi.ConfirmRes");
            List<PropertyVo> vos2 = getReqVo(clazz2);
            map.put("req",vos1);
            map.put("res",vos2);
        }
        return map;
    }
    public static Map<String,Object> thirdApi(RuleInterface ruleInterface) throws Exception {
        Map<String,Object> map = new HashMap<>();
        String reqPkg = ruleInterface.getInterfaceReqPkgname();
        String resPkg = ruleInterface.getInterfaceResPkgname();

        //请求报文
        Class<?> clazz1 = (Class<?>) Class.forName(reqPkg);
        List<PropertyVo> vos1 = getReqVo(clazz1);
        //返回报文
        Class<?> clazz2 = (Class<?>) Class.forName(resPkg);
        List<PropertyVo> vos2 = getReqVo(clazz2);
        map.put("req",vos1);
        map.put("res",vos2);
        map.put("msg",ruleInterface.getInterfaceReqMsg());
        return map;
    }

    /* *
     * vo.setFiledType("");//类型
     * vo.setChName("");//中文名称
     * vo.setFiledName("");//英文名称
     * vo.setFiledObjType("");//是否必填
     * @author ly
     * @modifyTime 2020/11/18 8:44:00
     */
    public static List<PropertyVo> getReqVo(Class<?> clazz) throws Exception {
        List<PropertyVo> vos = new ArrayList<>();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            PropertyVo vo = new PropertyVo();
            vo.setFiledName(field.getName());//英文名称

            if (field.isAnnotationPresent(FieldName.class)) {
                FieldName declaredAnnotation = field.getDeclaredAnnotation(FieldName.class);
                String filedType = declaredAnnotation.type();
                String name = declaredAnnotation.name();
                String remark = declaredAnnotation.remark();
                vo.setRemark(remark);//备注
                vo.setChName(name);////中文名称

                if(DroolsConstants.FieldType.OBJECT.equals(filedType)){//对象
                    Class<?> clazz1 = (Class<?>) Class.forName(field.getType().getName());
                    List<PropertyVo> children = getReqVo(clazz1);
                    vo.setChildren(children);
                }else if(DroolsConstants.FieldType.COLLECT.equals(filedType)){//集合
                    ParameterizedType pt = (ParameterizedType) field.getGenericType();
                    System.out.println(pt.getActualTypeArguments()[0]);
                    Class<?> actualTypeArgument = (Class<?>)pt.getActualTypeArguments()[0];
                    List<PropertyVo> children = getReqVo(actualTypeArgument);
                    vo.setChildren(children);
                    vo.setFiledType(field.getType().getSimpleName());//类型
                }else if(DroolsConstants.FieldType.BASE.equals(filedType)){//基本类型

                    vo.setFiledType(field.getType().getSimpleName());//类型
                    if(field.isAnnotationPresent(NotNull.class)|| field.isAnnotationPresent(NotBlank.class)){
                        vo.setFiledObjType("Y");//是否必填
                    }else{
                        vo.setFiledObjType("N");//是否必填
                    }
                }
            }
            vos.add(vo);
        }

        return vos;
    }

    /**
     * 获取实体类 @Column 的其中一个属性名称
     *
     * @param clazz
     * @return
     */
    public static List<PropertyVo> getChName(Class<?> clazz) throws Exception {
        List<PropertyVo> list = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(FieldName.class)) {
                /**
                 * 获取字段名
                 */
                FieldName declaredAnnotation = field.getDeclaredAnnotation(FieldName.class);
                String cnName = declaredAnnotation.name();
                System.out.println(cnName);
                PropertyVo vo = new PropertyVo();
                vo.setFiledName(field.getName());
                vo.setChName(cnName);

//                String enumCls = declaredAnnotation.enumCls();
                String enumCls = declaredAnnotation.enumCode();
                vo.setEnumName(enumCls);
//                if(StringUtils.isNotBlank(enumCls)){
//                    List<ClassVo> classVos = getAllEnum(enumCls);
//                    vo.setEnumList(classVos);
//                }
                String listCls = declaredAnnotation.listCls();
                vo.setListCls(listCls);

                String filedObjType = declaredAnnotation.type();
                vo.setFiledObjType(filedObjType);

                vo.setFiledType(field.getType().getSimpleName());
                list.add(vo);
            }
        }
        return list;
    }

    /**
     * 根据枚举的字符串获取枚举的值
     *
     * @param className 包名+类名
     * @return
     * @throws Exception
     */
    public static List<ClassVo> getAllEnum(String className) throws Exception {
        // 得到枚举类对象
        Class<Enum> clazz = (Class<Enum>) Class.forName(className);
        List<ClassVo> list = new ArrayList<ClassVo>();
        //获取所有枚举实例
        Enum[] enumConstants = clazz.getEnumConstants();
        //根据方法名获取方法
        Method getCode = clazz.getMethod("getCode");
        Method getName = clazz.getMethod("getName");
        ClassVo vo = null;
        for (Enum enum1 : enumConstants) {
            vo = new ClassVo();
            //执行枚举方法获得枚举实例对应的值
            vo.setName((String) getName.invoke(enum1));
            vo.setClassName((String) getCode.invoke(enum1));
            list.add(vo);
        }
        return list;
    }

    /* *
     * 解析方法入参
     * @author ly
     * @modifyTime 2020/11/19 9:07:00
     */
    public static ClassVo getMethodParams(String methodParams,String paramsDesc){
        ClassVo vo = new ClassVo();
        List<ClassVo> vos = new ArrayList<>();
        List<String> props = new ArrayList<>();

        if(methodParams.contains(DroolsConstants.Sysbols.COMMA)){
            String[] p = methodParams.split(DroolsConstants.Sysbols.COMMA);
            String[] pdesc = paramsDesc.split(DroolsConstants.Sysbols.COMMA);
            for (int i=0;i<p.length;i++) {
                String s = p[i];

                ClassVo paramsVo = new ClassVo();
                String[] para = s.split(DroolsConstants.Sysbols.EQUALS);
                paramsVo.setName(para[0]); //参数名称
                paramsVo.setType(para[1]);//参数类型
                paramsVo.setDesc(pdesc[i]);//参数备注
                vos.add(paramsVo);
                props.add(para[0]);
            }
        }else{
            String[] para = methodParams.split(DroolsConstants.Sysbols.EQUALS);
            ClassVo paramsVo = new ClassVo();
            paramsVo.setName(para[0]);
            paramsVo.setType(para[1]);
            paramsVo.setDesc(paramsDesc);
            vos.add(paramsVo);
            props.add(para[0]);
        }

        vo.setProps(props);
        vo.setParams(vos);
        return vo;
    }


    /* *
     * 获取条件方法
     * @author ly
     * @modifyTime 2020/11/19 8:48:00
     */
    public static List<ClassVo> getMethodName(String className) throws Exception {
        List<ClassVo> list = new ArrayList<ClassVo>();
        Class<?> clazz = Class.forName("com.drools.util.ClmUtil");
        Method[] methods = clazz.getMethods();
        ClassVo vo = null;
        //获取工具类里的方法
        for (Method method : methods) {
            //获取有注解的方法
            FieldMethodName fieldMethodName = method.getDeclaredAnnotation(FieldMethodName.class);
            if(fieldMethodName!=null){
                String methodName = fieldMethodName.name();
                String entityName = fieldMethodName.entityCls();
                String methodParams = fieldMethodName.params();
                String paramsDesc = fieldMethodName.paramsDesc();
                String methodType = fieldMethodName.methodType();

                //根据类匹配方法
                if(className.equals(entityName)){
                    vo = new ClassVo();
                    if(StringUtils.isNotBlank(methodParams)){
                        vo = getMethodParams(methodParams,paramsDesc);
                    }
                    Parameter[] params = method.getParameters();
                    StringBuffer mName = new StringBuffer(method.getName());
                    mName.append("(");
                    if(params.length!=0){
                        for (int i=0;i<params.length;i++) {
                            String parameterName = params[i].getName();
                            if(StringUtils.isNotBlank(methodParams) && vo.getProps().contains(parameterName)){
                                mName.append("$").append(parameterName).append("$");
                            }else{
                                if(methodType.equals("3")){
                                    mName.append("$").append(parameterName);
                                }else{
                                    mName.append(parameterName);
                                }
                            }
                            if(i!=(params.length-1)){
                                mName.append(",");
                            }
                            System.out.println("参数类型："+parameterName);
                        }
                    }
                    mName.append(")");

                    //执行枚举方法获得枚举实例对应的值
                    vo.setName(mName.toString());
                    vo.setClassName(methodName);
                    vo.setType(methodType);
                    list.add(vo);
                }
            }
        }
        return list;
    }


    /* *
     * 解析动作特殊参数
     * @author ly
     * @modifyTime 2020/11/20 9:16:00
     */
    public static Map<String,String> getSpeParam(String paramsStr){
        Map<String,String> result = new HashMap<>();

        if(paramsStr.indexOf(DroolsConstants.Sysbols.COMMA)>0){
            String[] paras = paramsStr.split(DroolsConstants.Sysbols.COMMA);
            for (String para : paras) {
                //判断参数是否有=号
                if(para.indexOf(DroolsConstants.Sysbols.EQUALS)>0){
                    String[] para1 = para.split(DroolsConstants.Sysbols.EQUALS);
                    result.put(para1[0],para1[1]);
                }else{
                    result.put(para,para);
                }

            }
        }else{
            if(paramsStr.indexOf(DroolsConstants.Sysbols.EQUALS)>0){
                String[] para1 = paramsStr.split(DroolsConstants.Sysbols.EQUALS);
                result.put(para1[0],para1[1]);
            }else{
                result.put(paramsStr,paramsStr);
            }
        }
        return result;
    }


    /* *
     * 获取动作
     * @author ly
     * @modifyTime 2020/11/19 13:14:00
     */
    public static List<ClassVo> getActionMethodName(String className) throws Exception {
        List<ClassVo> list = new ArrayList<ClassVo>();
        Class<?> clazz = Class.forName(className);
        Method[] methods = clazz.getMethods();
        ClassVo vo = null;
        for (Method method : methods) {
            ActionMethodName actionMethodName = method.getDeclaredAnnotation(ActionMethodName.class);
            if(actionMethodName!=null){
                String methodName = actionMethodName.name();
                String props = actionMethodName.props();
                String methodParams = actionMethodName.params();
                String specialParams = actionMethodName.specialParams();//特殊对象参数
                Map<String,String> speParams = new HashMap<>();
                if(StringUtils.isNotBlank(specialParams)){
                    speParams = getSpeParam(specialParams);
                }

                vo = new ClassVo();

                //执行枚举方法获得枚举实例对应的值
                StringBuffer mName = new StringBuffer(method.getName());
                mName.append("(");

                if(StringUtils.isNotBlank(methodParams)){
                    vo = getMethodParams(methodParams,props);
                }
                Parameter[] params = method.getParameters();
                if(params.length!=0){
                    for (int i=0;i<params.length;i++) {
                        String parameterName = params[i].getName();

                        //前台输入 将需前台输入的参数封装成$params$，用于前台替换
                        if(StringUtils.isNotBlank(methodParams) && vo.getProps().contains(parameterName)){
                            mName.append("$").append(parameterName).append("$");
                        }else{
                            //直接传入 ，如果特殊属性，则存入特殊属性： $policy 、 $policy.getChdnum()
                            if(speParams.keySet().contains(parameterName)){
                                mName.append(speParams.get(parameterName));
                            }else{
                                mName.append(parameterName);
                            }
                        }
                        if(i!=(params.length-1)){
                            mName.append(",");
                        }
                        System.out.println("参数类型："+parameterName);
                    }
                    vo.setType("1");
                }else{
                    vo.setType("0");
                }
                mName.append(")");
                vo.setClassName(methodName);//方法中文名称
                vo.setName(mName.toString());
                list.add(vo);
            }
        }
        return list;
    }

    public static String getJsonStr(String className) throws Exception{
        Class<?> clazz = Class.forName(className);
        Object obj = clazz.getConstructor().newInstance();
        return JSON.toJSONString(obj, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 获取实体标识(例如：com.drools.model.TestRule  最后得到 testRule)
     */
    public static String getActionClazzIdentify(String actionClass) {
        int index = actionClass.lastIndexOf(".");
        return actionClass.substring(index + 1).substring(0, 1).toLowerCase() +
            actionClass.substring(index + 1).substring(1);
    }

    /**
     * 方法说明:获取实体类名称
     */
    public static String getEntityClazz(String pkgName){
        if(StringUtils.isNotBlank(pkgName)){
            int index =  pkgName.lastIndexOf(".");
            return pkgName.substring(index+1);
        }
        return "";
    }

    public static String getEntityIdentify(String pkgName) {
        int index = pkgName.lastIndexOf(".");
        return pkgName.substring(index + 1).substring(0, 1).toLowerCase() +
            pkgName.substring(index + 1).substring(1);
    }
}
