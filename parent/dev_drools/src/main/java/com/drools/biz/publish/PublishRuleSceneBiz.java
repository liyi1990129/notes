package com.drools.biz.publish;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.drools.common.DroolsConstants;
import com.drools.mapper.publish.PublishRuleSceneInfoMapper;
import com.drools.model.RuleSceneInfo;
import com.drools.model.publish.PublishRuleSceneInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PublishRuleSceneBiz {
    @Resource
    PublishRuleSceneInfoMapper publishRuleSceneInfoMapper;

    /* *
     * 分页查询
     * @author ly
     * @modifyTime 2020/10/22 16:36:00
     */
    public PageInfo page(Map<String, Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");

        Example example = new Example(PublishRuleSceneInfo.class);
        Example.Criteria criteria = example.createCriteria();
        String sceneName = (String) params.get("sceneName");
        String sceneIdentify = (String) params.get("sceneIdentify");
        String sceneType = (String) params.get("sceneType");
        if (!StringUtils.isEmpty(sceneType)) {
            criteria.andEqualTo("sceneType", sceneType);
        }
        if (!StringUtils.isEmpty(sceneName)) {
            criteria.andLike("sceneName", "%"+sceneName+"%");
        }
        if (!StringUtils.isEmpty(sceneIdentify)) {
            criteria.andEqualTo("sceneIdentify", sceneIdentify);
        }
        example.setOrderByClause("publish_version asc");

        Page page = PageHelper.startPage(pageNumber, pageSize);
        List<PublishRuleSceneInfo> list = this.publishRuleSceneInfoMapper.selectByExample(example);

        PageInfo pageInfo = new PageInfo<>(list);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    /* *
     * 根据ID获取场景
     * @author ly
     * @modifyTime 2020/10/22 16:37:00
     */
    public PublishRuleSceneInfo getInfoById(Long id,Integer version) {
        PublishRuleSceneInfo info = new PublishRuleSceneInfo();
        info.setSceneId(id);
        info.setPublishVersion(version);
        return this.publishRuleSceneInfoMapper.selectOne(info);
    }

    /* *
     * 根据标识和版本获取场景
     * @author ly
     * @modifyTime 2020/10/22 16:37:00
     */
    public PublishRuleSceneInfo getInfoBySecne(String secne,Integer version) {
        PublishRuleSceneInfo info = new PublishRuleSceneInfo();
        info.setSceneIdentify(secne);
        info.setPublishVersion(version);
        return this.publishRuleSceneInfoMapper.selectOne(info);
    }
    /* *
     * 获取最新版本的场景
     * @author ly
     * @modifyTime 2020/11/17 15:32:00
     */
    public PublishRuleSceneInfo getUpdatedInfoBySecne(String secne) {
        PublishRuleSceneInfo info = new PublishRuleSceneInfo();
        info.setPublishStatus(DroolsConstants.PublishStatus.UPDATEING);
        info.setSceneIdentify(secne);
        return this.publishRuleSceneInfoMapper.selectOne(info);
    }
    /* *
     * 根据标识获取场景
     * @author ly
     * @modifyTime 2020/10/22 16:37:00
     */
    public List<PublishRuleSceneInfo> getListInfoBySecne(String secne) {
        PublishRuleSceneInfo info = new PublishRuleSceneInfo();
        info.setSceneIdentify(secne);
        return this.publishRuleSceneInfoMapper.select(info);
    }

    /* *
     * 保存
     * @author ly
     * @modifyTime 2020/11/20 13:37:00
     */
    public void saveOrUpdate(PublishRuleSceneInfo publishRuleSceneInfo){
        if(publishRuleSceneInfo.getSceneId()==null){
            this.publishRuleSceneInfoMapper.insertSelective(publishRuleSceneInfo);
        }else{
            this.publishRuleSceneInfoMapper.updateByPrimaryKey(publishRuleSceneInfo);
        }
    }

}
