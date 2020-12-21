package com.drools.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.drools.common.DroolsConstants;
import com.drools.mapper.RuleEntityItemInfoMapper;
import com.drools.model.RuleEntityItemInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RuleEntityItemBiz {
    @Resource
    private RuleEntityItemInfoMapper ruleEntityItemInfoMapper;

    /* *
     * 分页查询
     * @author ly
     * @modifyTime 2020/11/16 10:53:00
     */
    public PageInfo page(Map<String, Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");
        Example example = new Example(RuleEntityItemInfo.class);
        Example.Criteria criteria = example.createCriteria();

        Page page = PageHelper.startPage(pageNumber,pageSize);
        List<RuleEntityItemInfo> list = this.ruleEntityItemInfoMapper.selectByExample(example);

        PageInfo pageInfo = new PageInfo<>(list);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    /* *
     * 根据ID查询
     * @author ly
     * @modifyTime 2020/11/16 10:55:00
     */
    public RuleEntityItemInfo findBaseRuleEntityItemInfoById(Long id)  {
        RuleEntityItemInfo info = new RuleEntityItemInfo();
        info.setItemId(id);
        return this.ruleEntityItemInfoMapper.selectOne(info);
    }

    /* *
     * 根据实体类Id查询
     * @author ly
     * @modifyTime 2020/11/16 10:55:00
     */
    public List<RuleEntityItemInfo> listByParams(Map<String,Object> params)  {
        Long entityId = (Long) params.get("entityId");
        String isEffect = (String) params.get("isEffect");
        String itemType = (String) params.get("itemType");
        String itemObjType = (String) params.get("itemObjType");
        Example example = new Example(RuleEntityItemInfo.class);
        Example.Criteria criteria = example.createCriteria();
        if(null!=entityId){
            criteria.andEqualTo("entityId",entityId);
        }
        if(StringUtils.isNotBlank(itemType)){
            criteria.andEqualTo("itemType",itemType);
        }
        if(StringUtils.isNotBlank(itemObjType)){
            List<String> list = new ArrayList<>();
            if (itemObjType.indexOf(DroolsConstants.Sysbols.COMMA) != -1) {
                String[] strArr = itemObjType.split(DroolsConstants.Sysbols.COMMA);
                list = Arrays.asList(strArr);
            } else {
                list.add(itemObjType);
            }
//            Iterable<String> iterable = (Iterable<String>) list.iterator();
            criteria.andIn("itemObjType",list);
        }

        if(StringUtils.isNotBlank(isEffect)){
            criteria.andEqualTo("isEffect",isEffect);
        }


        return this.ruleEntityItemInfoMapper.selectByExample(example);
    }
    /* *
     * 根据Id删除
     * @author ly
     * @modifyTime 2020/11/16 10:55:00
     */
    public void delInfoById(Integer id)  {
        Example example = new Example(RuleEntityItemInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",id);
        this.ruleEntityItemInfoMapper.deleteByExample(example);
    }
    /* *
     * 根据实体类ID删除
     * @author ly
     * @modifyTime 2020/11/16 10:55:00
     */
    public void delInfoByEntityId(Long entityId)  {
        Example example = new Example(RuleEntityItemInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("entityId",entityId);
        this.ruleEntityItemInfoMapper.deleteByExample(example);
    }


    /* *
     * 保存与更新
     * @author ly
     * @modifyTime 2020/11/16 10:56:00
     */
    public void saveOrUpdate(RuleEntityItemInfo info){
        if(null== info.getItemId()){
            info.setCreTime(new Date());
            info.setCreUserId(new Long(1));
            this.ruleEntityItemInfoMapper.insertSelective(info);
        }else {
            Example example = new Example(RuleEntityItemInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("itemId",info.getEntityId());
            this.ruleEntityItemInfoMapper.updateByExample(info,example);
        }
    }

    /* *
     * 根据属性标识和实体标识查询实体属性
     * @author ly
     * @modifyTime 2020/11/16 10:45:00
     */
    public RuleEntityItemInfo getItemInfoByPkg(RuleEntityItemInfo ruleEntityItemInfo){
        RuleEntityItemInfo info = new RuleEntityItemInfo();
        info.setEntityIdentify(ruleEntityItemInfo.getEntityIdentify());
        info.setItemIdentify(ruleEntityItemInfo.getItemIdentify());
        return this.ruleEntityItemInfoMapper.selectOne(info);
    }

    /* *
     * 保存实体属性
     * @author ly
     * @modifyTime 2020/11/16 10:44:00
     */
    public void saveEntityAndItem(RuleEntityItemInfo ruleEntityItemInfo) {
        RuleEntityItemInfo info = getItemInfoByPkg(ruleEntityItemInfo);
        if(info == null){
            this.ruleEntityItemInfoMapper.insertSelective(ruleEntityItemInfo);
        }else{
            Example example = new Example(RuleEntityItemInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("itemId", info.getItemId());
            this.ruleEntityItemInfoMapper.updateByExample(ruleEntityItemInfo, example);
        }
    }
}
