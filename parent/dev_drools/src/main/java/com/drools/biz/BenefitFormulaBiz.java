package com.drools.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.drools.mapper.BenefitFormulaMapper;
import com.drools.model.BenefitFormula;
import com.drools.model.EventDate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class BenefitFormulaBiz {

    @Resource
    private BenefitFormulaMapper benefitFormulaMapper;


    /* *
     * 分页查询
     * @author ly
     * @modifyTime 2020/11/20 14:03:00
     */
    public PageInfo page(Map<String, Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");
        String crtable = (String) params.get("crtable");

        BenefitFormula info = new BenefitFormula();

        if (!StringUtils.isEmpty(crtable)) {
            info.setCrtable(crtable);
        }

        Page page = PageHelper.startPage(pageNumber, pageSize);
        List<BenefitFormula> list = this.benefitFormulaMapper.list(info);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(item -> item.setList(benefitFormulaMapper.findByCrtable(item.getCrtable())));
        }
        PageInfo pageInfo = new PageInfo<>(list);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }


    public List<BenefitFormula> getInfoByCrtable(String crtable) {
        return benefitFormulaMapper.findByCrtable(crtable);
    }


    public void delInfoByCrtable(String crtable) {
        if (org.apache.commons.lang.StringUtils.isNotBlank(crtable)) {
            BenefitFormula benefitFormula = new BenefitFormula();
            benefitFormula.setCrtable(crtable);
            this.benefitFormulaMapper.delete(benefitFormula);
        }

    }

    /* *
     * 保存
     * @author ly
     * @modifyTime 2020/11/20 14:04:00
     */
    public void saveOrUpdate(String crtable, List<BenefitFormula> benefitFormulas) {
        delInfoByCrtable(crtable);
        if (!CollectionUtils.isEmpty(benefitFormulas)) {
            benefitFormulas.forEach(benefitFormula -> {
                benefitFormula.setId(null);
                benefitFormula.setCrtable(crtable);
                this.benefitFormulaMapper.insert(benefitFormula);
            });
        }
    }

}
