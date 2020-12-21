package com.drools.biz.sys;

import com.drools.mapper.sys.SysDictDetailMapper;
import com.drools.model.sys.SysDictDetail;
import com.drools.util.ScanningFileUtil;
import com.drools.vo.PropertyVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysDictDetailBiz {
    @Resource
    private SysDictDetailMapper sysDictDetailMapper;

    public List<SysDictDetail> findByMainId(Long mId){
        Example example = new Example(SysDictDetail.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sdmId",mId);
        example.orderBy("sddSeq");
        return this.sysDictDetailMapper.selectByExample(example);
    }

    public List<SysDictDetail> findByMainCode(String mCode){
        Example example = new Example(SysDictDetail.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sdmCode",mCode);
        example.orderBy("sddSeq");
        return this.sysDictDetailMapper.selectByExample(example);
    }

    public void delById(Long mId){
        SysDictDetail dictDetail = new SysDictDetail();
        dictDetail.setSdmId(mId);
        this.sysDictDetailMapper.delete(dictDetail);
    }

    public void saveInfo(SysDictDetail dictDetail){
        if(dictDetail.getId()==null){
            this.sysDictDetailMapper.insert(dictDetail);
        }else{
            Example example = new Example(SysDictDetail.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("id",dictDetail.getId());
            this.sysDictDetailMapper.updateByExample(dictDetail,example);
        }
    }


    public List<PropertyVo> findByClassName(String className){
        List<PropertyVo> proList = ScanningFileUtil.getEntityFields(className);
        if(!CollectionUtils.isEmpty(proList)){
            for (PropertyVo propertyVo : proList) {
                if(!StringUtils.isEmpty(propertyVo.getEnumName())){
                    List<SysDictDetail> dictDetails = this.findByMainCode(propertyVo.getEnumName());
                    propertyVo.setDictDetails(dictDetails);
                }
            }
        }
        return proList;
    }
}
