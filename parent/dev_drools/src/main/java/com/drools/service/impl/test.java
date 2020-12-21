package com.drools.service.impl;

import com.alibaba.fastjson.JSON;
import com.drools.entity.clm.*;
import com.drools.model.fact.RuleExecutionObject;
import com.drools.model.fact.RuleExecutionResult;
import com.drools.service.ThirdApiService;
import com.drools.util.BASE64;
import com.drools.util.RSAEncrypt;
import com.drools.util.RSASignature;
import com.drools.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class test {
    private static final Logger log = LoggerFactory.getLogger(test.class);

    public static void main(String[] args) {
        try {
            // 生成密钥对
            KeyPair keyPair = RSAEncrypt.genKeyPair();
            String privateKey = new String(BASE64.encode(keyPair.getPrivate().getEncoded()));
            String publicKey = new String(BASE64.encode(keyPair.getPublic().getEncoded()));
            System.out.println("私钥:" + privateKey);
            System.out.println("公钥:" + publicKey);

            // RSA加密
            String data = "待加密的文字内容";

            // RSA签名
            String sign = RSASignature.sign(data, privateKey);
            System.out.println(sign);
            // RSA验签
            boolean result = RSASignature.signVerify(data, publicKey, sign);
            System.out.print("验签结果:" + result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("加解密异常");
        }


        try{
            byte[] info ="待签名信息".getBytes();

            //产生RSA密钥对(myKeyPair)
            KeyPairGenerator myKeyGen= KeyPairGenerator.getInstance("RSA");
            myKeyGen.initialize(1024);
            KeyPair myKeyPair = myKeyGen.generateKeyPair();
            System.out.println( "得到RSA密钥对    "+myKeyPair);

            //产生Signature对象,用私钥对信息(info)签名.
            Signature mySig = Signature.getInstance("SHA1WithRSA");  //用指定算法产生签名对象
            mySig.initSign(myKeyPair.getPrivate());  //用私钥初始化签名对象
            mySig.update(info);  //将待签名的数据传送给签名对象(须在初始化之后)
            byte[] sigResult = mySig.sign();  //返回签名结果字节数组
            System.out.println("签名后信息: "+ new String(sigResult) );

            //用公钥验证签名结果
            mySig.initVerify(myKeyPair.getPublic());  //使用公钥初始化签名对象,用于验证签名
            mySig.update(info); //更新签名内容
            boolean verify= mySig.verify(sigResult); //得到验证结果
            System.out.println( "签名验证结果: " +verify);
        }catch (Exception ex){ex.printStackTrace();}

    }

    public RuleExecutionObject testGroovy(Claim claim) throws Exception {

        log.info("建档接口invokeCreate:入参：", JSON.toJSONString(claim));

        RuleExecutionResult result = new RuleExecutionResult();
        RuleExecutionObject resultData = new RuleExecutionObject();
        ThirdApiService thirdApiService = SpringContextHolder.getBean("thirdApiServiceImpl");

        log.info("建档接口invokeCreate:开始插入执行规则[admin-audit-claim-create-01]对象");
        RuleExecutionObject node1 = new RuleExecutionObject();
        node1.setGlobal("_result", result);
        node1.addFactObject(claim);
        log.info("建档接口invokeCreate:开始执行规则[admin-audit-claim-create-01]");
        resultData = thirdApiService.excute(node1, "admin-audit-claim-create-01");
        log.info("建档接口invokeCreate:结束执行规则[admin-audit-claim-create-01]");

        log.info("建档接口invokeCreate:循环对象[Receiver]");
        List<Receiver> receiverList = new ArrayList<>();
        receiverList = claim.getReceivers();
        if (!CollectionUtils.isEmpty(receiverList)) {
            for (Receiver receiver : receiverList) {

                log.info("建档接口invokeCreate:开始插入执行规则[admin-audit-claim-create-02]对象");
                RuleExecutionObject node3 = new RuleExecutionObject();
                node3.setGlobal("_result", result);
                node3.addFactObject(claim);
                node3.addFactObject(receiver);
                log.info("建档接口invokeCreate:开始执行规则[admin-audit-claim-create-02]");
                resultData = thirdApiService.excute(node3, "admin-audit-claim-create-02");
                log.info("建档接口invokeCreate:结束执行规则[admin-audit-claim-create-02]");
            }
        }

        log.info("建档接口invokeCreate:返回：", JSON.toJSONString(resultData));
        return resultData;
    }
}
