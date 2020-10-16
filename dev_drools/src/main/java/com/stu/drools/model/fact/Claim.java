package com.stu.drools.model.fact;

import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class Claim {
    private Date agtToDate;
    private String claimType;
    private Boolean hasSameDaySurgery;
    private String reqnType;
    private String name;
    public Set noteSet = new HashSet();

    /**
     * 插入一个新照会
     *
     * @param pnCode 照会码
     * @param trecTyp 类型
     */
    public void addNote(String pnCode, String trecTyp){
        Note note = new Note();
        note.pnCode = pnCode;
        note.trecTyp = trecTyp;
//        note.pnDate = DateUtil.getToday();
        note.chdrNum = "";
        note.remark01 = "";

        if(!this.noteSet.contains(note))
            this.noteSet.add(note);
    }

}
