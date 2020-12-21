package com.drools.util;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.FieldMethodName;
import com.drools.entity.clm.*;
import com.drools.mapper.BenefitFormulaMapper;
import com.drools.mapper.EventDateMapper;
import com.drools.model.BenefitFormula;
import com.drools.model.EventDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ClmUtil {

    /**
     * 判断是否有同一天的多次手术
     *
     * @return
     */
    @FieldMethodName(name = "判断是否有同一天的多次手术", entityCls = "com.drools.entity.clm.Claim", methodType = DroolsConstants.ClmUtilMethodType.TYPE_0)
    public static boolean hasSameDaySurgery(List<Doctor> doctors) {
        log.info("判断是否有同一天的多次手术");
        if (doctors == null || doctors.size() == 0) {
            return false;
        } else {
            //List list = new ArrayList();
            for (int idx = 0, len = doctors.size(); idx < len; idx++) {

                if (doctors.get(idx) == null || doctors.get(idx).getSurgeries() == null || doctors.get(idx).getSurgeryLength() == 0) {
                    continue;
                } else {
                    for (int idx2 = 0, len2 = doctors.get(idx).getSurgeries().size() - 1; idx2 < len2; idx2++) {
                        if (String.valueOf(doctors.get(idx).getSurgeries().get(idx2).getSurgDte()).equals(String.valueOf(doctors.get(idx).getSurgeries().get(idx2 + 1).getSurgDte()))
                                && !doctors.get(idx).getSurgeries().get(idx2).getSurgSeq().equals(doctors.get(idx).getSurgeries().get(idx2 + 1).getSurgSeq())) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }

    @FieldMethodName(name = "判断营销员不在职", entityCls = "com.drools.entity.clm.Claim", methodType = DroolsConstants.ClmUtilMethodType.TYPE_0)
    public static Boolean getSalemanInactive(long agtToDate) {
        log.info("判断营销员不在职getSalemanInactive[{}],[{}]", agtToDate);
        int day = ClmUtil.getSepcialDay(new Date());
        if (day > agtToDate) {
            log.info("返回true");
            return true;
        }
        return false;
    }

    /**
     * 得到指定哪几种收据类型的数量
     *
     * @param paperTyp
     * @return
     */
    @FieldMethodName(name = "收据类型的数量是否等于零", entityCls = "com.drools.entity.clm.Claim", methodType = DroolsConstants.ClmUtilMethodType.TYPE_1, params = "paperTyp=String", paramsDesc = "收据类型")
    public static boolean getReceiptQuantityOfTypeNum(List<Doctor> doctors, String paperTyp) {
        if (null == paperTyp || paperTyp.equals("")) {
            return false;
        }
        String[] array = paperTyp.split("、");

        if (null != doctors && doctors.size() > 0) {
            for (int i = 0; i < doctors.size(); i++) {
                if (null != doctors.get(i).getReceipts() && doctors.get(i).getReceiptLength() > 0) {
                    for (int j = 0; j < doctors.get(i).getReceiptLength(); j++) {
                        for (int k = 0; k < array.length; k++) {
                            if (doctors.get(i).getReceipts().get(j).getPaperTyp().equals(array[k]))
                                return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    @FieldMethodName(name = "年龄小于18岁", entityCls = "com.drools.entity.clm.Claim", methodType = DroolsConstants.ClmUtilMethodType.TYPE_0)
    public static boolean getYearDiff(int cltDob, int submitDte) {
        log.info("年龄小于18岁cltDob[{}]submitDte[{}]", cltDob, submitDte);
        int age = TimeUtil.getYearDiff(cltDob, submitDte);
        log.info("age:{}", age);
        if (age < 18) {
            return true;
        }
        return false;
    }


    /* *
     * 有住院日期
     * @author ly
     * @modifyTime 2020/11/18 16:53:00
     */
    @FieldMethodName(name = "有住院日期", entityCls = "com.drools.entity.clm.Claim", methodType = DroolsConstants.ClmUtilMethodType.TYPE_0)
    public static boolean getFirstHsDte(List<Doctor> doctors) {
        if (doctors.size() > 0) {
            for (int i = 0; i < doctors.size(); i++) {
                if (doctors.get(i).getHsDte() > 0)
                    return true;
            }
        }
        return false;
    }


    @FieldMethodName(name="一般出院日期",entityCls="com.drools.entity.clm.Claim",methodType=DroolsConstants.ClmUtilMethodType.TYPE_1,params = "params=int",paramsDesc="出院日期")
    public static boolean getFirstLeaveDte(List<Doctor> doctors,Integer params) {
        if(doctors.size() > 0){
            for(int i=0;i<doctors.size();i++){
                if(doctors.get(i).getLeaveDte() == params)
                    return true;
            }
        }
        return false;
    }
    @FieldMethodName(name="一般住院日期",entityCls="com.drools.entity.clm.Claim",methodType=DroolsConstants.ClmUtilMethodType.TYPE_1,params = "params=int",paramsDesc="住院日期")
    public static boolean getFirstHsDte(List<Doctor> doctors,Integer params) {
        if(doctors.size() > 0){
            for(int i=0;i<doctors.size();i++){
                if(doctors.get(i).getHsDte() == params)
                    return true;
            }
        }
        return false;
    }
    @FieldMethodName(name="ICU住院日期",entityCls="com.drools.entity.clm.Claim",methodType=DroolsConstants.ClmUtilMethodType.TYPE_1,params = "params=int",paramsDesc="ICU住院日期")
    public static boolean getFirstIcuHsDte(List<Doctor> doctors,Integer params) {
        if(doctors.size() > 0){
            for(int i=0;i<doctors.size();i++){
                if(doctors.get(i).getIcuHsDte() == params)
                    return true;
            }
        }
        return false;
    }
    @FieldMethodName(name="ICU出院日期",entityCls="com.drools.entity.clm.Claim",methodType=DroolsConstants.ClmUtilMethodType.TYPE_1,params = "params=int",paramsDesc="ICU出院日期")
    public static boolean getFirstIcuLeaveDte(List<Doctor> doctors,Integer params) {
        if(doctors.size() > 0){
            for(int i=0;i<doctors.size();i++){
                if(doctors.get(i).getIcuLeaveDte() == params)
                    return true;
            }
        }
        return false;
    }
    /* *
     * 有住院日期
     * @author ly
     * @modifyTime 2020/11/18 16:53:00
     */
    @FieldMethodName(name = "有ICU住院日期", entityCls = "com.drools.entity.clm.Claim", methodType = DroolsConstants.ClmUtilMethodType.TYPE_0)
    public static boolean getFirstIcuHsDte(List<Doctor> doctors) {
        if (doctors.size() > 0) {
            for (int i = 0; i < doctors.size(); i++) {
                if (doctors.get(i).getIcuHsDte() > 0)
                    return true;
            }
        }
        return false;
    }

    /**
     * 判断是否存在手术信息
     *
     * @return true-存在 false-不存在
     */
    @FieldMethodName(name="不存在手术信息",entityCls="com.drools.entity.clm.Claim",methodType=DroolsConstants.ClmUtilMethodType.TYPE_0)
    public static boolean isExistedSurgery(List<Doctor> doctors) {
        if(!CollectionUtils.isEmpty(doctors)){
            for(int idx = 0;idx<doctors.size();idx++){
                if(doctors.get(idx).getSurgeries() != null){
                    for(int idx2 = 0;idx2<doctors.get(idx).getSurgeryLength();idx2++){
                        if(doctors.get(idx).getSurgeries().get(idx2) != null && doctors.get(idx).getSurgeries().get(idx2).getSurgCode() != null && doctors.get(idx).getSurgeries().get(idx2).getSurgCode().trim().length() > 0 )
                            return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * 判断DC013、DC013 互斥
     * （为定额给付时互斥，为实质支付时不需判断）
     */

    @FieldMethodName(name = "判断DC013、DC014", entityCls = "com.drools.entity.clm.Claim", methodType = DroolsConstants.ClmUtilMethodType.TYPE_0)
    public static boolean mutexFile(List<Document> documents) {
        boolean bool = true;

        for (int i = 0; i < documents.size(); i++) {
            Document dc = documents.get(i);
            if (dc.getDocCode().equals("DC014") && dc.getDocCount() > 0) { // 文档审核
                bool = false;
            }
        }

        return bool;
    }

    @FieldMethodName(name="通用审核类型",entityCls="com.drools.entity.clm.Claim",methodType=DroolsConstants.ClmUtilMethodType.TYPE_1,params = "params=String",paramsDesc="赔案理赔形态")
    public static boolean isClaimTypeContain(String params,String claimType){
        log.info("通用审核类型[{}][{}]",params,claimType);
        boolean flag = StringUtil.isContain(params, claimType);
        log.info("通用审核类型返回：{}",flag);
        return flag;
    }

    /**
     * 判断是否存在伤病信息
     *
     * @return true-存在 false-不存在
     */
    @FieldMethodName(name="不存在伤病信息",entityCls="com.drools.entity.clm.Claim",methodType=DroolsConstants.ClmUtilMethodType.TYPE_0)
    public static boolean isExistedIllCodes(List<Doctor> doctors) {
        if(!CollectionUtils.isEmpty(doctors)){
            for(int idx = 0;idx<doctors.size();idx++){
                if(doctors.get(idx).getIllCodes() != null){
                    for(int idx2 = 0,len2=doctors.get(idx).getIllCodes().size();idx2<len2;idx2++){
                        if(doctors.get(idx).getIllCodes().get(idx2) != null && doctors.get(idx).getIllCodes().get(idx2).trim().length() > 0 )
                            return false;
                    }
                }
            }
        }

        return true;
    }
    @FieldMethodName(name="事故发生地不包含于",entityCls="com.drools.entity.clm.Claim",methodType=DroolsConstants.ClmUtilMethodType.TYPE_1,params = "params=String",paramsDesc="事故发生地")
    public static boolean isClaimEventPlaceNotContain(String params,String eventPlace){
        log.info("事故发生地不包含于[{}][{}]",params,eventPlace);
        return StringUtil.isNotContain(params,eventPlace);
    }
    @FieldMethodName(name="本次与历史赔案意外事故日间隔3天",entityCls="com.drools.entity.clm.HistoryClaim",methodType=DroolsConstants.ClmUtilMethodType.TYPE_1,params = "day=int",paramsDesc="天数")
    public static boolean isDateBetweenByAcdtDte(Claim claim,HistoryClaim historyClaim,int day){
        log.info("本次与历史赔案意外事故日间隔3天[{}]",day);
        return TimeUtil.isDateBetween(historyClaim.getAcdtDte(),claim.getAcdtDte(),day);
    }
    @FieldMethodName(name="本次与历史赔案事故认定日间隔天数在多少天内",entityCls="com.drools.entity.clm.HistoryClaim",methodType=DroolsConstants.ClmUtilMethodType.TYPE_1,params = "day=int",paramsDesc="天数")
    public static boolean isDateBetweenByEventDte(Claim claim,HistoryClaim historyClaim,int day){
        log.info("本次与历史赔案事故认定日间隔天数在多少天内[{}]",day);
        return TimeUtil.isDateBetween(historyClaim.getEventDte(),claim.getEventDte(),day);
    }
    @FieldMethodName(name="本次与历史赔案事故认定日间隔周年数",entityCls="com.drools.entity.clm.HistoryClaim",methodType=DroolsConstants.ClmUtilMethodType.TYPE_1,params = "sysbol=String,day=int",paramsDesc="比较符号(>、<、=),天数")
    public static boolean calYearByEvent(Claim claim,HistoryClaim historyClaim,String sysbol,int day){
        log.info("本次与历史赔案事故认定日间隔周年数[{}],[{}]",sysbol,day);
        int day1 = TimeUtil.calYear(historyClaim.getEventDte(),claim.getEventDte());
        if(">".equals(sysbol)){
            if(day1>day){
                return true;
            }
        }else if("<".equals(sysbol)){
            if(day1<day){
                return true;
            }
        }else if("<=".equals(sysbol)){
            if(day1<=day){
                return true;
            }
        }else if(">=".equals(sysbol)){
            if(day1>=day){
                return true;
            }
        }else if("=".equals(sysbol)){
            if(day1==day){
                return true;
            }
        }
        return false;
    }
    /**
     * 得到最早理赔间隔天数（不分前后）
     *
     * @param
     */
    public static int getEarlyClaimHistory(int historyClaimLength, int claimDay, List<HistoryClaim> historyClaims) {
        int evenDate = 0;
        if (historyClaimLength > 0) {
            for (int i = 0; i < historyClaimLength; i++) {
                if (0 != historyClaims.get(i).getEventDte()) {
                    evenDate = evenDate == 0 ? TimeUtil.calDayBetween(historyClaims.get(i).getEventDte(), claimDay) : evenDate;
                    if (evenDate > TimeUtil.calDayBetween(historyClaims.get(i).getEventDte(), claimDay)) {
                        evenDate = TimeUtil.calDayBetween(historyClaims.get(i).getEventDte(), claimDay);
                    }
                }
            }
        }


        return evenDate;
    }

    @FieldMethodName(name = "最近次赔付间隔多少天", entityCls = "com.drools.entity.clm.Policy", methodType = DroolsConstants.ClmUtilMethodType.TYPE_1, params = "days=int", paramsDesc = "天数")
    public static boolean getEarlyClaimHistory(int historyClaimLength, int claimDay, List<HistoryClaim> historyClaims, int days) {
        log.info("最近次赔付间隔多少天historyClaimLength:[{}],claimDay:[{}],days:[{}]", historyClaimLength, claimDay, days);
        int evenDate = getEarlyClaimHistory(historyClaimLength, claimDay, historyClaims);
        log.info("最近次赔付间隔多少天:[{}]", evenDate);
        if (evenDate < days) {
            return true;
        }
        return false;
    }


    /* *
     * 出险日距离保单复效日多少天内
     * @author ly
     * @modifyTime 2020/11/19 13:51:00
     */
    @FieldMethodName(name = "出险日距离保单复效日多少天内", entityCls = "com.drools.entity.clm.Policy", methodType = DroolsConstants.ClmUtilMethodType.TYPE_3, params = "days=int", paramsDesc = "天数")
    public static boolean getSepcialDay(Claim claim, Policy policy, int days) {
        log.info("出险日距离保单复效日多少天内{}", days);
        int day = TimeUtil.getSepcialDay(policy.getReinsDate(), 2, days);
        log.info("getEventDte:{},day:{}", claim.getEventDte(), day);
        if (claim.getEventDte() < day) {
            return true;
        }
        return false;
    }

    @FieldMethodName(name="事故认定日与保费缴至日后多少日",entityCls="com.drools.entity.clm.Policy",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "days=int,symbol=String",paramsDesc="天数,比较符号(>、<=)")
    public static boolean eqEventDteAndPtDte(Claim claim, Policy policy,int days,String symbol){
        log.info("事故认定日与保费缴至日后多少日{}",days);
        int day = TimeUtil.getSepcialDay(policy.getPtDate(),2,days);
        log.info("getEventDte:{},day:{}",claim.getEventDte(),day);
        if(">".equals(symbol)){
            if(claim.getEventDte()>day){
                return true;
            }
        }else if("<=".equals(symbol)){
            if(claim.getEventDte()<=day){
                return true;
            }
        }
        return false;
    }
    @FieldMethodName(name="事故认定日与下期保障费用收取前一月后多少日",entityCls="com.drools.entity.clm.Policy",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "days=int,symbol=String",paramsDesc="天数,比较符号(>、<=)")
    public static boolean eqEventDteAndBenBillDte(Claim claim, Policy policy,int days,String symbol){
        log.info("事故认定日与下期保障费用收取前一月后多少日{}",days);
        int day = TimeUtil.getSepcialDay(policy.getBenBillDate(),3);
        int endDay = TimeUtil.getSepcialDay(day,2,days);
        log.info("getEventDte:{},endDay:{}",claim.getEventDte(),day);
        if(">".equals(symbol)){
            if(claim.getEventDte()>endDay){
                return true;
            }
        }else if("<=".equals(symbol)){
            if(claim.getEventDte()<=endDay){
                return true;
            }
        }
        return false;
    }

    /**
     * 某些险种等待期事故特殊处理
     * crtable1：配置的
     * crtable2：实际险种
     * claimType：理赔型态
     */
    @FieldMethodName(name="出险日距离险种生效日多少天内",entityCls="com.drools.entity.clm.Plan",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "crtable1=String",paramsDesc="险种")
    public static boolean waitAccident(String crtable1,Claim claim, Plan plan){
        log.debug("进入某些险种等待期事故特殊处理waitAccident");
        String claimType = claim.getClaimType();
        String crtable2 = plan.getCrtable();
        if(crtable1 == null || crtable2 == null || claimType == null){
            log.debug("crtable1="+crtable1+",crtable2="+crtable2+",claimType="+claimType+"返回的结果是="+true);
            return true;
        }else if(StringUtil.isContain(crtable1,crtable2)){
            if(StringUtil.isContain("D、T",claimType)){//理赔型态为身故或全残
                log.debug("险种="+crtable2+",满足理赔形态，理赔形态为="+claimType+",返回的结果是="+false);
                return false;

            }else {
                log.debug("险种="+crtable2+",不满足理赔形态，理赔形态为="+claimType+",返回的结果是="+true);
                return true;
            }
        }else{
            log.debug("不满足险种,险种="+crtable2+",返回的结果是="+true);
            return true;
        }
    }
    @FieldMethodName(name="出险日距离险种生效日多少天内",entityCls="com.drools.entity.clm.Plan",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "days=int",paramsDesc="天数")
    public static boolean getPlanSepcialDay(Claim claim, Plan plan,int days){
        log.info("出险日距离险种生效日多少天内{}",days);
        int day = TimeUtil.getSepcialDay(plan.getCrrCd(),2,days);
        log.info("getEventDte:{},day:{}",claim.getEventDte(),day);
        if(claim.getEventDte()<day){
            return true;
        }
        return false;
    }

    @FieldMethodName(name = "状态非有效", entityCls = "com.drools.entity.clm.Plan", methodType = DroolsConstants.ClmUtilMethodType.TYPE_1, params = "params=String", paramsDesc = "保单状态")
    public static boolean isPlanNotContain(String params, String statCode) {
        log.info("状态非有效[{}][{}]", params, statCode);
        return StringUtil.isNotContain(params, statCode);
    }

    @FieldMethodName(name = "险种产品(多个)", entityCls = "com.drools.entity.clm.Plan", methodType = DroolsConstants.ClmUtilMethodType.TYPE_1, params = "params=String", paramsDesc = "险种产品")
    public static boolean isPlanContain(String params, String prdTyp) {
        log.info("险种产品(多个)[{}][{}]", params, prdTyp);
        boolean flag = StringUtil.isContain(params, prdTyp);
        log.info("返回：{}",flag);
        return flag;
    }
    @FieldMethodName(name="险种代码包含",entityCls="com.drools.entity.clm.Plan",methodType=DroolsConstants.ClmUtilMethodType.TYPE_1,params = "params=String",paramsDesc="险种产品")
    public static boolean isCrtableContain(String params,String crtable){
        log.info("险种代码包含[{}][{}]",params,crtable);
        return StringUtil.isContain(params,crtable);
    }
    @FieldMethodName(name="包含statSect",entityCls="com.drools.entity.clm.Plan",methodType=DroolsConstants.ClmUtilMethodType.TYPE_1,params = "params=String",paramsDesc="statSect")
    public static boolean isPlanStatSectContain(String params,String statSect){
        log.info("包含statSect[{}][{}]",params,statSect);
        return StringUtil.isContain(params,statSect);
    }
    @FieldMethodName(name="包含险种类型",entityCls="com.drools.entity.clm.Plan",methodType=DroolsConstants.ClmUtilMethodType.TYPE_1,params = "params=String",paramsDesc="险种类型")
    public static boolean isPlanStatFundContain(String params,String covrStatFund){
        log.info("包含险种类型[{}][{}]",params,covrStatFund);
        return StringUtil.isContain(params,covrStatFund);
    }
    @FieldMethodName(name="险种状态不包含",entityCls="com.drools.entity.clm.Plan",methodType=DroolsConstants.ClmUtilMethodType.TYPE_1,params = "params=String",paramsDesc="险种状态")
    public static boolean isPlanStatCodeNotContain(String params,String statCode){
        log.info("险种状态不包含[{}][{}]",params,statCode);
        return StringUtil.isNotContain(params,statCode);
    }

    @FieldMethodName(name="事故认定日时年龄",entityCls="com.drools.entity.clm.Plan",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "age1=int,age2=int",paramsDesc="年龄1,年龄2")
    public static boolean calYear(Claim claim,Plan plan,int age1,int age2){
        int age = TimeUtil.calYear(claim.getCltDob(),plan.getEventDte(),claim.getEventDte());
        log.info("事故认定日时年龄[{}][{}][{}]",age,age1,age2);
        if(age>=age1 && age<age2){
            return true;
        }
        return false;
    }
    @FieldMethodName(name="险种是否有除外责任",entityCls="com.drools.entity.clm.Plan",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3)
    public static boolean isContainOneByExcludeBenefits(Claim claim,Plan plan){
        if(CollectionUtils.isEmpty(plan.getExcludeBenefits())){
            return false;
        }
        String[] s =  plan.getExcludeBenefits().toArray(new String[plan.getExcludeBenefits().size()]);
        return StringUtil.isContainOne(s,claim.getExcludeBenefit());
    }
    @FieldMethodName(name="险种是否有除外意外事故码",entityCls="com.drools.entity.clm.Plan",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3)
    public static boolean isContainOneByExcludeAcdtCodes(Claim claim,Plan plan){
        if(CollectionUtils.isEmpty(plan.getExcludeAcdtCodes())){
            return false;
        }
        String[] s =  plan.getExcludeAcdtCodes().toArray(new String[plan.getExcludeAcdtCodes().size()]);
        return StringUtil.isContainOne(s,claim.getAcdtCode());
    }
    @FieldMethodName(name="险种是否有除外意外事故地",entityCls="com.drools.entity.clm.Plan",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3)
    public static boolean isContainOneByExcludeEventPlaces(Claim claim,Plan plan){
        if(CollectionUtils.isEmpty(plan.getExcludeEventPlaces())){
            return false;
        }
        String[] s =  plan.getExcludeEventPlaces().toArray(new String[plan.getExcludeEventPlaces().size()]);
        return StringUtil.isContainOne(s,claim.getEventPlace());
    }
    @FieldMethodName(name="存在除外年龄",entityCls="com.drools.entity.clm.Plan",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3)
    public static boolean isExcludeAge(Claim claim,Plan plan){
        int day = TimeUtil.calDay(claim.getCltDob(),claim.getCriticalDte());
        if(plan.getExcludeAge() > day){
            return true;
        }
        return false;
    }
    @FieldMethodName(name="就医存在除外伤病代码",entityCls="com.drools.entity.clm.Plan",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3)
    public static boolean isExcludeAge(Doctor doctor,Plan plan){
        if(CollectionUtils.isEmpty(plan.getExcludeIllCodes()) || CollectionUtils.isEmpty(doctor.getIllCodes())){
            return false;
        }
        String[] s =  plan.getExcludeIllCodes().toArray(new String[plan.getExcludeIllCodes().size()]);
        return StringUtil.isContains(s,  doctor.getIllCodes().toArray(new String[doctor.getIllCodes().size()]));
    }
    @FieldMethodName(name="就医存在除外意外手术码",entityCls="com.drools.entity.clm.Plan",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3)
    public static boolean isExcludeSurgeryCodes(Doctor doctor,Plan plan){
        if(CollectionUtils.isEmpty(plan.getExcludeSurgeryCodes())){
            return false;
        }
        String[] s1 =  plan.getExcludeSurgeryCodes().toArray(new String[plan.getExcludeSurgeryCodes().size()]);
        if(!CollectionUtils.isEmpty(doctor.getSurgeries())){
            String[] s = new String[doctor.getSurgeries().size()];
            for (int i=0;i<doctor.getSurgeries().size();i++) {
                Surgery surgery = doctor.getSurgeries().get(i);
                s[i] = surgery.getSurgCode();
            }
            return StringUtil.isContains(s1, s);
        }else{
            return false;
        }
    }


    @FieldMethodName(name="本次赔付单个特殊责任时捆绑跨同组保额续赔责任",entityCls="com.drools.entity.clm.Plan",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "benCodeAll=String",paramsDesc="责任")
    public static boolean checkBenefitFormula(Plan plan,String benCodeAll){
        String[] array = benCodeAll.split("、");
        for(int i = 0;i<plan.getBenefitLength();i++){
            Benefit ben = plan.getBenefits().get(i);
            for(int j=0;j<array.length;j++){
                if(ben.getBenCode().equals(array[j])&&!ben.getFormulaStr().equals("")){
                    return true;
                }
            }
        }
        return false;
    }

    @FieldMethodName(name="责任在历史中是否赔付",entityCls="com.drools.entity.clm.HistoryPlan",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "benCode=String",paramsDesc="责任")
    public static boolean isExistBenefit(HistoryPlan historyPlan,String benCode){
        String[] array = benCode.split("、");
        for(int i=0;i<historyPlan.getBenefitLength();i++){
            for(int j=0;j<array.length;j++){
                if(historyPlan.getBenefits().get(i).getBenCode().equals(array[j]) && historyPlan.getBenefits().get(i).getPayAmt() > 0){
                    return true;
                }
            }
        }

        return false;
    }

    @FieldMethodName(name="受款人不为事故人",entityCls="com.drools.entity.clm.Receiver",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3)
    public static boolean isReceverNotContain(Claim claim,Receiver receiver){
        log.info("受款人不为事故人[{}][{}]",claim.getClntNumes(),receiver.getClntNum());
        for(int i=0,len = claim.getClntNumes().length;i<len;i++){
            if(claim.getClntNumes()[i].trim().length() > 0 && claim.getClntNumes()[i].trim().equals(receiver.getClntNum())){
                return false;
            }
        }
        return true;
    }


    @FieldMethodName(name="判断是否赔付了指定责任",entityCls="com.drools.entity.clm.HistoryClaim",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "benCodes=String",paramsDesc="责任")
    public static boolean isBenefitExisted(Plan plan,HistoryClaim historyClaim,String benCodes){
        HistoryPlan historyPlan = getPlan(plan, historyClaim);
        if(historyPlan == null){
            return false;
        }else{
            String[] benCode = benCodes.split(StringUtil.SPERATION_SYMBOL);

            for(int i=0;i<historyPlan.benefitLength;i++){
                for(int j=0;j<benCode.length;j++){
                    if(historyPlan.getBenefits().get(i).getPayAmt() >= 0.01
                        && historyPlan.getBenefits().get(i).getBenCode().equals(benCode[j])){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 根据险种信息，获得历史理赔的险种信息
     * @return
     */
    public static HistoryPlan getPlan(Plan plan,HistoryClaim historyClaim){
        String life = plan.getLife();
        String coverage = plan.getCoverage();
        String rider = plan.getRider();
        String crtable = plan.getCrtable();
        for(int i=0;i<historyClaim.getPlanLength();i++){
            HistoryPlan historyPlan = historyClaim.getPlans().get(i);
            if(historyPlan.getLife().equals(life)
                && historyPlan.getCoverage().equals(coverage)
                && historyPlan.getRider().equals(rider)
                && historyPlan.getCrtable().equals(crtable)){
                return historyPlan;
            }
        }

        return null;
    }


    /**
     * 判断手术日期是否有任意一个在指定的日期范围内
     * @return
     */
    @FieldMethodName(name="判断手术日期是否有任意一个在指定的日期范围内",entityCls="com.drools.entity.clm.Doctor",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "day=int",paramsDesc="天数")
    public static boolean isAnySurgeryBetweenDate(HistoryClaim historyClaim,Doctor doctor,int day) {
        int sDay = historyClaim.getAcdtDte();
        int eDay = TimeUtil.getSepcialDay(sDay,2,day);
        if(!CollectionUtils.isEmpty(doctor.getSurgeries())){
            for(int i=0;i<doctor.getSurgeries().size();i++){
                if(doctor.getSurgeries().get(i).getSurgDte() >= sDay && doctor.getSurgeries().get(i).getSurgDte() <= eDay)
                    return true;
            }
        }else{
            return false;
        }

        return false;
    }
    /**
     * 判断手术日期是否有任意一个在指定的日期范围内
     * @return
     */
    @FieldMethodName(name="本次伤病代码包括",entityCls="com.drools.entity.clm.Doctor",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "codes=String",paramsDesc="伤病代码")
    public static boolean isContainsEqualOrLike(Doctor doctor,String codes) {
        if(CollectionUtils.isEmpty(doctor.getIllCodes())){
            return false;
        }
       return StringUtil.isContainsEqualOrLike(doctor.getIllCodes().toArray(new String[doctor.getIllCodes().size()]),codes);
    }


    @FieldMethodName(name="住出院日期间隔",entityCls="com.drools.entity.clm.HistoryDoctor",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "day=int",paramsDesc="天数")
    public static boolean isDateBetweenByHsDte(Doctor doctor,HistoryDoctor historyDoctor,int day) {
        return TimeUtil.isDateBetween(historyDoctor.getLeaveDte(),doctor.getHsDte(),day);
    }

    @FieldMethodName(name="出住院日期间隔",entityCls="com.drools.entity.clm.HistoryDoctor",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "day=int",paramsDesc="天数")
    public static boolean isDateBetweenByLeaveDte(Doctor doctor,HistoryDoctor historyDoctor,int day) {
        return TimeUtil.isDateBetween(historyDoctor.getHsDte(),doctor.getLeaveDte(),day);
    }

    @FieldMethodName(name="ICU住出院日期间隔",entityCls="com.drools.entity.clm.HistoryDoctor",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "day=int",paramsDesc="天数")
    public static boolean isDateBetweenByIcuHsDte(Doctor doctor,HistoryDoctor historyDoctor,int day) {
        return TimeUtil.isDateBetween(historyDoctor.getIcuLeaveDte(),doctor.getIcuHsDte(),day);
    }
    @FieldMethodName(name="ICU住出院日期间隔",entityCls="com.drools.entity.clm.HistoryDoctor",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "day=int",paramsDesc="天数")
    public static boolean isDateBetweenByIcuLeaveDte(Doctor doctor,HistoryDoctor historyDoctor,int day) {
        return TimeUtil.isDateBetween(historyDoctor.getIcuHsDte(),doctor.getIcuLeaveDte(),day);
    }
    @FieldMethodName(name="伤病代码是否同组",entityCls="com.drools.entity.clm.HistoryDoctor",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3)
    public static boolean isContainIllCodes(Doctor doctor,HistoryDoctor historyDoctor,Benefit benefit) {
        if(CollectionUtils.isEmpty(historyDoctor.getIllCodes()) || CollectionUtils.isEmpty(doctor.getIllCodes())){
            return false;
        }
        String[] s1 = historyDoctor.getIllCodes().toArray(new String[historyDoctor.getIllCodes().size()]);
        String[] s2 = doctor.getIllCodes().toArray(new String[doctor.getIllCodes().size()]);
        return StringUtil.isContain(s1,s2,benefit.getIllCodeArray());
    }
    @FieldMethodName(name="自定义伤病代码是否同组(不校验同类同组)",entityCls="com.drools.entity.clm.HistoryDoctor",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "codes=String",paramsDesc="伤病代码")
    public static boolean isCheckSameIllCodeGroup(Doctor doctor,HistoryDoctor historyDoctor,String codes) {
        if(CollectionUtils.isEmpty(historyDoctor.getIllCodes()) || CollectionUtils.isEmpty(doctor.getIllCodes())){
            return false;
        }
        String[] s1 = historyDoctor.getIllCodes().toArray(new String[historyDoctor.getIllCodes().size()]);
        String[] s2 = doctor.getIllCodes().toArray(new String[doctor.getIllCodes().size()]);
        return StringUtil.isCheckSameIllCodeGroup(s1,s2,codes);
    }
    @FieldMethodName(name="自定义伤病代码是否同组",entityCls="com.drools.entity.clm.HistoryDoctor",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "codes=String",paramsDesc="伤病代码")
    public static boolean isCheckAllSameIllCodeGroup(Doctor doctor,HistoryDoctor historyDoctor,String codes) {
        if(CollectionUtils.isEmpty(historyDoctor.getIllCodes()) || CollectionUtils.isEmpty(doctor.getIllCodes())){
            return false;
        }
        String[] s1 = historyDoctor.getIllCodes().toArray(new String[historyDoctor.getIllCodes().size()]);
        String[] s2 = doctor.getIllCodes().toArray(new String[doctor.getIllCodes().size()]);
        return StringUtil.isCheckAllSameIllCodeGroup(s1,s2,codes);
    }
    @FieldMethodName(name="本次与历史手术是否都进行了指定手术",entityCls="com.drools.entity.clm.HistoryDoctor",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "codes=String",paramsDesc="手术")
    public static boolean isContainBySurgeryCode(Doctor doctor,HistoryDoctor historyDoctor,String codes) {
        return StringUtil.isContain(getSurgeryCode(historyDoctor),getSurgeryCode(doctor),codes);
    }
    @FieldMethodName(name="自定义伤病代码指定长度为同一组",entityCls="com.drools.entity.clm.HistoryDoctor",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "codes=int",paramsDesc="伤病代码长度")
    public static boolean isEqualILLCodeLength(Doctor doctor,HistoryDoctor historyDoctor,int codes) {
        if(CollectionUtils.isEmpty(historyDoctor.getIllCodes()) || CollectionUtils.isEmpty(doctor.getIllCodes())){
            return false;
        }
        String[] s1 = historyDoctor.getIllCodes().toArray(new String[historyDoctor.getIllCodes().size()]);
        String[] s2 = doctor.getIllCodes().toArray(new String[doctor.getIllCodes().size()]);
        return StringUtil.isEqualILLCodeLength(s1,s2,codes);
    }
    @FieldMethodName(name="历史与本次伤病代码相同",entityCls="com.drools.entity.clm.HistoryDoctor",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3)
    public static boolean isContainsHistory(Doctor doctor,HistoryDoctor historyDoctor) {
        if(CollectionUtils.isEmpty(historyDoctor.getIllCodes()) || CollectionUtils.isEmpty(doctor.getIllCodes())){
            return false;
        }
        String[] s1 = historyDoctor.getIllCodes().toArray(new String[historyDoctor.getIllCodes().size()]);
        String[] s2 = doctor.getIllCodes().toArray(new String[doctor.getIllCodes().size()]);
        return StringUtil.isContains(s1,s2);
    }
    @FieldMethodName(name="历史伤病代码",entityCls="com.drools.entity.clm.HistoryDoctor",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "codes=String",paramsDesc="伤病代码")
    public static boolean isCheckHtryIllCodeContains(HistoryDoctor historyDoctor,String codes) {
        if(CollectionUtils.isEmpty(historyDoctor.getIllCodes()) ){
            return false;
        }
        String[] s1 = historyDoctor.getIllCodes().toArray(new String[historyDoctor.getIllCodes().size()]);
        return StringUtil.isCheckHtryIllCodeContains(s1,codes);
    }



    @FieldMethodName(name="本次手术日期在历史事故认定日后多少天前",entityCls="com.drools.entity.clm.Surgery",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "day=int",paramsDesc="天数")
    public static boolean isDateAfter(HistoryClaim historyClaim,Surgery surgery,int day) {
        return TimeUtil.isDateAfter(historyClaim.getEventDte(),surgery.getSurgDte(),day);
    }
    @FieldMethodName(name="本次手术日期在本次事故认定日后多少天前",entityCls="com.drools.entity.clm.Surgery",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "day=int",paramsDesc="天数")
    public static boolean isDateAfter(Claim claim,Surgery surgery,int day) {
        return TimeUtil.isDateBefore(claim.getEventDte(),surgery.getSurgDte(),day);
    }
    @FieldMethodName(name="本次手术代码包括",entityCls="com.drools.entity.clm.Surgery",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "codes=String",paramsDesc="手术代码")
    public static  boolean isContainSurgeryCode(Surgery surgery,String codes) {
        return StringUtil.isContain(codes,surgery.getSurgCode());
    }
    @FieldMethodName(name="本次手术不包括",entityCls="com.drools.entity.clm.Surgery",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "codes=String",paramsDesc="手术代码")
    public static boolean isNotContainSurgeryCode(Surgery surgery,String codes) {
        return StringUtil.isNotContain(codes,surgery.getSurgCode());
    }

    @FieldMethodName(name="历史手术代码包括",entityCls="com.drools.entity.clm.HistorySurgery",methodType=DroolsConstants.ClmUtilMethodType.TYPE_3,params = "codes=String",paramsDesc="手术代码")
    public static boolean isContainHistorySurgeryCode(HistorySurgery surgery,String codes) {
        return StringUtil.isContain(codes,surgery.getSurgCode());
    }

    /**
     * 获得手术代码数组
     *
     * @return
     */
    public static String[] getSurgeryCode(HistoryDoctor historyDoctor) {
        if(!CollectionUtils.isEmpty(historyDoctor.getSurgeries())){
            String[] s = new String[historyDoctor.getSurgeries().size()];
            for(int i=0;i<historyDoctor.getSurgeries().size();i++){
                s[i] = historyDoctor.getSurgeries().get(i).getSurgCode();
            }
            return s;
        }else{
            return null;
        }
    }
    public static String[] getSurgeryCode(Doctor doctor) {
        if(!CollectionUtils.isEmpty(doctor.getSurgeries())){
            String[] s = new String[doctor.getSurgeries().size()];
            for(int i=0;i<doctor.getSurgeries().size();i++){
                s[i] = doctor.getSurgeries().get(i).getSurgCode();
            }
            return s;
        }else{
            return null;
        }
    }
    /**
     * 把日期转换为8位数字形式
     *
     * @param day
     * @return
     */
    public static int getSepcialDay(Date day) {
        if (day == null)
            return 0;
        else
            return Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(day)).intValue();
    }


    /**
     * 得到最早的入院日期
     *
     * @return
     */
    public static long getEarlyHsDte(List<Doctor> doctors) {
        long earlyHsDte = 0;
        if (!CollectionUtils.isEmpty(doctors)) {
            for (Doctor doctor : doctors) {
                if (0 != doctor.hsDte) {
                    earlyHsDte = earlyHsDte == 0 ? doctor.hsDte : earlyHsDte;
                    if (earlyHsDte > doctor.hsDte) {
                        earlyHsDte = doctor.hsDte;
                    }
                }
                if (0 != doctor.icuHsDte) {
                    earlyHsDte = earlyHsDte == 0 ? doctor.icuHsDte : earlyHsDte;
                    if (earlyHsDte > doctor.icuHsDte) {
                        earlyHsDte = doctor.icuHsDte;
                    }
                }
            }
        }
        return earlyHsDte;
    }

    /**
     * 得到最早的手术日期
     *
     * @return
     */
    public static int getEarlySurgDte(List<Doctor> doctors) {
        int earlySurgDte = 0;
        if (!CollectionUtils.isEmpty(doctors)) {
            for (Doctor doctor : doctors) {
                if (doctor != null) {
                    if (CollectionUtils.isEmpty(doctor.getSurgeries())) {
                        for (Surgery surgery : doctor.surgeries) {
                            if (0 != surgery.surgDte) {
                                earlySurgDte = earlySurgDte == 0 ? surgery.surgDte : earlySurgDte;
                                if (earlySurgDte > surgery.surgDte) {
                                    earlySurgDte = surgery.surgDte;
                                }
                            }
                        }
                    }
                }
            }
        }

        return earlySurgDte;
    }

    /**
     * 得到最早的就医信息
     *
     * @return
     */
    public static int getEarlyConsultDte(List<Doctor> doctors) {
        int earlyconsultDte = 0;

        if (!CollectionUtils.isEmpty(doctors)) {
            for (Doctor doctor : doctors) {
                if (0 != doctor.consultDte) {
                    earlyconsultDte = earlyconsultDte == 0 ? (int) doctor.consultDte : earlyconsultDte;
                    if (earlyconsultDte > doctor.consultDte) {
                        earlyconsultDte = (int) doctor.consultDte;
                    }
                }
            }
        }

        return earlyconsultDte;
    }

    /**
     * 得到就医信息中所有的伤病代码
     *
     * @return
     */
    public static String[] getAllIllcodes(List<Doctor> doctors) {
        Set set = new HashSet();
        if (!CollectionUtils.isEmpty(doctors)) {
            for (Doctor doctor : doctors) {
                if (CollectionUtils.isEmpty(doctor.illCodes)) {
                    for (String code : doctor.illCodes) {
                        if (code != null && !set.contains(code))
                            set.add(code);
                    }
                }
            }
            String[] s = new String[set.size()];
            set.toArray(s);
            return s;
        }
        return null;
    }
}
