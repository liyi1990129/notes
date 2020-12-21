package com.drools.common;

public class DroolsConstants {

    /* *
     * 场景标识
     * @author ly
     * @modifyTime 2020/11/17 15:13:00
     */
    public static final class SceneIdentities{
        //建档
        public static final String ADMIN_AUDIT_CLAIM_CREATE_01  = "admin-audit-claim-create-01";//赔案行政规则
        public static final  String ADMIN_AUDIT_CLAIM_CREATE_02 = "admin-audit-claim-create-02";//受款人行政规则

        //资料确认
        public static final  String ADMIN_AUDIT_CLAIM_CONFIRM_01 = "admin-audit-claim-confirm-01";//保单行政规则
        public static final  String ADMIN_AUDIT_CLAIM_CONFIRM_02 = "admin-audit-claim-confirm-02";//受款人行政规则
        public static final  String ADMIN_AUDIT_CLAIM_CONFIRM_03 = "admin-audit-claim-confirm-03";//险种行政规则
        public static final  String ADMIN_AUDIT_CLAIM_CONFIRM_04 = "admin-audit-claim-confirm-04";//就医行政规则
        public static final  String ADMIN_AUDIT_CLAIM_CONFIRM_05 = "admin-audit-claim-confirm-05";//赔案文件审核规则
        public static final  String ADMIN_AUDIT_CLAIM_CONFIRM_06 = "admin-audit-claim-confirm-06";//险种文件审核规则

        //审核
        public static final  String ADMIN_AUDIT_CLAIM_AUDIT_01 = "admin-audit-claim-audit-01";//通用审核(前置)-被保人不是事故本人
        public static final  String ADMIN_AUDIT_CLAIM_AUDIT_02 = "admin-audit-claim-audit-02";//通用审核(前置)-险种大类
        public static final  String ADMIN_AUDIT_CLAIM_AUDIT_03 = "admin-audit-claim-audit-03";//通用审核(前置)-保障类型
        public static final  String ADMIN_AUDIT_CLAIM_AUDIT_04 = "admin-audit-claim-audit-04";//通用审核(前置)-险种类型
        public static final  String ADMIN_AUDIT_CLAIM_AUDIT_05 = "admin-audit-claim-audit-05";//通用审核(前置)-安心倚医疗险

        public static final  String ADMIN_AUDIT_CLAIM_MEDICAL_01 = "admin-audit-claim-medical-01";//通用审核(后置)-非有效状态的险种审核拒赔
        public static final  String ADMIN_AUDIT_CLAIM_MEDICAL_02 = "admin-audit-claim-medical-02";//通用审核(后置)-意外事故地
        public static final  String ADMIN_AUDIT_CLAIM_MEDICAL_03 = "admin-audit-claim-medical-03";//通用审核(后置)-重疾住院津贴

        public static final  String ADMIN_AUDIT_CLAIM_MEDICAL_04 = "admin-audit-claim-medical-04";//通用审核(后置)-险种有除外责任
        public static final  String ADMIN_AUDIT_CLAIM_MEDICAL_05 = "admin-audit-claim-medical-05";//通用审核(后置)-险种有除外事故代码
        public static final  String ADMIN_AUDIT_CLAIM_MEDICAL_06 = "admin-audit-claim-medical-06";//通用审核(后置)-险种有除外意外事故地代码
        public static final  String ADMIN_AUDIT_CLAIM_MEDICAL_07 = "admin-audit-claim-medical-07";//通用审核(后置)-险种有除外年龄
        public static final  String ADMIN_AUDIT_CLAIM_MEDICAL_08 = "admin-audit-claim-medical-08";//通用审核(后置)-险种有除外伤病代码

        public static final  String ADMIN_AUDIT_CLAIM_MEDICAL_09 = "admin-audit-claim-medical-09";//续赔件-历史赔案续赔件
        public static final  String ADMIN_AUDIT_CLAIM_MEDICAL_10 = "admin-audit-claim-medical-10";//续赔件-手术续赔
        public static final  String ADMIN_AUDIT_CLAIM_MEDICAL_11 = "admin-audit-claim-medical-11";//续赔件-历史就医续赔件
        public static final  String ADMIN_AUDIT_CLAIM_MEDICAL_12 = "admin-audit-claim-medical-12";//续赔件-历史就医手术续赔件

    }
    /* *
     * 险种审核状态
     * @author ly
     * @modifyTime 2020/11/17 15:13:00
     */
    public static final class PlanExamStatus{
        public static final  String PE = "PE";
        public static final  String EL = "EL";
        public static final  String DC = "DC";
        public static final  String NA = "NA";
    }
    /* *
     * 保单审核状态
     * @author ly
     * @modifyTime 2020/11/17 15:13:00
     */
    public static final class PolicyExamStatus{
        public static final  String PE = "PE";
        public static final  String EL = "EL";
        public static final  String DC = "DC";
        public static final  String NA = "NA";
        public static final  String EX = "EX";
        public static final  String CO = "CO";
    }
    /* *
     * 接口返回code
     * @author ly
     * @modifyTime 2020/11/17 15:13:00
     */
    public static final class ResultApi{
        public static final  String SUCCESS = "0";//成功
        public static final  String ERROR = "1";//失败
    }

    /* *
     * 生成流水号编号代码
     * @author ly
     * @modifyTime 2020/11/13 10:01:00
     */
    public static final   class SeqName{
        public static final  String RULE_CODE = "R";//规则编号
        public static final  String CONDITION_CODE = "C";//规则条件编号
        public static final  String ACTION_CODE = "A";//规则动作编号
        public static final  String BATCH_CODE = "S";//场景执行批次编号
    }

    /* *
     * 规则条件类型
     * @author ly
     * @modifyTime 2020/11/13 10:01:00
     */
    public static final   class ConditionType{
        public static final  String PROPERTY = "1";//'实体属性'
        public static final  String METHOD = "2";//'调用方法'
        public static final  String ENUM = "3";//'枚举|输入值'
    }
    /* *
     * 规则条件方法类型
     * @author ly
     * @modifyTime 2020/11/13 10:01:00
     */
    public static final   class ConditionMethodType{
        public static final  String ONLY_ONE = "0";// 单对象
        public static final  String MORE = "1";// 多对象
    }
    /* *
     * 工具栏方法类型
     * @author ly
     * @modifyTime 2020/11/13 10:01:00
     */
    public static final   class ClmUtilMethodType{
        public static final  String TYPE_0 = "0";// 方法页面不允许修改
        public static final  String TYPE_1 = "1";// 方法需页面动态传参
        public static final  String TYPE_2 = "2";// 多对象
        public static final  String TYPE_3 = "3";// 方法参数为多个对象，可动态传参
    }

    /* *
     * 版本发布状态
     * @author ly
     * @modifyTime 2020/11/13 10:02:00
     */
    public static final   class PublishStatus{
        public static final  String PUBLISHED = "1";//已发布
        public static final  String NOT_PUBLISH = "0";//未发布
        public static final  String UPDATEING = "2";//最新版本
    }
    
    /* *
     * 规则动作类型 
     * @author ly
     * @modifyTime 2020/11/13 14:15:00
     */
    public static final   class ActionType{
        public static final  String TYPE_1 = "1";//实现
        public static final  String METHOD = "4";//'方法'
        public static final  String ENUM = "3";//'枚举|输入值'
    }

    /* *
     * 属性对象类型
     * @author ly
     * @modifyTime 2020/11/16 14:47:00
     */
    public static final   class FieldType{
        public static final  String BASE = "1";//基本类型
        public static final  String OBJECT = "2";//单一对象
        public static final  String COLLECT = "3";//集合
    }

    /* *
     * 标识符
     * @author ly
     * @modifyTime 2020/11/20 13:55:00
     */
    public static final  class Sysbols{
        public static final String COMMA = ",";//逗号
        public static final String EQUALS = "=";//等号
    }

    /* *
     * 是否有效
     * @author ly
     * @modifyTime 2020/11/23 17:32:00
     */
    public static final  class IsEffect{
        public static final String YES = "1";//有效
        public static final String NO = "0";//无效
    }

    /* *
     * 节点类型
     * @author ly
     * @modifyTime 2020/11/23 17:32:00
     */
    public static final  class NodeType{
        public static final String NODE_ROOT = "1";//root节点
        public static final String NODE_RULE = "2";//执行规则节点
        public static final String NODE_CIRCLE = "3";//循环节点
        public static final String NODE_OTHER = "4";//其他节点
    }

    /* *
     * 结果是否通过
     * @author ly
     * @modifyTime 2020/11/23 17:32:00
     */
    public static final  class ResultStatus{
        public static final Integer PASS = 1;//通过
        public static final Integer REFUSE = 0;//未通过
    }
}
