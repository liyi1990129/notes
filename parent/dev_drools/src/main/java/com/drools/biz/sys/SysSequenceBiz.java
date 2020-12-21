package com.drools.biz.sys;

import com.drools.mapper.sys.SysSequenceMapper;
import com.drools.model.sys.SysSequence;
import com.drools.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
public class SysSequenceBiz {
    @Resource
    private SysSequenceMapper sysSequenceMapper;

    /**
     * @param seqName
     * @return
     */
    public String gainNoByType(String seqName){
        log.info("生成单号：{}",seqName);
        String bizNo = "";
        synchronized(this){
            //获取数据库中最大值
            Long maxNo = this.gainBizNo(seqName);
            //获取流水号
            String curNo = this.gainNo(String.valueOf(maxNo));
            bizNo = seqName + DateUtil.getyMdHms(new Date())+curNo;
        }

        return bizNo;
    }

    /**
     * 获取业务代码
     * @return
     */
    public Long gainBizNo(String seqName) {

        //获取当前流水号
        SysSequence insSequence = new SysSequence();
        insSequence.setSeqName(seqName);

        Long seqNo = sysSequenceMapper.getPrdSequence(insSequence);

        log.debug("业务代码【{}】流水号【{}】",seqName,seqNo);
        return seqNo;
    }

    //获取7位流水号
    public String gainNo(String maxNo){
        String seqNo = "";
        if(StringUtils.isEmpty(maxNo)){
            return "0000000001";
        }
        if(maxNo.length()<10){
            int x = 10-maxNo.length();
            for(int i=0;i<x;i++){
                seqNo += "0";
            }
            seqNo = seqNo + maxNo;
        }else{
            seqNo = maxNo;
        }
        return seqNo;
    }

    //重置到上一位序号
    public void restSeqNo(String seqName) {
        //获取当前流水号
        SysSequence insSequence = new SysSequence();
        insSequence.setSeqName(seqName);
        SysSequence sequence = sysSequenceMapper.selectOne(insSequence);

        if (sequence != null) {
            if (sequence.getCurrentValue() != 1) {
                Long seqNo = sequence.getCurrentValue() - sequence.getIncrement();
                sequence.setCurrentValue(seqNo);
                this.sysSequenceMapper.updateByPrimaryKey(sequence);
            }
        }
    }
}
