package com.drools.util;

import com.drools.vo.RulePropertyRelInfoVo;
import org.springframework.util.CollectionUtils;

import java.util.List;

/* *
 * 规则属性比较 
 * @author ly
 * @modifyTime 2020/11/13 8:47:00
 */
public class ComparePropertyUtil {
    //换行符
    private static final String lineSeparator = System.getProperty("line.separator");
    
    /* *
     * 判断两个版本的DROOLS属性是有新增还是删除的
     * @param resultStr
     * @param list1 原版本
     * @param list2 变更版本
     * @author ly
     * @modifyTime 2020/11/12 17:32:00
     */
    public static void comparePropertyList(StringBuffer resultStr, List<RulePropertyRelInfoVo> list1, List<RulePropertyRelInfoVo> list2){

        //如果列表1为空，列表2不为空，则规则都是删除的
        if(CollectionUtils.isEmpty(list1) && !CollectionUtils.isEmpty(list2)){
            for (RulePropertyRelInfoVo rulePropertyRelInfoVo : list2) {
                resultStr.append("删除DROOLS属性【").append(rulePropertyRelInfoVo.getRulePropertyIdentify()).append("】,值：")
                    .append(rulePropertyRelInfoVo.getRulePropertyValue()).append(";").append(lineSeparator);
            }
        }else if(!CollectionUtils.isEmpty(list1) && CollectionUtils.isEmpty(list2)){
            //如果列表2为空，列表1不为空，则规则新增
            for (RulePropertyRelInfoVo rulePropertyRelInfoVo : list1) {
                resultStr.append("新增DROOLS属性【").append(rulePropertyRelInfoVo.getRulePropertyIdentify()).append("】,值：")
                    .append(rulePropertyRelInfoVo.getRulePropertyValue()).append(";").append(lineSeparator);
            }
        }else if(!CollectionUtils.isEmpty(list1) && !CollectionUtils.isEmpty(list2)){
            //判断规则的新增和删除
            compareProperty(resultStr,list1,list2);

            //判断规则的更新
            comparePropertyUpdate(resultStr,list1,list2);
        }
    }

    private static void comparePropertyUpdate(StringBuffer resultStr, List<RulePropertyRelInfoVo> list1, List<RulePropertyRelInfoVo> list2) {
        List<RulePropertyRelInfoVo> vos = CompareUtil.getSameList(list1,list2,CompareUtil.PROPERTY);
        if(!CollectionUtils.isEmpty(vos)){
            for (RulePropertyRelInfoVo vo : vos) {
                RulePropertyRelInfoVo beforeUpdateObj = compareById(vo.getRulePropertyId(),list1);
                RulePropertyRelInfoVo updateedObj = compareById(vo.getRulePropertyId(),list2);
                //比较两个对象数据是否更新
                compareVo(beforeUpdateObj,updateedObj,resultStr);
            }
        }
    }

    /* *
     * 比较两个版本的属性
     * @param beforeUpdateObj
     * @param updateedObj
     * @param resultStr
     * @author ly
     * @modifyTime 2020/11/13 8:31:00
     */
    public static void compareVo(RulePropertyRelInfoVo beforeUpdateObj,RulePropertyRelInfoVo updateedObj,StringBuffer resultStr){
        //比较属性
        if(!beforeUpdateObj.getRulePropertyValue().equals(updateedObj.getRulePropertyValue())){
            resultStr.append("属性【").append(beforeUpdateObj.getRulePropertyIdentify()).append("】,属性值由【").append(beforeUpdateObj.getRulePropertyValue()).append("】更新为【")
                .append(updateedObj.getRulePropertyValue()).append("】").append(";").append(lineSeparator);
        }
    }



    /* *
     * 根据ID找出list中的属性对象
     * @author ly
     * @modifyTime 2020/11/13 8:32:00
     */
    public static RulePropertyRelInfoVo compareById(Long rulePropertyId,List<RulePropertyRelInfoVo> list){
        for (RulePropertyRelInfoVo rulePropertyRelInfoVo : list) {
            if(rulePropertyRelInfoVo.getRulePropertyId()==rulePropertyId){
                return rulePropertyRelInfoVo;
            }
        }
        return null;
    }


    /* *
     *
     * @param resultStr
     * @param list1 原版本
     * @param list2 变更版本
     * @author ly
     * @modifyTime 2020/11/12 17:32:00
     */
    public static void compareProperty(StringBuffer resultStr,List<RulePropertyRelInfoVo> list1,List<RulePropertyRelInfoVo> list2){
        List<RulePropertyRelInfoVo> vos1 = CompareUtil.getAddaListThanbList(list2,list1,CompareUtil.PROPERTY);

        if(!CollectionUtils.isEmpty(vos1)){
            for (RulePropertyRelInfoVo rulePropertyRelInfoVo : vos1) {
                resultStr.append("新增DROOLS属性【").append(rulePropertyRelInfoVo.getRulePropertyIdentify()).append("】,值：")
                    .append(rulePropertyRelInfoVo.getRulePropertyValue()).append(";").append(lineSeparator);
            }
        }
        List<RulePropertyRelInfoVo> vos2 = CompareUtil.getReduceaListThanbList(list2,list1,CompareUtil.PROPERTY);

        if(!CollectionUtils.isEmpty(vos2)){
            for (RulePropertyRelInfoVo rulePropertyRelInfoVo : vos2) {
                resultStr.append("删除DROOLS属性【").append(rulePropertyRelInfoVo.getRulePropertyIdentify()).append("】,值：")
                    .append(rulePropertyRelInfoVo.getRulePropertyValue()).append(";").append(lineSeparator);
            }

        }
    }
}
