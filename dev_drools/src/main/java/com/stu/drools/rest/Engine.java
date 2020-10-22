package com.stu.drools.rest;

import com.stu.drools.model.RuleSceneInfo;
import com.stu.drools.model.fact.RuleExecutionObject;
import com.stu.drools.util.DroolsUtil;
import org.kie.api.runtime.KieSession;

import java.util.List;
import java.util.Map;

public class Engine {
    public static RuleExecutionObject excute(String droolRuleStr, RuleExecutionObject ruleExecutionObject, final String scene) throws Exception {
        //KieSession对象
        KieSession session;
        try {
            //编译规则脚本,返回KieSession对象
            session = DroolsUtil.getInstance().getDrlSession(droolRuleStr.toString(), scene);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Drools初始化失败，请检查Drools语句！");
        }
        //执行规则
        return executeRuleEngine(session, ruleExecutionObject, scene);
    }

    public static RuleExecutionObject executeRuleEngine(KieSession session, RuleExecutionObject ruleExecutionObject, final String scene) throws Exception {
        try {
            // 1.插入全局对象
            Map<String, Object> globalMap = ruleExecutionObject.getGlobalMap();
            for (Object glb : globalMap.entrySet()) {
                Map.Entry global = (Map.Entry) glb;
                String key = (String) global.getKey();
                Object value = global.getValue();
                System.out.println("插入Global对象:" + value.getClass().getName());
                session.setGlobal(key, value);
            }
            // 2.插入fact对象
            // 2.1插入业务fact对象
            List<Object> factObjectList = ruleExecutionObject.getFactObjectList();
            for (Object o : factObjectList) {
                System.out.println("插入Fact对象：" + o.getClass().getName());
                session.insert(o);
            }
            // 2.2将 ruleExecutionObject 也插入到规则中，回调使用
            session.insert(ruleExecutionObject);
            // 3.将规则涉及的所有动作实现类插入到规则中(如果没有，则不处理)
            RuleSceneInfo sceneInfo = new RuleSceneInfo();
            sceneInfo.setSceneIdentify(scene);

//            session.startProcess("test");
//            session.getAgenda().getAgendaGroup("group1").setFocus();
//            ActivationGroup ag = session.getAgenda().getActivationGroup("test");
            session.fireAllRules();
            //执行规则
            //1,是否全部执行
//            if (ruleExecutionObject.isExecuteAll()) {
//                session.fireAllRules();
//            } else {
//                //2,执行以 ruleExecutionObject里规则名开头的规则
//                session.fireAllRules(new RuleNameStartsWithAgendaFilter(ruleExecutionObject.getRuleName()));
//            }

            return ruleExecutionObject;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            //释放资源
            session.dispose();
        }
    }
}

