package com.drools.util;

import com.drools.model.publish.PublishRuleActionInfo;
import org.springframework.util.CollectionUtils;

import java.util.List;

/* *
 * 规则条件比较
 * @author ly
 * @modifyTime 2020/11/13 8:45:00
 */
public class CompareActionUtil {
    //换行符
    private static final String lineSeparator = System.getProperty("line.separator");
    
    /* *
     * 判断两个版本的动作是有新增还是删除的
     * @param resultStr
     * @param list1 原版本
     * @param list2 变更版本
     * @author ly
     * @modifyTime 2020/11/12 17:32:00
     */
    public static void compareActionList(StringBuffer resultStr, List<PublishRuleActionInfo> list1, List<PublishRuleActionInfo> list2){

        //如果列表1为空，列表2不为空，则规则都是删除的
        if(CollectionUtils.isEmpty(list1) && !CollectionUtils.isEmpty(list2)){
            for (PublishRuleActionInfo publishRuleActionInfo : list2) {
                resultStr.append("删除规则动作【").append(publishRuleActionInfo.getActionName()).append("】,表达式：")
                    .append(publishRuleActionInfo.getActionExpression()).append(";").append(lineSeparator);
            }
        }else if(!CollectionUtils.isEmpty(list1) && CollectionUtils.isEmpty(list2)){
            //如果列表2为空，列表1不为空，则规则新增
            for (PublishRuleActionInfo publishRuleActionInfo : list1) {
                resultStr.append("新增规则动作【").append(publishRuleActionInfo.getActionName()).append("】,表达式：")
                    .append(publishRuleActionInfo.getActionExpression()).append(";").append(lineSeparator);
            }
        }else if(!CollectionUtils.isEmpty(list1) && !CollectionUtils.isEmpty(list2)){
            //判断新增和删除
            compareAction(resultStr,list1,list2);

            //判断更新
            compareActionUpdate(resultStr,list1,list2);
        }
    }

    private static void compareActionUpdate(StringBuffer resultStr, List<PublishRuleActionInfo> list1, List<PublishRuleActionInfo> list2) {
        List<PublishRuleActionInfo> vos = CompareUtil.getSameList(list1,list2,CompareUtil.ACTION);
        if(!CollectionUtils.isEmpty(vos)){
            for (PublishRuleActionInfo vo : vos) {
                PublishRuleActionInfo beforeUpdateObj = compareByCode(vo.getActionCode(),list1);
                PublishRuleActionInfo updateedObj = compareByCode(vo.getActionCode(),list2);
                //比较两个对象数据是否更新
                compareVo(beforeUpdateObj,updateedObj,resultStr);
            }
        }
    }

    /* *
     * 比较两个版本的动作
     * @param beforeUpdateObj
     * @param updateedObj
     * @param resultStr
     * @author ly
     * @modifyTime 2020/11/13 8:31:00
     */
    public static void compareVo(PublishRuleActionInfo beforeUpdateObj,PublishRuleActionInfo updateedObj,StringBuffer resultStr){
        //比较条件
        if(!beforeUpdateObj.getActionExpression().equals(updateedObj.getActionExpression())){
            resultStr.append("规则动作【").append(beforeUpdateObj.getActionName()).append("】")
                .append("表达式由【").append(beforeUpdateObj.getActionExpression()).append("】(").append(beforeUpdateObj.getActionDesc()).append(")")
                .append("更新为【").append(updateedObj.getActionExpression()).append("】")
                .append("(").append(updateedObj.getActionDesc()).append(")").append(";").append(lineSeparator);
        }
    }



    /* *
     * 根据ID找出list中的属性对象
     * @author ly
     * @modifyTime 2020/11/13 8:32:00
     */
    public static PublishRuleActionInfo compareByCode(String actionCode,List<PublishRuleActionInfo> list){
        for (PublishRuleActionInfo publishRuleActionInfo : list) {
            if(publishRuleActionInfo.getActionCode().equals(actionCode)){
                return publishRuleActionInfo;
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
    public static void compareAction(StringBuffer resultStr,List<PublishRuleActionInfo> list1,List<PublishRuleActionInfo> list2){
        List<PublishRuleActionInfo> vos1 = CompareUtil.getAddaListThanbList(list2,list1,CompareUtil.ACTION);

        if(!CollectionUtils.isEmpty(vos1)){
            for (PublishRuleActionInfo publishRuleActionInfo : vos1) {
                resultStr.append("新增规则动作【").append(publishRuleActionInfo.getActionName()).append("】,表达式：")
                    .append(publishRuleActionInfo.getActionExpression()).append(";").append(lineSeparator);
            }
        }
        List<PublishRuleActionInfo> vos2 = CompareUtil.getReduceaListThanbList(list2,list1,CompareUtil.ACTION);

        if(!CollectionUtils.isEmpty(vos2)){
            for (PublishRuleActionInfo publishRuleActionInfo : vos2) {
                resultStr.append("删除规则动作【").append(publishRuleActionInfo.getActionName()).append("】,表达式：")
                    .append(publishRuleActionInfo.getActionExpression()).append(";").append(lineSeparator);
            }

        }
    }
}
