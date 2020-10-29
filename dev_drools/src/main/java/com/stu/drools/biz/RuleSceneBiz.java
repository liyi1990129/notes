package com.stu.drools.biz;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stu.drools.mapper.RuleSceneEntityRelInfoMapper;
import com.stu.drools.mapper.RuleSceneInfoMapper;
import com.stu.drools.model.RuleEntityInfo;
import com.stu.drools.model.RuleSceneEntityRelInfo;
import com.stu.drools.model.RuleSceneInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RuleSceneBiz {
    @Resource
    private RuleSceneInfoMapper ruleSceneInfoMapper;
    @Resource
    private RuleSceneEntityRelInfoMapper ruleSceneEntityRelInfoMapper;

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
        if (!StringUtils.isEmpty(sceneType)) {
            criteria.andEqualTo("sceneType", sceneType);
        }
        if (!StringUtils.isEmpty(sceneName)) {
            criteria.andLike("sceneName", sceneName);
        }
        PageHelper.startPage(pageNumber, pageSize);
        List<RuleSceneInfo> list = this.ruleSceneInfoMapper.selectByExample(example);

        PageInfo pageInfo = new PageInfo<>(list);
        return pageInfo;
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
        
        this.delRelInfoById(id);
    }

    /* *
     * 根据场景id删除 与实体关联表
     * @author ly
     * @modifyTime 2020/10/22 16:36:00
     */
    public void delRelInfoById(Long id) {
        Example example = new Example(RuleSceneEntityRelInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sceneId", id);
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
            info.setIsEffect(1);
            this.ruleSceneInfoMapper.insertSelective(info);
        } else {
            Example example = new Example(RuleSceneInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("sceneId", info.getSceneId());
            this.ruleSceneInfoMapper.updateByExample(info, example);
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

    public List<RuleSceneInfo> findList(RuleSceneInfo info){
        return this.ruleSceneInfoMapper.select(info);
    }
}
