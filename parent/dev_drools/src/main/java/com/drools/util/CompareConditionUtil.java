package com.drools.util;

import com.drools.model.publish.PublishRuleConditionInfo;
import com.drools.vo.RulePropertyRelInfoVo;
import org.springframework.util.CollectionUtils;

import java.util.List;

/* *
 * 规则条件比较
 * @author ly
 * @modifyTime 2020/11/13 8:45:00
 */
public class CompareConditionUtil {
    //换行符
    private static final String lineSeparator = System.getProperty("line.separator");

    /* *
     * 判断两个版本的条件是有新增还是删除的
     * @param resultStr
     * @param list1 原版本
     * @param list2 变更版本
     * @author ly
     * @modifyTime 2020/11/12 17:32:00
     */
    public static void compareConditionList(StringBuffer resultStr, List<PublishRuleConditionInfo> list1, List<PublishRuleConditionInfo> list2){

        //如果列表1为空，列表2不为空，则规则都是删除的
        if(CollectionUtils.isEmpty(list1) && !CollectionUtils.isEmpty(list2)){
            for (PublishRuleConditionInfo publishRuleConditionInfo : list2) {
                resultStr.append("删除规则条件【").append(publishRuleConditionInfo.getConditionName()).append("】,表达式：")
                    .append(publishRuleConditionInfo.getConditionExpression()).append(";").append(lineSeparator);
            }
        }else if(!CollectionUtils.isEmpty(list1) && CollectionUtils.isEmpty(list2)){
            //如果列表2为空，列表1不为空，则规则新增
            for (PublishRuleConditionInfo publishRuleConditionInfo : list1) {
                resultStr.append("新增规则条件【").append(publishRuleConditionInfo.getConditionName()).append("】,表达式：")
                    .append(publishRuleConditionInfo.getConditionExpression()).append(";").append(lineSeparator);
            }
        }else if(!CollectionUtils.isEmpty(list1) && !CollectionUtils.isEmpty(list2)){
            //判断新增和删除
            compareCondition(resultStr,list1,list2);

            //判断更新
            compareConditionUpdate(resultStr,list1,list2);
        }
    }

    private static void compareConditionUpdate(StringBuffer resultStr, List<PublishRuleConditionInfo> list1, List<PublishRuleConditionInfo> list2) {
        List<PublishRuleConditionInfo> vos = CompareUtil.getSameList(list1,list2,CompareUtil.CONDITION);
        if(!CollectionUtils.isEmpty(vos)){
            for (PublishRuleConditionInfo vo : vos) {
                PublishRuleConditionInfo beforeUpdateObj = compareByCode(vo.getConditionCode(),list1);
                PublishRuleConditionInfo updateedObj = compareByCode(vo.getConditionCode(),list2);
                //比较两个对象数据是否更新
                compareVo(beforeUpdateObj,updateedObj,resultStr);
            }
        }
    }

    /* *
     * 比较两个版本的条件
     * @param beforeUpdateObj
     * @param updateedObj
     * @param resultStr
     * @author ly
     * @modifyTime 2020/11/13 8:31:00
     */
    public static void compareVo(PublishRuleConditionInfo beforeUpdateObj,PublishRuleConditionInfo updateedObj,StringBuffer resultStr){
        //比较条件
        if(!beforeUpdateObj.getConditionExpression().equals(updateedObj.getConditionExpression())){
            resultStr.append("规则条件【").append(beforeUpdateObj.getConditionName()).append("】")
                .append("表达式由【").append(beforeUpdateObj.getConditionExpression()).append("】(").append(beforeUpdateObj.getConditionDesc()).append(")")
                .append("更新为【").append(updateedObj.getConditionExpression()).append("】")
                .append("(").append(updateedObj.getConditionDesc()).append(")").append(";").append(lineSeparator);
        }
    }



    /* *
     * 根据ID找出list中的属性对象
     * @author ly
     * @modifyTime 2020/11/13 8:32:00
     */
    public static PublishRuleConditionInfo compareByCode(String conditionCode,List<PublishRuleConditionInfo> list){
        for (PublishRuleConditionInfo publishRuleConditionInfo : list) {
            if(publishRuleConditionInfo.getConditionCode().equals(conditionCode)){
                return publishRuleConditionInfo;
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
    public static void compareCondition(StringBuffer resultStr,List<PublishRuleConditionInfo> list1,List<PublishRuleConditionInfo> list2){
        List<PublishRuleConditionInfo> vos1 = CompareUtil.getAddaListThanbList(list2,list1,CompareUtil.CONDITION);

        if(!CollectionUtils.isEmpty(vos1)){
            for (PublishRuleConditionInfo publishRuleConditionInfo : vos1) {
                resultStr.append("新增规则条件【").append(publishRuleConditionInfo.getConditionName()).append("】,表达式：")
                    .append(publishRuleConditionInfo.getConditionExpression()).append(";").append(lineSeparator);
            }
        }
        List<PublishRuleConditionInfo> vos2 = CompareUtil.getReduceaListThanbList(list2,list1,CompareUtil.CONDITION);

        if(!CollectionUtils.isEmpty(vos2)){
            for (PublishRuleConditionInfo publishRuleConditionInfo : vos2) {
                resultStr.append("删除规则条件【").append(publishRuleConditionInfo.getConditionName()).append("】,表达式：")
                    .append(publishRuleConditionInfo.getConditionExpression()).append(";").append(lineSeparator);
            }

        }
    }
}
