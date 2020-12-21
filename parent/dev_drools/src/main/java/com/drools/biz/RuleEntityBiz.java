package com.drools.biz;

import com.github.pagehelper.Page;
import com.google.common.collect.Lists;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.drools.common.DroolsConstants;
import com.drools.mapper.RuleEntityInfoMapper;
import com.drools.model.RuleEntityInfo;
import com.drools.model.RuleEntityItemInfo;
import com.drools.vo.PropertyVo;
import com.drools.vo.RuleEntityInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class RuleEntityBiz {
    @Resource
    private RuleEntityInfoMapper ruleEntityInfoMapper;
    @Resource
    private RuleEntityItemBiz ruleEntityItemBiz;

    /* *
     * 分页查询
     * @author ly
     * @modifyTime 2020/11/11 14:26:00
     */
    public PageInfo page(Map<String, Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");
        Page page = PageHelper.startPage(pageNumber, pageSize);
        Example example = new Example(RuleEntityInfo.class);
        Example.Criteria criteria = example.createCriteria();
        String entityName = (String) params.get("entityName");
        String entityIdentify = (String) params.get("entityIdentify");
        if (!StringUtils.isEmpty(entityIdentify)) {
            criteria.andEqualTo("entityIdentify", entityIdentify);
        }
        if (!StringUtils.isEmpty(entityName)) {
            criteria.andLike("entityName", "%"+entityName+"%");
        }

        List<RuleEntityInfo> list = this.ruleEntityInfoMapper.selectByExample(example);

        PageInfo pageInfo = new PageInfo<>(list);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    /* *
     * 查询所有的实体
     * @author ly
     * @modifyTime 2020/10/22 15:30:00
     */
    public List<RuleEntityInfo> findBaseRuleEntityInfoList() {
        return this.ruleEntityInfoMapper.selectAll();
    }

    /* *
     * 列表查询
     * @author ly
     * @modifyTime 2020/11/11 14:43:00
     */
    public List<RuleEntityInfo> list(RuleEntityInfo info) {
        return this.ruleEntityInfoMapper.select(info);
    }

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据实体id获取实体信息
     *
     * @param id 实体id
     */
    public RuleEntityInfo findBaseRuleEntityInfoById(Long id) {
        return this.ruleEntityInfoMapper.findBaseRuleEntityInfoById(id);
    }

    /* *
     * 根据ID删除实体类信息
     * @author ly
     * @modifyTime 2020/11/11 14:26:00
     */
    public void delEntityInfoById(Long id) {
        Example example = new Example(RuleEntityInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("entityId", id);
        this.ruleEntityInfoMapper.deleteByExample(example);
    }

    /* *
     * 根据ID获取实体类信息
     * @author ly
     * @modifyTime 2020/11/11 14:25:00
     */
    public RuleEntityInfo getEntityInfoById(Long id) {
        RuleEntityInfo info = new RuleEntityInfo();
        info.setEntityId(id);
        return this.ruleEntityInfoMapper.selectOne(info);
    }


    /* *
     * 保存
     * @author ly
     * @modifyTime 2020/11/11 14:26:00
     */
    public Long saveOrUpdate(RuleEntityInfo info) {
        if (null == info.getEntityId()) {
            info.setCreTime(new Date());
            info.setCreUserId(new Long(1));
            info.setIsEffect(DroolsConstants.IsEffect.YES);
            this.ruleEntityInfoMapper.add(info);
        } else {
            Example example = new Example(RuleEntityInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("entityId", info.getEntityId());
            this.ruleEntityInfoMapper.updateByExample(info, example);

        }
        return info.getEntityId();
    }

    /* *
     * 保存实体类
     * @author ly
     * @modifyTime 2020/11/13 16:18:00
     */
    public void saveEntityAndItem(RuleEntityInfo ruleEntityInfo, List<RuleEntityItemInfo> itemList) {
        RuleEntityInfo info = getInfoByPkg(ruleEntityInfo);
        if (info == null) {
            this.ruleEntityInfoMapper.add(ruleEntityInfo);
        } else {
            Example example = new Example(RuleEntityInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("entityId", info.getEntityId());
            this.ruleEntityInfoMapper.updateByExample(ruleEntityInfo, example);
            ruleEntityInfo.setEntityId(info.getEntityId());
        }
        Long id = ruleEntityInfo.getEntityId();
        for (RuleEntityItemInfo itemInfo : itemList) {
            itemInfo.setEntityId(id);
            this.ruleEntityItemBiz.saveEntityAndItem(itemInfo);
        }
    }


    /* *
     * 根据包名查询实体类
     * @author ly
     * @modifyTime 2020/11/16 10:44:00
     */
    public RuleEntityInfo getInfoByPkg(RuleEntityInfo ruleEntityInfo) {
        RuleEntityInfo info = new RuleEntityInfo();
        info.setEntityIdentify(ruleEntityInfo.getEntityIdentify());
        return this.ruleEntityInfoMapper.selectOne(info);
    }


    /* *
     * 根据包名查询实体属性
     * @author ly
     * @modifyTime 2020/11/16 10:45:00
     */
    public List<RuleEntityItemInfo> getItemInfoByEntity(String pkgName) {
        RuleEntityInfo info = new RuleEntityInfo();
        info.setPkgName(pkgName);
        RuleEntityInfo ruleEntityInfo = this.ruleEntityInfoMapper.selectOne(info);

        Map<String, Object> query = new HashMap<>();
        query.put("entityId", ruleEntityInfo.getEntityId());
        query.put("isEffect", DroolsConstants.IsEffect.YES);
        return this.ruleEntityItemBiz.listByParams(query);
    }

    /* *
     * 根据报名及实体标识查询实体属性及子对象属性
     * @author ly
     * @modifyTime 2020/11/16 10:46:00
     */
    public List<PropertyVo> getItems(String pkgName, String entityIdentify,String entityIdentifyName) {
        List<PropertyVo> result = new ArrayList<>();
        List<RuleEntityItemInfo> proList = getItemInfoByEntity(pkgName);
        if (!CollectionUtils.isEmpty(proList)) {
            for (RuleEntityItemInfo itemInfo : proList) {
                PropertyVo vo = new PropertyVo();
                vo.setFiledName(itemInfo.getItemIdentify());
                vo.setChName(itemInfo.getItemName());
                vo.setEnumName(itemInfo.getEnumName());
                vo.setListCls(itemInfo.getItemCls());
                vo.setFiledType(itemInfo.getItemType());
                vo.setChildren(Lists.newArrayList());
                vo.setParent(entityIdentify + "." + itemInfo.getItemIdentify());
                vo.setParentText(entityIdentifyName + "." + itemInfo.getItemName());



                if ("List".equals(itemInfo.getItemType())) {
                    if(!StringUtils.isEmpty(itemInfo.getItemCls())){
                        List<PropertyVo> childList = getItems(itemInfo.getItemCls(), vo.getParent() + "#" + itemInfo.getItemIdentify(),vo.getParentText()+"#"+itemInfo.getItemName());
                        vo.setChildren(childList);
                    }
                }
                result.add(vo);
            }
        }
        return result;
    }


    /* *
     * 根据实体类，查询子类所有实体
     * @author ly
     * @modifyTime 2020/11/16 11:28:00
     */
    public List<RuleEntityInfoVo> getEntityList(List<RuleEntityInfo> infos, List<RuleEntityInfoVo> result, int level, String parentIdentity) {

        for (RuleEntityInfo info : infos) {
            RuleEntityInfoVo vo = new RuleEntityInfoVo();
            BeanUtils.copyProperties(info, vo);

            Map<String, Object> query = new HashMap<>();
            query.put("entityId", info.getEntityId());
            query.put("isEffect", DroolsConstants.IsEffect.YES);
            query.put("itemObjType", DroolsConstants.FieldType.OBJECT);
            List<RuleEntityItemInfo> list = this.ruleEntityItemBiz.listByParams(query);

            if (!CollectionUtils.isEmpty(list)) {
                List<RuleEntityInfo> children = new ArrayList<>();

                for (RuleEntityItemInfo itemInfo : list) {
                    RuleEntityInfo einfo = new RuleEntityInfo();
                    einfo.setPkgName(itemInfo.getItemCls());
                    RuleEntityInfo ruleEntityInfo = this.ruleEntityInfoMapper.selectOne(einfo);
                    if(ruleEntityInfo!=null){
                        children.add(ruleEntityInfo);
                    }
                }
                log.info(list.size() + ":" + info.getEntityId());
                getEntityList(children, result, (level + 1), (info.getEntityIdentify() + level));
            }
            vo.setTreeId(info.getEntityIdentify() + level);
            vo.setParentIdentify(parentIdentity);
            result.add(vo);
        }
        return result;
    }

    public List<RuleEntityInfoVo> getChildEntityList(RuleEntityInfoVo info) {
        List<RuleEntityInfoVo> result = new ArrayList<>();

        int level = info.getLevel()+1;
        Map<String, Object> query = new HashMap<>();
        query.put("entityId", info.getEntityId());
        query.put("isEffect", DroolsConstants.IsEffect.YES);
        query.put("itemType", "List");
        List<RuleEntityItemInfo> list = this.ruleEntityItemBiz.listByParams(query);

        if (!CollectionUtils.isEmpty(list)) {

            for (RuleEntityItemInfo itemInfo : list) {
                RuleEntityInfo einfo = new RuleEntityInfo();
                einfo.setPkgName(itemInfo.getItemCls());
                RuleEntityInfo ruleEntityInfo = this.ruleEntityInfoMapper.selectOne(einfo);

                RuleEntityInfoVo vo = new RuleEntityInfoVo();
                BeanUtils.copyProperties(ruleEntityInfo, vo);
                vo.setTreeId(info.getEntityIdentify() + level);
                vo.setParentIdentify(info.getParentIdentify());
                vo.setLevel(level);
                vo.setLeaf(true);
                result.add(vo);

            }
        }


        return result;
    }
}
