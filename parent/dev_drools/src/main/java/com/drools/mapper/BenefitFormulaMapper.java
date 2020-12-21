package com.drools.mapper;

import com.drools.model.BenefitFormula;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述：
 */
public interface BenefitFormulaMapper extends Mapper<BenefitFormula> {


    List<BenefitFormula> findBenefitFormulaByCrtableAndBenCode(@Param("crtable") String crtable, @Param("benCode") String benCode);

    List<BenefitFormula> findByCrtable(@Param("crtable") String crtable);

    List<BenefitFormula> list(BenefitFormula benefitFormula);
}
