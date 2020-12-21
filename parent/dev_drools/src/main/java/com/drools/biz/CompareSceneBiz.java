package com.drools.biz;

import com.drools.util.CompareActionUtil;
import com.drools.util.CompareConditionUtil;
import com.drools.util.ComparePropertyUtil;
import com.drools.util.CompareUtil;
import com.drools.vo.PublishRuleSceneInfoVo;
import com.drools.vo.PublishSceneRuleInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CompareSceneBiz {
    //换行符
    private static final String lineSeparator = System.getProperty("line.separator");

    /* *
     * 版本比较 
     * @author ly
     * @modifyTime 2020/11/20 14:11:00
     */
    public String compare(Map<String,Object> map){
        StringBuffer resultStr = new StringBuffer("");
        PublishRuleSceneInfoVo vo1 = (PublishRuleSceneInfoVo) map.get("vo1");
        PublishRuleSceneInfoVo vo2 = (PublishRuleSceneInfoVo) map.get("vo2");

        List<PublishSceneRuleInfoVo> list1 = vo1.getRuleInfoList();
        List<PublishSceneRuleInfoVo> list2 = vo2.getRuleInfoList();

        compareRuleList(resultStr,list1,list2);

        log.info(resultStr.toString());
        String result = resultStr.toString();
        if(StringUtils.isBlank(result)){
            result = "尚未发现有更新信息";
        }
        return result;
    }

    /* *
     * 判断两个版本的规则是有新增还是删除的
     * @param resultStr
     * @param list1 原版本
     * @param list2 变更版本
     * @author ly
     * @modifyTime 2020/11/12 17:32:00
     */
    public void compareRuleList(StringBuffer resultStr,List<PublishSceneRuleInfoVo> list1,List<PublishSceneRuleInfoVo> list2){

        //如果列表1为空，列表2不为空，则规则都是删除的
        if(CollectionUtils.isEmpty(list1) && !CollectionUtils.isEmpty(list2)){
            for (PublishSceneRuleInfoVo publishSceneRuleInfoVo : list2) {
                resultStr.append("删除规则【").append(publishSceneRuleInfoVo.getRuleInfo().getRuleName()).append("】,编号")
                    .append(publishSceneRuleInfoVo.getRuleInfo().getRuleCode()).append(";").append(lineSeparator);
            }
        }else if(!CollectionUtils.isEmpty(list1) && CollectionUtils.isEmpty(list2)){
            //如果列表2为空，列表1不为空，则规则新增
            for (PublishSceneRuleInfoVo publishSceneRuleInfoVo : list1) {
                resultStr.append("新增规则【").append(publishSceneRuleInfoVo.getRuleInfo().getRuleName()).append("】,编号")
                    .append(publishSceneRuleInfoVo.getRuleInfo().getRuleCode()).append(";").append(lineSeparator);
            }
        }else if(!CollectionUtils.isEmpty(list1) && !CollectionUtils.isEmpty(list2)){
            //判断规则的新增和删除
            compareRule(resultStr,list1,list2);

            //判断规则的更新
            compareUpdate(resultStr,list1,list2);
        }
    }

    /* *
     * 比较规则CODE未改变，其他是否有更新
     * @param resultStr
     * @param list1 原版本
     * @param list2 变更版本
     * @author ly
     * @modifyTime 2020/11/12 17:59:00
     */
    private void compareUpdate(StringBuffer resultStr, List<PublishSceneRuleInfoVo> list1, List<PublishSceneRuleInfoVo> list2) {
        List<PublishSceneRuleInfoVo> vos = CompareUtil.getSameList(list1,list2,CompareUtil.RULE);
        if(!CollectionUtils.isEmpty(vos)){
            for (PublishSceneRuleInfoVo vo : vos) {
                PublishSceneRuleInfoVo beforeUpdateObj = compareByRuleCode(vo.getRuleInfo().getRuleCode(),list1);
                PublishSceneRuleInfoVo updateedObj = compareByRuleCode(vo.getRuleInfo().getRuleCode(),list2);
                //比较两个对象数据是否更新
                compareVo(beforeUpdateObj,updateedObj,resultStr);
            }
        }
    }

    /* *
     * 比较两个版本的规则
     * @param beforeUpdateObj
     * @param updateedObj
     * @param resultStr
     * @author ly
     * @modifyTime 2020/11/13 8:31:00
     */
    public void compareVo(PublishSceneRuleInfoVo beforeUpdateObj,PublishSceneRuleInfoVo updateedObj,StringBuffer resultStr){
        //比较规则名称
        if(!beforeUpdateObj.getRuleInfo().getRuleName().equals(updateedObj.getRuleInfo().getRuleName())){
            resultStr.append("规则名称由【").append(beforeUpdateObj.getRuleInfo().getRuleName()).append("】更新为【")
                .append(updateedObj.getRuleInfo().getRuleName()).append("】").append(";").append(lineSeparator);
        }

        //比较属性
        ComparePropertyUtil.comparePropertyList(resultStr,beforeUpdateObj.getRulePropertyInfos(),updateedObj.getRulePropertyInfos());
        //比较条件
        CompareConditionUtil.compareConditionList(resultStr,beforeUpdateObj.getRuleConditionInfos(),updateedObj.getRuleConditionInfos());
        //比较动作
        CompareActionUtil.compareActionList(resultStr,beforeUpdateObj.getRuleActionInfos(),updateedObj.getRuleActionInfos());
    }



    /* *
     * 根据规则CODE找出list中的规则对象
     * @author ly
     * @modifyTime 2020/11/13 8:32:00
     */
    public PublishSceneRuleInfoVo compareByRuleCode(String ruleCode,List<PublishSceneRuleInfoVo> list){
        for (PublishSceneRuleInfoVo publishSceneRuleInfoVo : list) {
            if(publishSceneRuleInfoVo.getRuleInfo().getRuleCode().equals(ruleCode)){
                return publishSceneRuleInfoVo;
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
    public void compareRule(StringBuffer resultStr,List<PublishSceneRuleInfoVo> list1,List<PublishSceneRuleInfoVo> list2){
        List<PublishSceneRuleInfoVo> vos1 = CompareUtil.getAddaListThanbList(list2,list1,CompareUtil.RULE);

        if(!CollectionUtils.isEmpty(vos1)){
            for (PublishSceneRuleInfoVo publishSceneRuleInfoVo : vos1) {
                resultStr.append("新增规则【").append(publishSceneRuleInfoVo.getRuleInfo().getRuleName()).append("】,编号")
                    .append(publishSceneRuleInfoVo.getRuleInfo().getRuleCode()).append(";").append(lineSeparator);
            }
        }
        List<PublishSceneRuleInfoVo> vos2 = CompareUtil.getReduceaListThanbList(list2,list1,CompareUtil.RULE);

        if(!CollectionUtils.isEmpty(vos2)){
            for (PublishSceneRuleInfoVo publishSceneRuleInfoVo : vos2) {
                resultStr.append("删除规则【").append(publishSceneRuleInfoVo.getRuleInfo().getRuleName()).append("】,编号")
                    .append(publishSceneRuleInfoVo.getRuleInfo().getRuleCode()).append(";").append(lineSeparator);
            }

        }
    }

}
