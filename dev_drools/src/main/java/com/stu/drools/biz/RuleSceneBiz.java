package com.stu.drools.biz;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stu.drools.mapper.BaseRuleSceneEntityRelInfoMapper;
import com.stu.drools.mapper.BaseRuleSceneInfoMapper;
import com.stu.drools.model.BaseRuleEntityInfo;
import com.stu.drools.model.BaseRuleSceneInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RuleSceneBiz {
    @Resource
    private BaseRuleSceneInfoMapper baseRuleSceneInfoMapper;
    @Resource
    private BaseRuleSceneEntityRelInfoMapper baseRuleSceneEntityRelInfoMapper;

    public PageInfo page(Map<String, Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");
        BaseRuleSceneInfo info = new BaseRuleSceneInfo();

        PageHelper.startPage(pageNumber,pageSize);
        List<BaseRuleSceneInfo> list = this.baseRuleSceneInfoMapper.findBaseRuleSceneInfiList(info);

        PageInfo pageInfo = new PageInfo<>(list);
        return pageInfo;
    }




    public void delInfoById(Integer id)  {
        Example example = new Example(BaseRuleSceneInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sceneId",id);
         this.baseRuleSceneInfoMapper.deleteByExample(example);
    }
    public BaseRuleSceneInfo getInfoById(Integer id)  {
        BaseRuleSceneInfo info = new BaseRuleSceneInfo();
        info.setSceneId(id);
        return  this.baseRuleSceneInfoMapper.selectOne(info);
    }


    public void saveOrUpdate(BaseRuleSceneInfo info){
        if(null== info.getSceneId()){
            info.setCreTime(new Date());
            info.setCreUserId(new Long(1));
            info.setIsEffect(1);
             this.baseRuleSceneInfoMapper.insertSelective(info);
        }else {
            Example example = new Example(BaseRuleSceneInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("sceneId",info.getSceneId());
            this.baseRuleSceneInfoMapper.updateByExample(info,example);

        }
    }

    public List<BaseRuleEntityInfo> findBaseRuleEntityListByScene(BaseRuleSceneInfo baseRuleSceneInfo){
        return this.baseRuleSceneEntityRelInfoMapper.findBaseRuleEntityListByScene(baseRuleSceneInfo);
    }
}
