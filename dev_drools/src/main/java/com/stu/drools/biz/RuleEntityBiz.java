package com.stu.drools.biz;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stu.drools.mapper.BaseRuleEntityInfoMapper;
import com.stu.drools.model.BaseRuleEntityInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RuleEntityBiz {
    @Resource
    private BaseRuleEntityInfoMapper baseRuleEntityInfoMapper;

    public PageInfo page(Map<String, Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");
        BaseRuleEntityInfo baseRuleEntityInfo = new BaseRuleEntityInfo();

        PageHelper.startPage(pageNumber,pageSize);
        List<BaseRuleEntityInfo> list = this.baseRuleEntityInfoMapper.findBaseRuleEntityInfoList(baseRuleEntityInfo);

        PageInfo pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 获取规则引擎实体信息
     *
     * @param baseRuleEntityInfo 参数
     */
    public List<BaseRuleEntityInfo> findBaseRuleEntityInfoList(BaseRuleEntityInfo baseRuleEntityInfo) throws Exception {
        return this.baseRuleEntityInfoMapper.findBaseRuleEntityInfoList(baseRuleEntityInfo);
    }

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据实体id获取实体信息
     *
     * @param id 实体id
     */
    public BaseRuleEntityInfo findBaseRuleEntityInfoById(Integer id)  {
        return this.baseRuleEntityInfoMapper.findBaseRuleEntityInfoById(id);
    }
    public void delEntityInfoById(Integer id)  {
        Example example = new Example(BaseRuleEntityInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("entityId",id);
         this.baseRuleEntityInfoMapper.deleteByExample(example);
    }


    public int saveOrUpdate(BaseRuleEntityInfo info){
        if(null== info.getEntityId()){
            info.setCreTime(new Date());
            info.setCreUserId(new Long(1));
            info.setIsEffect(1);
             this.baseRuleEntityInfoMapper.add(info);
        }else {
            Example example = new Example(BaseRuleEntityInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("entityId",info.getEntityId());
            this.baseRuleEntityInfoMapper.updateByExample(info,example);

        }
        return info.getEntityId();
    }
}
