package com.drools.util;

import com.drools.common.DroolsConstants;
import com.drools.model.RuleActionInfo;
import com.drools.model.RuleConditionInfo;
import com.drools.model.publish.PublishRuleActionInfo;
import com.drools.model.publish.PublishRuleConditionInfo;
import com.drools.vo.PublishSceneRuleInfoVo;
import com.drools.vo.RulePropertyRelInfoVo;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompareUtil {

    public static final String RULE = "1";
    public static final String ACTION = "2";
    public static final String CONDITION = "3";
    public static final String PROPERTY = "4";

    /**
     * 计算列表aList相对于bList的增加的情况，兼容任何类型元素的列表数据结构
     *
     * @param aList 本列表
     * @param bList 对照列表
     * @return 返回增加的元素组成的列表
     */
    public static <E> List<E> getAddaListThanbList(List<E> aList, List<E> bList, String type) {
        List<E> addList = new ArrayList<E>();
        for (int i = 0; i < aList.size(); i++) {
            if (!myListContains(bList, aList.get(i), type)) {
                addList.add(aList.get(i));
            }
        }
        return addList;
    }

    /**
     * 计算列表aList相对于bList的减少的情况，兼容任何类型元素的列表数据结构
     *
     * @param aList 本列表
     * @param bList 对照列表
     * @return 返回减少的元素组成的列表
     */
    public static <E> List<E> getReduceaListThanbList(List<E> aList, List<E> bList, String type) {
        List<E> reduceaList = new ArrayList<E>();
        for (int i = 0; i < bList.size(); i++) {
            if (!myListContains(aList, bList.get(i), type)) {
                reduceaList.add(bList.get(i));
            }
        }
        return reduceaList;
    }

    /**
     * 计算列表相同的情况，兼容任何类型元素的列表数据结构
     *
     * @param aList 本列表
     * @param bList 对照列表
     * @return 返回相同列表
     */
    public static <E> List<E> getSameList(List<E> aList, List<E> bList, String type) {
        List<E> reduceaList = new ArrayList<E>();
        for (int i = 0; i < bList.size(); i++) {
            if (myListContains(aList, bList.get(i), type)) {
                reduceaList.add(bList.get(i));
            }
        }
        return reduceaList;
    }

    /**
     * 判断元素element是否是sourceList列表中的一个子元素
     *
     * @param sourceList 源列表
     * @param element    待判断的包含元素
     * @return 包含返回 true，不包含返回 false
     */
    private static <E> boolean myListContains(List<E> sourceList, E element, String type) {
        if (sourceList == null || element == null) {
            return false;
        }
        if (sourceList.isEmpty()) {
            return false;
        }
        if (RULE.equals(type)) {//规则
            PublishSceneRuleInfoVo el = (PublishSceneRuleInfoVo) element;
            for (E tip : sourceList) {
                PublishSceneRuleInfoVo vo = (PublishSceneRuleInfoVo) tip;
                if (el.getRuleInfo().getRuleCode().equals(vo.getRuleInfo().getRuleCode())) {
                    return true;
                }
            }
        }else if (ACTION.equals(type)) {//动作
            PublishRuleActionInfo el = (PublishRuleActionInfo) element;
            for (E tip : sourceList) {
                PublishRuleActionInfo vo = (PublishRuleActionInfo) tip;
                if (el.getActionCode().equals(vo.getActionCode())) {
                    return true;
                }
            }
        }else if (CONDITION.equals(type)) {//条件
            PublishRuleConditionInfo el = (PublishRuleConditionInfo) element;
            for (E tip : sourceList) {
                PublishRuleConditionInfo vo = (PublishRuleConditionInfo) tip;
                if (el.getConditionCode().equals(vo.getConditionCode())) {
                    return true;
                }
            }
        }else if (PROPERTY.equals(type)) {//属性
            RulePropertyRelInfoVo el = (RulePropertyRelInfoVo) element;
            for (E tip : sourceList) {
                RulePropertyRelInfoVo vo = (RulePropertyRelInfoVo) tip;
                if (el.getRulePropertyId().equals(vo.getRulePropertyId())) {
                    return true;
                }
            }
        }
        return false;
    }


    /* *
     * 获取动作比条件中多的实体 
     * @author ly
     * @modifyTime 2020/11/13 9:56:00
     */
    public static  List<RuleActionInfo> getAddActionList(List<RuleActionInfo> aList, List<RuleConditionInfo> bList) {
        List<RuleActionInfo> addList = new ArrayList<RuleActionInfo>();
        for (int i = 0; i < aList.size(); i++) {
            if (!myListContains(bList, aList.get(i))) {
                addList.add(aList.get(i));
            }
        }
        return addList;
    }

    private static  boolean myListContains(List<RuleConditionInfo> sourceList, RuleActionInfo element) {
        if (sourceList == null || element == null) {
            return false;
        }
        if (sourceList.isEmpty()) {
            return false;
        }
        for (RuleConditionInfo vo : sourceList) {
            if (element.getLeftEntityId().equals(vo.getLeftEntityId())) {
                return true;
            }
        }
        return false;
    }
    
    /* *
     * 获取已发布版本中动作比条件中多的实体
     * @author ly
     * @modifyTime 2020/11/13 10:04:00
     */
    public static  List<PublishRuleActionInfo> getAddPublishActionList(List<PublishRuleActionInfo> aList, List<PublishRuleConditionInfo> bList) {
        List<PublishRuleActionInfo> addList = new ArrayList<PublishRuleActionInfo>();
        for (int i = 0; i < aList.size(); i++) {
            if (!myListContains(bList, aList.get(i))) {
                addList.add(aList.get(i));
            }
        }
        return addList;
    }

    private static  boolean myListContains(List<PublishRuleConditionInfo> sourceList, PublishRuleActionInfo element) {
        if (sourceList == null || element == null) {
            return false;
        }
        if (sourceList.isEmpty()) {
            return false;
        }
        for (PublishRuleConditionInfo vo : sourceList) {
            if (element.getLeftEntityId().equals(vo.getLeftEntityId())) {
                return true;
            }
        }
        return false;
    }

    public static List<Long> getConditionId(List<RuleConditionInfo> list){
        List<Long> result = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            for (RuleConditionInfo ruleConditionInfo : list) {
                if(DroolsConstants.ConditionType.PROPERTY.equals(ruleConditionInfo.getConditionType())){
                    if(!result.contains(ruleConditionInfo.getLeftEntityId())){
                        result.add(ruleConditionInfo.getLeftEntityId());
                    }
                    if(!result.contains(ruleConditionInfo.getRightEntityId())){
                        result.add(ruleConditionInfo.getRightEntityId());
                    }
                }else{
                    if(!result.contains(ruleConditionInfo.getLeftEntityId())){
                        result.add(ruleConditionInfo.getLeftEntityId());
                    }
                }
            }
        }
        return result;
    }

    public static List<Long> getPublishConditionId(List<PublishRuleConditionInfo> list){
        List<Long> result = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            for (PublishRuleConditionInfo ruleConditionInfo : list) {
                if(DroolsConstants.ConditionType.PROPERTY.equals(ruleConditionInfo.getConditionType())){
                    if(!result.contains(ruleConditionInfo.getLeftEntityId())){
                        result.add(ruleConditionInfo.getLeftEntityId());
                    }
                    if(!result.contains(ruleConditionInfo.getRightEntityId())){
                        result.add(ruleConditionInfo.getRightEntityId());
                    }
                }else{
                    if(!result.contains(ruleConditionInfo.getLeftEntityId())){
                        result.add(ruleConditionInfo.getLeftEntityId());
                    }
                }
            }
        }
        return result;
    }
}
