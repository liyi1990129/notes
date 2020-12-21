package com.drools.mapper;

import com.drools.model.RuleInterfaceLog;
import com.drools.vo.RuleInterfaceLogVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RuleInterfaceLogMapper extends Mapper<RuleInterfaceLog> {
    /**
     * 查询所有的接口请求数量
     * @param ruleInterfaceLog
     * @return
     */
    List<RuleInterfaceLogVo> countByInterface(RuleInterfaceLog ruleInterfaceLog);

    /**
     * 查询某个接口一周的请求数量
     * @param ruleInterfaceLog
     * @return
     */
    List<RuleInterfaceLogVo> countByInterfaceIdentify(RuleInterfaceLog ruleInterfaceLog);

    List<RuleInterfaceLogVo> listByParams(RuleInterfaceLog ruleInterfaceLog);

    RuleInterfaceLog countByBatchNo(RuleInterfaceLog ruleInterfaceLog);
}
