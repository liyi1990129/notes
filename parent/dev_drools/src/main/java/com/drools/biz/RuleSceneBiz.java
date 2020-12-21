package com.drools.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.drools.common.DroolsConstants;
import com.drools.mapper.RuleInfoMapper;
import com.drools.mapper.RuleSceneEntityRelInfoMapper;
import com.drools.mapper.RuleSceneInfoMapper;
import com.drools.model.RuleEntityInfo;
import com.drools.model.RuleInfo;
import com.drools.model.RuleSceneEntityRelInfo;
import com.drools.model.RuleSceneInfo;
import com.drools.vo.RuleSceneInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RuleSceneBiz {
    @Resource
    private RuleSceneInfoMapper ruleSceneInfoMapper;
    @Resource
    private RuleSceneEntityRelInfoMapper ruleSceneEntityRelInfoMapper;
    @Resource
    private RuleInfoMapper ruleInfoMapper;

    /* *
     * 分页查询 
     * @author ly
     * @modifyTime 2020/10/22 16:36:00
     */
    public PageInfo page(Map<String, Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");

        Example example = new Example(RuleSceneInfo.class);
        Example.Criteria criteria = example.createCriteria();
        String sceneName = (String) params.get("sceneName");
        String sceneType = (String) params.get("sceneType");
        String sceneIdentify = (String) params.get("sceneIdentify");
        if (!StringUtils.isEmpty(sceneType)) {
            criteria.andEqualTo("sceneType", sceneType);
        }
        if (!StringUtils.isEmpty(sceneIdentify)) {
            criteria.andEqualTo("sceneIdentify", sceneIdentify);
        }
        if (!StringUtils.isEmpty(sceneName)) {
            criteria.andLike("sceneName", "%"+sceneName+"%");
        }

        Page page = PageHelper.startPage(pageNumber, pageSize,true);
        List<RuleSceneInfo> list = this.ruleSceneInfoMapper.selectByExample(example);

        List<RuleSceneInfoVo> ruleSceneInfoVoList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            for (RuleSceneInfo ruleSceneInfo : list) {
                RuleInfo info = new RuleInfo();
                info.setSceneIdentify(ruleSceneInfo.getSceneIdentify());
                List<RuleInfo> ruleInfos = ruleInfoMapper.select(info);

                RuleSceneInfoVo vo = new RuleSceneInfoVo();
                BeanUtils.copyProperties(ruleSceneInfo,vo);
                vo.setRuleInfoList(ruleInfos);
                ruleSceneInfoVoList.add(vo);
            }
        }

        PageInfo pageInfo = new PageInfo(ruleSceneInfoVoList);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    public List<RuleSceneInfo> list(String scene){
        RuleSceneInfo ruleSceneInfo = new RuleSceneInfo();
        ruleSceneInfo.setSceneIdentify(scene);
        return this.ruleSceneInfoMapper.select(ruleSceneInfo);
    }


    /* *
     * 根据id删除场景 
     * @author ly
     * @modifyTime 2020/10/22 16:36:00
     */
    public void delInfoById(Long id) {
        Example example = new Example(RuleSceneInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sceneId", id);
        this.ruleSceneInfoMapper.deleteByExample(example);
    }

    /* *
     * 根据场景id删除 与实体关联表
     * @author ly
     * @modifyTime 2020/10/22 16:36:00
     */
    public void delRelInfoBySceneIdentify(String sceneIdentify) {
        Example example = new Example(RuleSceneEntityRelInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sceneIdentify", sceneIdentify);
        this.ruleSceneEntityRelInfoMapper.deleteByExample(example);
    }

    /* *
     * 根据ID获取场景 
     * @author ly
     * @modifyTime 2020/10/22 16:37:00
     */
    public RuleSceneInfo getInfoById(Long id) {
        RuleSceneInfo info = new RuleSceneInfo();
        info.setSceneId(id);
        return this.ruleSceneInfoMapper.selectOne(info);
    }


    /* *
     * 保存场景信息 
     * @author ly
     * @modifyTime 2020/10/22 16:37:00
     */
    public Long saveOrUpdate(RuleSceneInfo info) {
        if (null == info.getSceneId()) {
            info.setCreTime(new Date());
            info.setCreUserId(new Long(1));
            info.setIsEffect(DroolsConstants.IsEffect.YES);
            this.ruleSceneInfoMapper.add(info);
        } else {
            Example example = new Example(RuleSceneInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("sceneId", info.getSceneId());
            this.ruleSceneInfoMapper.updateByExampleSelective(info, example);
        }
        return info.getSceneId();
    }

    /* *
     * 根据场景信息获取相关实体 
     * @author ly
     * @modifyTime 2020/10/22 16:38:00
     */
    public List<RuleEntityInfo> findBaseRuleEntityListByScene(RuleSceneInfo ruleSceneInfo) {
        return this.ruleSceneEntityRelInfoMapper.findBaseRuleEntityListByScene(ruleSceneInfo);
    }

    /* *
     * 列表查询 
     * @author ly
     * @modifyTime 2020/11/20 14:25:00
     */
    public List<RuleSceneInfo> findList(RuleSceneInfo info){
        return this.ruleSceneInfoMapper.select(info);
    }
}
