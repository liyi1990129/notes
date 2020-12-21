package com.drools.service.impl;

import com.drools.biz.*;
import com.drools.common.DroolsConstants;
import com.drools.model.*;
import com.drools.service.CreateJavaServce;
import com.drools.util.FiledUtil;
import com.drools.util.JavaCodeFormattingUtil;
import com.drools.util.RuleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/* *
 * 动态生成java 代码
 * @author ly
 * @modifyTime 2020/11/25 13:11:00
 */
@Slf4j
@Service
public class CreateJavaServceImpl implements CreateJavaServce {
    private final String JAVACODE = "#JAVACODE#";

    @Autowired
    RuleInterfaceBiz ruleInterfaceBiz;
    @Autowired
    RuleInterfaceFlowBiz ruleInterfaceFlowBiz;
    @Autowired
    RuleSceneBiz ruleSceneBiz;
    @Autowired
    RuleEntityBiz ruleEntityBiz;
    @Autowired
    RuleEntityItemBiz ruleEntityItemBiz;

    /* *
     * 根据接口流程生成java代码
     * @author ly
     * @modifyTime 2020/11/25 13:07:00
     */
    @Override
    public String createJavaCode(Long interfaceId) {

        RuleInterface ruleInterface = ruleInterfaceBiz.findByID(interfaceId);

        //根据接口ID获取流程节点
        List<RuleInterfaceFlow> flowList = ruleInterfaceFlowBiz.findListByInterfaceId(interfaceId);

        //封装成 map
        Map<String, RuleInterfaceFlow> maps = flowList.stream().collect(Collectors.toMap(RuleInterfaceFlow::getFlowShapeId, Function.identity(), (key1, key2) -> key2));
        Map<String, String> result = new HashMap<>();

        String logPrefix = ruleInterface.getInterfaceName() + ruleInterface.getInterfaceIdentify() + ":";
        //获取java代码引用及共用部分
        StringBuffer java = getJavaTemplate(ruleInterface, logPrefix);

        //拼装各个节点的JAVA 代码
        int i = 0;
        while (i < flowList.size()) {
            StringBuffer sb = new StringBuffer("");
            RuleInterfaceFlow flow = flowList.get(i);

            if (DroolsConstants.NodeType.NODE_CIRCLE.equals(flow.getFlowNodeType())) {//for循环
                sb = getforStr(flow, maps, logPrefix);
            } else if (DroolsConstants.NodeType.NODE_RULE.equals(flow.getFlowNodeType())) {//调用规则
                sb = getexStr(flow, maps, logPrefix,ruleInterface);
            }

            result.put(flow.getFlowShapeId(), sb.toString());
            i++;
        }

        //整合各个节点的代码
        StringBuffer ruleCode = getResult(result, maps);
        String javaStr = java.toString();
        //替换
        String resultStr = javaStr.replace(JAVACODE, ruleCode.toString());
        String endStr = "#end#";
        if(resultStr.indexOf(endStr)>0){
            resultStr =  resultStr.replace(endStr,"");
        }
        //格式化JAVA代码
        resultStr = JavaCodeFormattingUtil.tryFormat(resultStr);
        log.info(resultStr);
        return resultStr;
    }

    /* *
     * 整合各个节点的代码
     * @param result
     * @param maps
     * @author ly
     * @modifyTime 2020/11/26 14:06:00
     */
    public StringBuffer getResult(Map<String, String> result, Map<String, RuleInterfaceFlow> maps) {
        RuleInterfaceFlow begin = maps.get("begin");
        String next = begin.getFlowNextNode();
        StringBuffer stringBuffer = new StringBuffer("");

        //获取开始的下一步
        RuleInterfaceFlow nextFlow = maps.get(next);
        String str = result.get(nextFlow.getFlowShapeId());
        stringBuffer.append(str);

        stringBuffer = getR(result, maps, nextFlow, stringBuffer);
        return stringBuffer;
    }

    /* *
     * 根据 流程节点ID 替换下一步内容
     * @author ly
     * @modifyTime 2020/11/25 13:11:00
     */
    public StringBuffer getR(Map<String, String> result, Map<String, RuleInterfaceFlow> maps, RuleInterfaceFlow flow, StringBuffer sb) {
        //获取下一步内容
        String str = result.get(flow.getFlowNextNode());

        String strb = sb.toString();
        String source = "#" + flow.getFlowNextNode() + "#";

        //替换代码内容
        if ((DroolsConstants.NodeType.NODE_CIRCLE.equals(flow.getFlowNodeType()) ||
                DroolsConstants.NodeType.NODE_RULE.equals(flow.getFlowNodeType()))
                && StringUtils.isNotBlank(str)
        ) {
            if (strb.indexOf(source) > 0) {
                strb = strb.replace(source, str);
                sb = new StringBuffer(strb);
            } else {
                sb.append(str);
            }
        }else{
            //节点内容为空
            if(StringUtils.isBlank(str) && strb.indexOf(source) > 0){
                strb = strb.replace(source, "");
                sb = new StringBuffer(strb);
            }
        }

        //如果有下一步，进行下一步内容的替换
        if (StringUtils.isNotBlank(flow.getFlowNextNode())) {
            RuleInterfaceFlow nextFlow = maps.get(flow.getFlowNextNode());
            sb = getR(result, maps, nextFlow, sb);
        }
        return sb;
    }


    /* *
     * 生成节点类型为执行规则的JAVA代码
     * @author ly
     * @modifyTime 2020/11/25 13:13:00
     */
    private StringBuffer getexStr(RuleInterfaceFlow flow, Map<String, RuleInterfaceFlow> maps, String logPrefix,RuleInterface ruleInterface) {
        StringBuffer sb = new StringBuffer();

        //获取场景标识
        String scene = flow.getSceneIdentify();
        // 根据场景标识获取实体
        RuleSceneInfo sceneInfo = new RuleSceneInfo();
        sceneInfo.setSceneIdentify(scene);
        List<RuleEntityInfo> entityInfos = ruleSceneBiz.findBaseRuleEntityListByScene(sceneInfo);
        //RuleExecutionObject node1 = new RuleExecutionObject();
        sb.append("\n");
        sb.append("log.info(\"" + logPrefix + "开始插入执行规则[").append(scene).append("]对象\");\n ");
        sb.append("RuleExecutionObject ").append(flow.getFlowShapeId()).append(" = new RuleExecutionObject();").append("\n");

        // node1.setGlobal("_result",result);
        sb.append(flow.getFlowShapeId()).append(".setGlobal(\"_result\", result);").append("\n");

        // node1.addFactObject(claim);
        for (RuleEntityInfo entityInfo : entityInfos) {
            sb.append(flow.getFlowShapeId()).append(".addFactObject(").append(FiledUtil.getActionClazzIdentify(entityInfo.getPkgName())).append(");").append("\n");
        }

        // resultData = thirdApiServcie.excute(node1,scene,interfaceIdentify);
        sb.append("log.info(\"" + logPrefix + "开始执行规则[").append(scene).append("]\");\n ");
        sb.append("resultData = thirdApiService.excute(")
                .append(flow.getFlowShapeId()).append(",")
                .append("\"").append(scene).append("\"").append(",")
                .append("\"").append(ruleInterface.getInterfaceIdentify()).append("\"").append(",")
                .append("batchCode").append(",")
                .append("clmnum")
                .append(");").append("\n");
        sb.append("log.info(\"" + logPrefix + "结束执行规则[").append(scene).append("]\");\n ");

        String next = flow.getFlowNextNode();

        //如果没有终止循环 下一步内容
        if (StringUtils.isNotBlank(next) && StringUtils.isBlank(flow.getEndCircle())) {
            RuleInterfaceFlow nextFlow = maps.get(next);
            if (DroolsConstants.NodeType.NODE_RULE.equals(nextFlow.getFlowNodeType()) ||
                    DroolsConstants.NodeType.NODE_CIRCLE.equals(nextFlow.getFlowNodeType())) {
                sb.append("#").append(nextFlow.getFlowShapeId()).append("#").append("\n");
            }
        }
        return sb;
    }

    /* *
     * 生成节点类型为循环的JAVA代码
     * @author ly
     * @modifyTime 2020/11/25 13:13:00
     */
    private StringBuffer getforStr(RuleInterfaceFlow flow, Map<String, RuleInterfaceFlow> maps, String logPrefix) {
        String next = flow.getFlowNextNode();

        //获取要循环的实体
        RuleEntityInfo entityInfo = ruleEntityBiz.getEntityInfoById(flow.getEntityId());
        RuleEntityItemInfo itemInfo = ruleEntityItemBiz.findBaseRuleEntityItemInfoById(flow.getItemId());

        String pkg = itemInfo.getItemCls();
        String identity = itemInfo.getItemIdentify();
        StringBuffer sb = new StringBuffer();
        String listName = FiledUtil.getActionClazzIdentify(pkg) + "List";//policyList

        //初始化LIST List<Policy> policyList = new ArrayList();
        sb.append("\n");
        sb.append("log.info(\"" + logPrefix + "循环对象[").append(FiledUtil.getEntityClazz(pkg)).append("]\");\n ");
        sb.append("List<").append(FiledUtil.getEntityClazz(pkg)).append(">");
        sb.append(" ").append(listName).append(" = ").append("new ArrayList<>()").append(";\n");

        // policyList = claim.getPolicys();
        sb.append(listName).append(" = ").append(FiledUtil.getActionClazzIdentify(entityInfo.getPkgName()))
                .append(".")
                .append(RuleUtils.getMethodByProperty(identity)).append(";\n");


        //list 非空判断 if(!CollectionUtils.isEmpty(policyList))
        sb.append("if(!CollectionUtils.isEmpty(").append(listName).append(")){").append("\n");

        // for 循环 for(Policy policy: policyList){}
        sb.append("  ").append("for(").append(FiledUtil.getEntityClazz(pkg)).append(" ").append(FiledUtil.getActionClazzIdentify(pkg))
                .append(":").append(listName).append("){").append("\n");


        //下一步的内容
        RuleInterfaceFlow nextFlow = null;
        if (StringUtils.isNotBlank(next)) {
            nextFlow = maps.get(next);
            if (DroolsConstants.NodeType.NODE_RULE.equals(nextFlow.getFlowNodeType()) ||
                    DroolsConstants.NodeType.NODE_CIRCLE.equals(nextFlow.getFlowNodeType())) {
                sb.append("#").append(nextFlow.getFlowShapeId()).append("#").append("\n");
            }
        }

        sb.append("  }").append("\n");
        sb.append("}").append("\n");

        // 如果下一步的下一步终止循环在放在循环外
        if (nextFlow != null) {
            if (StringUtils.isNotBlank(nextFlow.getFlowNextNode()) && StringUtils.isNotBlank(nextFlow.getEndCircle())) {
                nextFlow = maps.get(nextFlow.getFlowNextNode());
                if (DroolsConstants.NodeType.NODE_RULE.equals(nextFlow.getFlowNodeType()) ||
                        DroolsConstants.NodeType.NODE_CIRCLE.equals(nextFlow.getFlowNodeType())) {
                    sb.append("#").append(nextFlow.getFlowShapeId()).append("#").append("\n");
                }
            }
        }

        //循环结束的下一步
        for (String key : maps.keySet()) {
            RuleInterfaceFlow flow1 = maps.get(key);
            if (StringUtils.isNotBlank(flow1.getEndCircle()) && flow1.getEndCircle().equals(flow.getFlowShapeId())) {
                String str = "#"+flow1.getFlowNextNode()+"#";
                if(sb.toString().indexOf(str)>0){

                }else{
                    sb.append(str).append("\n");
                }
            }
        }


        return sb;
    }

    /* *
     * 根据接口生成class类
     * @author ly
     * @modifyTime 2020/11/25 13:13:00
     */
    public StringBuffer getJavaTemplate(RuleInterface ruleInterface, String logPrefix) {
        String s = "package com.drools.service.impl;\n" +
                "\n" +
                "import com.alibaba.fastjson.JSON;\n" +
                "import com.drools.common.DroolsConstants;\n" +
                "import com.drools.entity.children.*;\n" +
                "import com.drools.entity.clm.*;\n" +
                "import com.drools.model.fact.RuleExecutionObject;\n" +
                "import com.drools.model.fact.RuleExecutionResult;\n" +
                "import com.drools.service.ThirdApiService;\n" +
//                "import com.drools.biz.sys.SysSequenceBiz;\n" +
                "import com.drools.util.SpringContextHolder;\n" +
                "import groovy.lang.GroovyClassLoader;\n" +
                "import org.slf4j.Logger;\n" +
                "import org.slf4j.LoggerFactory;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.List;\n" +
                "import org.springframework.util.CollectionUtils;\n" +
                "\n" +
                "import java.lang.reflect.Method;\n" +
                "\n";
        StringBuffer sb = new StringBuffer(s);
        sb.append("public class ").append("Test").append(ruleInterface.getInterfaceIdentify()).append("{\n");
        sb.append("private final static Logger log = LoggerFactory.getLogger(").append("Test").append(ruleInterface.getInterfaceIdentify()).append(".class);\n");
//        "private final static Logger log = LoggerFactory.getLogger(TestServiceImpl.class);\n" +
        String s1 = "    public RuleExecutionObject testGroovy(Claim claim,String clmnum, String batchCode) throws Exception {\n" +
                "\n" +
                "        log.info(\"" + logPrefix + "入参：\",JSON.toJSONString(claim));\n" +
//                "        String clmnum = claim.getClmnum();\n" +
//                "        SysSequenceBiz sysSequenceBiz =  SpringContextHolder.getBean(\"sysSequenceBiz\");\n"+
//                "        String code = sysSequenceBiz.gainNoByType(DroolsConstants.SeqName.BATCH_CODE);\n" +
//                "        String batchCode = clmnum+code;\n" +
                "\n" +
                "        RuleExecutionResult result = new RuleExecutionResult();\n" +
                "        RuleExecutionObject resultData = new RuleExecutionObject();\n" +
                "        ThirdApiService thirdApiService =  SpringContextHolder.getBean(\"thirdApiServiceImpl\");\n"
                + JAVACODE +
                "        log.info(\"" + logPrefix + "返回：\",JSON.toJSONString(resultData));\n" +
                "       return resultData;" +
                "    }\n" +
                "}";
        sb.append(s1);
        return sb;
    }
}
