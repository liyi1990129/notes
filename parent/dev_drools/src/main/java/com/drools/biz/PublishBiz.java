package com.drools.biz;

import com.drools.biz.publish.PublishRuleSceneBiz;
import com.drools.common.DroolsConstants;
import com.drools.common.ObjectRestResponse;
import com.drools.mapper.*;
import com.drools.mapper.publish.*;
import com.drools.model.*;
import com.drools.model.publish.*;
import com.drools.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PublishBiz {
    @Resource
    PublishRuleActionInfoMapper publishRuleActionInfoMapper;
    @Resource
    PublishRuleInfoMapper publishRuleInfoMapper;
    @Resource
    PublishRulePropertyRelInfoMapper publishRulePropertyRelInfoMapper;
    @Resource
    PublishRuleSceneEntityRelInfoMapper publishRuleSceneEntityRelInfoMapper;
    @Resource
    PublishRuleSceneInfoMapper publishRuleSceneInfoMapper;
    @Resource
    PublishRuleConditionInfoMapper publishRuleConditionInfoMapper;

    @Resource
    RuleActionInfoBiz ruleActionInfoBiz;
    @Resource
    RuleInfoBiz ruleInfoBiz;
    @Resource
    RulePropertyRelInfoMapper rulePropertyRelInfoMapper;
    @Resource
    RuleSceneEntityRelBiz ruleSceneEntityRelBiz;
    @Resource
    RuleSceneBiz ruleSceneBiz;
    @Resource
    RuleConditionBiz ruleConditionBiz;
    @Resource
    PublishRuleSceneBiz publishRuleSceneBiz;
    @Resource
    CompareSceneBiz compareSceneBiz;

    /* *
     * 发布版本
     * 更新场景版本
     * 根据场景ID 复制一份数据到 已发布规则表中
     * @author ly
     * @modifyTime 2020/11/12 13:13:00
     */
    public void publishScene(Long id){
        //场景
        RuleSceneInfo ruleSceneInfo = ruleSceneBiz.getInfoById(id);
        ruleSceneInfo.setPublishStatus(DroolsConstants.PublishStatus.PUBLISHED);
        Integer version = ruleSceneInfo.getPublishVersion();
        if(version==null || version==0){
            version = 1;
        }else{
            version = version + 1;
        }
        ruleSceneInfo.setPublishVersion(version);
        this.ruleSceneBiz.saveOrUpdate(ruleSceneInfo);

        //场景关联实体
        RuleSceneEntityRelInfo relInfo = new RuleSceneEntityRelInfo();
        relInfo.setSceneIdentify(ruleSceneInfo.getSceneIdentify());
        List<RuleSceneEntityRelInfo> ruleSceneEntityRelInfos =  ruleSceneEntityRelBiz.getList(relInfo);

        //场景下的有效规则信息
        List<RuleInfo> ruleInfoList = ruleInfoBiz.findBaseRuleListByScene(ruleSceneInfo);

        //规则关联属性
        List<RulePropertyRelInfo> allPropertyList = new ArrayList<>();
        //规则关联条件
        List<RuleConditionInfo> allConditionsList = new ArrayList<>();
        //规则关联的动作
        List<RuleActionInfo> allActionList = new ArrayList<>();

        //遍历规则 获取动作，条件，属性
        if(!CollectionUtils.isEmpty(ruleInfoList)){
            for (RuleInfo ruleInfo : ruleInfoList) {
                //规则关联属性
                List<RulePropertyRelInfo> rulePropertyRelInfos = getRulePropertyRelInfos(ruleInfo.getRuleCode());
                allPropertyList.addAll(rulePropertyRelInfos);

                //规则关联条件
                List<RuleConditionInfo> ruleConditionInfos = ruleConditionBiz.findRuleConditionInfoByRuleCode(ruleInfo.getRuleCode());
                allConditionsList.addAll(ruleConditionInfos);

                //规则关联的动作
                RuleActionInfo info = new RuleActionInfo();
                info.setRuleCode(ruleInfo.getRuleCode());
                List<RuleActionInfo> ruleActionRuleRelInfos = ruleActionInfoBiz.list(info);
                allActionList.addAll(ruleActionRuleRelInfos);
            }
        }


        // 复制版本
        //场景
        PublishRuleSceneInfo publishRuleSceneInfo = new PublishRuleSceneInfo();
        BeanUtils.copyProperties(ruleSceneInfo,publishRuleSceneInfo);
        publishRuleSceneInfo.setSceneId(null);
        publishRuleSceneInfo.setPublishStatus(DroolsConstants.PublishStatus.UPDATEING);

        PublishRuleSceneInfo pinfo = new PublishRuleSceneInfo();
        pinfo.setSceneIdentify(publishRuleSceneInfo.getSceneIdentify());
        List<PublishRuleSceneInfo> plist = this.publishRuleSceneInfoMapper.select(pinfo);
        if(!CollectionUtils.isEmpty(plist)){
            for (PublishRuleSceneInfo sceneInfo : plist) {
                sceneInfo.setPublishStatus(DroolsConstants.PublishStatus.PUBLISHED);
                Example example = new Example(PublishRuleSceneInfo.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("sceneIdentify", publishRuleSceneInfo.getSceneIdentify());
                this.publishRuleSceneInfoMapper.updateByExample(sceneInfo,example);
            }
        }

        this.publishRuleSceneInfoMapper.insertSelective(publishRuleSceneInfo);



        //场景实体
        if(!CollectionUtils.isEmpty(ruleSceneEntityRelInfos)){
            for (RuleSceneEntityRelInfo ruleSceneEntityRelInfo : ruleSceneEntityRelInfos) {
                PublishRuleSceneEntityRelInfo publishRuleSceneEntityRelInfo = new PublishRuleSceneEntityRelInfo();
                BeanUtils.copyProperties(ruleSceneEntityRelInfo,publishRuleSceneEntityRelInfo);
                publishRuleSceneEntityRelInfo.setSceneEntityRelId(null);
                publishRuleSceneEntityRelInfo.setPublishVersion(publishRuleSceneInfo.getPublishVersion());
                this.publishRuleSceneEntityRelInfoMapper.insertSelective(publishRuleSceneEntityRelInfo);
            }
        }

        //规则
        if(!CollectionUtils.isEmpty(ruleInfoList)){
            for (RuleInfo ruleInfo : ruleInfoList) {
                PublishRuleInfo publishRuleInfo = new PublishRuleInfo();
                BeanUtils.copyProperties(ruleInfo,publishRuleInfo);
                publishRuleInfo.setRuleId(null);
                publishRuleInfo.setPublishVersion(publishRuleSceneInfo.getPublishVersion());
                this.publishRuleInfoMapper.insertSelective(publishRuleInfo);
            }
        }

        // 规则属性
        if(!CollectionUtils.isEmpty(allPropertyList)){
            for (RulePropertyRelInfo rulePropertyRelInfo : allPropertyList) {
                PublishRulePropertyRelInfo publishRulePropertyRelInfo = new PublishRulePropertyRelInfo();
                BeanUtils.copyProperties(rulePropertyRelInfo,publishRulePropertyRelInfo);
                publishRulePropertyRelInfo.setRuleProRelId(null);
                publishRulePropertyRelInfo.setPublishVersion(publishRuleSceneInfo.getPublishVersion());
                this.publishRulePropertyRelInfoMapper.insertSelective(publishRulePropertyRelInfo);
            }
        }

        //规则条件
        if(!CollectionUtils.isEmpty(allConditionsList)){
            for (RuleConditionInfo ruleConditionInfo : allConditionsList) {
                PublishRuleConditionInfo publishRuleConditionInfo = new PublishRuleConditionInfo();
                BeanUtils.copyProperties(ruleConditionInfo,publishRuleConditionInfo);
                publishRuleConditionInfo.setConditionId(null);
                publishRuleConditionInfo.setPublishVersion(publishRuleSceneInfo.getPublishVersion());
                this.publishRuleConditionInfoMapper.insertSelective(publishRuleConditionInfo);
            }
        }
        //规则动作
        if(!CollectionUtils.isEmpty(allActionList)){
            for (RuleActionInfo ruleActionInfo : allActionList) {
               PublishRuleActionInfo publishRuleActionInfo = new PublishRuleActionInfo();
                BeanUtils.copyProperties(ruleActionInfo,publishRuleActionInfo);
                publishRuleActionInfo.setActionId(null);
                publishRuleActionInfo.setPublishVersion(publishRuleSceneInfo.getPublishVersion());
                this.publishRuleActionInfoMapper.insertSelective(publishRuleActionInfo);
            }
        }
    }

    /* *
     * 与未发布的场景对比
     * @author ly
     * @modifyTime 2020/11/12 13:15:00
     */
    public ObjectRestResponse comepareNotPublishScene(String scene, Integer publishVersion){
        ObjectRestResponse res = new ObjectRestResponse();
        PublishRuleSceneInfoVo ruleSceneInfoVo = getNotPublishSceneVo(scene);
        if(ruleSceneInfoVo==null){
            res.setErrorMsg("未找到未发布的相关场景");
            return res;
        }

        PublishRuleSceneInfoVo publishRuleSceneInfoVo = getPublishSceneVo(scene,publishVersion);
        if(ruleSceneInfoVo==null){
            res.setErrorMsg("未找到已发布的相关版本场景");
            return res;
        }

        Map<String,Object> result = new HashMap<>();
        result.put("vo1",publishRuleSceneInfoVo);
        result.put("vo2",ruleSceneInfoVo);

        String resultStr = compareSceneBiz.compare(result);
        result.put("conclusion",resultStr);

        res.setSuucessMsg("查询成功");
        res.setData(result);
        return res;
    }

    /* *
     * 根据场景获取未发布场景的规则信息
     * @author ly
     * @modifyTime 2020/11/12 13:32:00
     */
    public PublishRuleSceneInfoVo getNotPublishSceneVo(String scene){
        PublishRuleSceneInfoVo ruleSceneInfoVo = new PublishRuleSceneInfoVo();

        List<RuleSceneInfo> ruleSceneInfos = ruleSceneBiz.list(scene);
        if(CollectionUtils.isEmpty(ruleSceneInfos)){
            return null;
        }
        //获取未发布的场景
        RuleSceneInfo ruleSceneInfo = ruleSceneInfos.get(0);
        PublishRuleSceneInfo publishRuleSceneInfo = new PublishRuleSceneInfo();
        BeanUtils.copyProperties(ruleSceneInfo,publishRuleSceneInfo);

        //场景下的有效规则信息
        List<RuleInfo> ruleInfoList = ruleInfoBiz.findBaseRuleListByScene(ruleSceneInfo);
        List<PublishSceneRuleInfoVo> ruleInfoVoList = new ArrayList<>();

        //遍历规则 获取动作，条件，属性
        if(!CollectionUtils.isEmpty(ruleInfoList)){
            for (RuleInfo ruleInfo : ruleInfoList) {
                PublishSceneRuleInfoVo vo = new PublishSceneRuleInfoVo();
                //规则关联属性
                List<RulePropertyRelInfoVo> rulePropertyInfos = ruleInfoBiz.findRulePropertyListByRuleCode(ruleInfo.getRuleCode());

                //规则关联条件
                List<RuleConditionInfo> ruleConditionInfos = ruleConditionBiz.findRuleConditionInfoByRuleCode(ruleInfo.getRuleCode());

                //规则关联的动作
                RuleActionInfo info = new RuleActionInfo();
                info.setRuleCode(ruleInfo.getRuleCode());
                List<RuleActionInfo> ruleActionInfos = ruleActionInfoBiz.list(info);

                PublishRuleInfo publishRuleInfo = new PublishRuleInfo();
                BeanUtils.copyProperties(ruleInfo,publishRuleInfo);

                List<PublishRuleConditionInfo> publishRuleConditionInfos = new ArrayList<>();
                if(!CollectionUtils.isEmpty(ruleConditionInfos)){
                    for (RuleConditionInfo ruleConditionInfo : ruleConditionInfos) {
                        PublishRuleConditionInfo publishRuleConditionInfo = new PublishRuleConditionInfo();
                        BeanUtils.copyProperties(ruleConditionInfo,publishRuleConditionInfo);
                        publishRuleConditionInfos.add(publishRuleConditionInfo);
                    }
                }

                List<PublishRuleActionInfo> publishRuleActionInfos = new ArrayList<>();
                if(!CollectionUtils.isEmpty(ruleConditionInfos)){
                    for (RuleActionInfo ruleActionInfo : ruleActionInfos) {
                        PublishRuleActionInfo publishRuleActionInfo = new PublishRuleActionInfo();
                        BeanUtils.copyProperties(ruleActionInfo,publishRuleActionInfo);
                        publishRuleActionInfos.add(publishRuleActionInfo);
                    }
                }

                vo.setRuleInfo(publishRuleInfo);
                vo.setRuleConditionInfos(publishRuleConditionInfos);
                vo.setRulePropertyInfos(rulePropertyInfos);
                vo.setRuleActionInfos(publishRuleActionInfos);
                ruleInfoVoList.add(vo);
            }
        }

        ruleSceneInfoVo.setRuleSceneInfo(publishRuleSceneInfo);
        ruleSceneInfoVo.setRuleInfoList(ruleInfoVoList);
        return ruleSceneInfoVo;
    }

    /* *
     * 根据场景和版本获取已发布场景的规则信息
     * @author ly
     * @modifyTime 2020/11/12 13:32:00
     */
    public PublishRuleSceneInfoVo getPublishSceneVo(String scene,Integer publishVersion){
        PublishRuleSceneInfoVo ruleSceneInfoVo = new PublishRuleSceneInfoVo();

        //获取已发布的场景
        PublishRuleSceneInfo ruleSceneInfo = publishRuleSceneBiz.getInfoBySecne(scene,publishVersion);
        if(ruleSceneInfo == null){
            return null;
        }

        //场景下的有效规则信息
        List<PublishRuleInfo> ruleInfoList = publishRuleInfoMapper.findBaseRuleListByScene(ruleSceneInfo);
        List<PublishSceneRuleInfoVo> ruleInfoVoList = new ArrayList<>();

        //遍历规则 获取动作，条件，属性
        if(!CollectionUtils.isEmpty(ruleInfoList)){
            for (PublishRuleInfo ruleInfo : ruleInfoList) {
                PublishSceneRuleInfoVo vo = new PublishSceneRuleInfoVo();
                //规则关联属性
                List<RulePropertyRelInfoVo> rulePropertyInfos = publishRuleInfoMapper.findRulePropertyListByRuleCode(ruleInfo.getRuleCode(),publishVersion);

                //规则关联条件
                PublishRuleConditionInfo conditionInfo = new PublishRuleConditionInfo();
                conditionInfo.setRuleCode(ruleInfo.getRuleCode());
                conditionInfo.setPublishVersion(publishVersion);
                List<PublishRuleConditionInfo> ruleConditionInfos = publishRuleConditionInfoMapper.select(conditionInfo);

                //规则关联的动作
                PublishRuleActionInfo actionInfo = new PublishRuleActionInfo();
                actionInfo.setRuleCode(ruleInfo.getRuleCode());
                actionInfo.setPublishVersion(publishVersion);
                List<PublishRuleActionInfo> ruleActionInfos = publishRuleActionInfoMapper.select(actionInfo);

                vo.setRuleInfo(ruleInfo);
                vo.setRuleConditionInfos(ruleConditionInfos);
                vo.setRulePropertyInfos(rulePropertyInfos);
                vo.setRuleActionInfos(ruleActionInfos);
                ruleInfoVoList.add(vo);
            }
        }

        ruleSceneInfoVo.setRuleSceneInfo(ruleSceneInfo);
        ruleSceneInfoVo.setRuleInfoList(ruleInfoVoList);
        return ruleSceneInfoVo;
    }



    /* *
     * 获取属性信息
     * @author ly
     * @modifyTime 2020/11/12 13:33:00
     */
    public List<RulePropertyRelInfo> getRulePropertyRelInfos(String ruleCode){
        RulePropertyRelInfo relInfo = new RulePropertyRelInfo();
        relInfo.setRuleCode(ruleCode);
       return  rulePropertyRelInfoMapper.select(relInfo);
    }
}
