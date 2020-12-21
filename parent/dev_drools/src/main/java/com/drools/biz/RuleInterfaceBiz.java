package com.drools.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.drools.mapper.RuleInterfaceMapper;
import com.drools.model.RuleInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RuleInterfaceBiz {

    @Resource
    private RuleInterfaceMapper ruleInterfaceMapper;

    /* *
     * 分页查询
     * @author ly
     * @modifyTime 2020/11/11 14:26:00
     */
    public PageInfo page(Map<String, Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");
        Page page = PageHelper.startPage(pageNumber, pageSize);
        Example example = new Example(RuleInterface.class);
        Example.Criteria criteria = example.createCriteria();
        String interfaceName = (String) params.get("interfaceName");
        if (!StringUtils.isEmpty(interfaceName)) {
            criteria.andLike("interfaceName", interfaceName);
        }

        List<RuleInterface> list = this.ruleInterfaceMapper.selectByExample(example);

        PageInfo pageInfo = new PageInfo<>(list);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    /* *
     * 查询所有信息
     * @author ly
     * @modifyTime 2020/11/23 10:17:00
     */
    public List<RuleInterface> findAllList(){
        return this.ruleInterfaceMapper.selectAll();
    }

    /* *
     * 根据ID查询
     * @author ly
     * @modifyTime 2020/11/23 10:19:00
     */
    public RuleInterface findByID(Long id){
        RuleInterface info = new RuleInterface();
        info.setInterfaceId(id);
        return this.ruleInterfaceMapper.selectOne(info);
    }

    /* *
     * 根据接口标识查询
     * @author ly
     * @modifyTime 2020/11/26 10:03:00
     */
    public RuleInterface findByIdentify(String identify){
        RuleInterface info = new RuleInterface();
        info.setInterfaceIdentify(identify);
        return this.ruleInterfaceMapper.selectOne(info);
    }

    public void updateInterface(RuleInterface ruleInterface){
        this.ruleInterfaceMapper.updateByPrimaryKey(ruleInterface);
    }
}
