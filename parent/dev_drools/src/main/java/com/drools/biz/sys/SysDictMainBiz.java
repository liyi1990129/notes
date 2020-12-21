package com.drools.biz.sys;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.drools.mapper.sys.SysDictMainMapper;
import com.drools.model.sys.SysDictDetail;
import com.drools.model.sys.SysDictMain;
import com.drools.util.ScanningFileUtil;
import com.drools.vo.PropertyVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SysDictMainBiz {
    @Resource
    private SysDictMainMapper sysDictMainMapper;

    public PageInfo page(Map<String, Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");
        Page page = PageHelper.startPage(pageNumber,pageSize);
        Example example = new Example(SysDictMain.class);
        Example.Criteria criteria = example.createCriteria();
        String sdmName = (String) params.get("sdmName");
        String sdmCode = (String) params.get("sdmCode");
        if(!StringUtils.isEmpty(sdmCode)){
            criteria.andEqualTo("sdmCode",sdmCode);
        }
        if(!StringUtils.isEmpty(sdmName)){
            criteria.andLike("sdmName","%"+sdmName+"%");
        }

        List<SysDictMain> list = this.sysDictMainMapper.selectByExample(example);

        PageInfo pageInfo = new PageInfo<>(list);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    public void delInfoById(Long id)  {
        Example example = new Example(SysDictMain.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",id);
        this.sysDictMainMapper.deleteByExample(example);
    }
    public SysDictMain getInfoById(Long id)  {
        SysDictMain sysDictMain = new SysDictMain();
        sysDictMain.setId(id);
        return this.sysDictMainMapper.selectOne(sysDictMain);
    }


    public Long saveOrUpdate(SysDictMain info){
        if(null== info.getId()){
            this.sysDictMainMapper.add(info);
        }else {
            Example example = new Example(SysDictMain.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("id",info.getId());
            this.sysDictMainMapper.updateByExample(info,example);

        }
        return info.getId();
    }

}
