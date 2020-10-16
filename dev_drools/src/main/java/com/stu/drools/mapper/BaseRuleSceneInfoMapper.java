package com.stu.drools.mapper;

import com.stu.drools.model.BaseRuleSceneInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.stu.drools.mapper.BaseRuleSceneInfoMapper
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/20
 */
public interface BaseRuleSceneInfoMapper extends Mapper<BaseRuleSceneInfo> {

    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 获取规则引擎场景集合
     * @param sceneInfo 参数
     */
    List<BaseRuleSceneInfo> findBaseRuleSceneInfiList(BaseRuleSceneInfo sceneInfo);
}
