//package com.drools.rest;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.drools.common.ObjectRestResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//@Slf4j
//@RestController
//@RequestMapping(value = "/interface/api")
//public class IntefaceController {
//
//    @RequestMapping("save")
//    public ObjectRestResponse save(String json) {
//
//        ObjectRestResponse res = new ObjectRestResponse();
//        JSONObject jsonObject = JSON.parseObject(json);
//        Object cells = jsonObject.get("cells");
//
//        return res;
//    }
//
//    public static void main(String[] args) {
//        String json = "{\"cells\":[{\"position\":{\"x\":380,\"y\":80},\"size\":{\"width\":80,\"height\":42},\"attrs\":{\"text\":{\"text\":\"起始节点\"},\"body\":{\"rx\":24,\"ry\":24}},\"shape\":\"flow-chart-rect\",\"ports\":{\"groups\":{\"top\":{\"position\":\"top\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}},\"right\":{\"position\":\"right\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}},\"bottom\":{\"position\":\"bottom\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}},\"left\":{\"position\":\"left\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}}},\"items\":[{\"group\":\"top\",\"id\":\"0eba4cf3-c91a-4d05-8d27-cdf599a7095c\"},{\"group\":\"right\",\"id\":\"a5fe272e-7ae1-41cf-ad3c-50f59c889ad6\"},{\"group\":\"bottom\",\"id\":\"12e835bd-9b4b-41da-9eee-0c2534aa4c14\"},{\"group\":\"left\",\"id\":\"83ac8543-0160-4354-8cd1-581b48c96377\"}]},\"id\":\"fa3084dc-0463-4a5a-8503-b8182b5538dc\",\"zIndex\":1},{\"position\":{\"x\":380,\"y\":608},\"size\":{\"width\":80,\"height\":42},\"attrs\":{\"text\":{\"text\":\"结束节点\"},\"body\":{\"rx\":24,\"ry\":24}},\"shape\":\"flow-chart-rect\",\"ports\":{\"groups\":{\"top\":{\"position\":\"top\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}},\"right\":{\"position\":\"right\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}},\"bottom\":{\"position\":\"bottom\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}},\"left\":{\"position\":\"left\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}}},\"items\":[{\"group\":\"top\",\"id\":\"d3ed3e51-d8a0-4480-9859-cba3c6175362\"},{\"group\":\"right\",\"id\":\"e574d5c6-bd8a-41d4-ae09-0a4eb525e1ee\"},{\"group\":\"bottom\",\"id\":\"adc80e3c-1f9e-4a0b-b398-870a8bf474bf\"},{\"group\":\"left\",\"id\":\"076b73fa-192b-40e6-9c76-d1e47bbfae25\"}]},\"id\":\"c3af1391-2875-44a9-a98d-82aa48c8470a\",\"zIndex\":2},{\"position\":{\"x\":380,\"y\":186},\"size\":{\"width\":80,\"height\":42},\"attrs\":{\"text\":{\"text\":\"流程节点\"}},\"shape\":\"flow-chart-rect\",\"ports\":{\"groups\":{\"top\":{\"position\":\"top\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}},\"right\":{\"position\":\"right\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}},\"bottom\":{\"position\":\"bottom\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}},\"left\":{\"position\":\"left\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}}},\"items\":[{\"group\":\"top\",\"id\":\"f4a12414-7e05-4596-8979-7976e118d4a6\"},{\"group\":\"right\",\"id\":\"647c432a-7c33-4ed0-9a1f-f3eb5d7a29b0\"},{\"group\":\"bottom\",\"id\":\"dddfdb88-1787-4c4c-8c39-d8323d748ba4\"},{\"group\":\"left\",\"id\":\"46c21946-3e1b-4570-bf93-f633ba642172\"}]},\"id\":\"d72519f2-66b7-4e58-9f17-98733cd70a29\",\"zIndex\":3},{\"position\":{\"x\":380,\"y\":308},\"size\":{\"width\":80,\"height\":42},\"attrs\":{\"text\":{\"text\":\"循环节点\"}},\"shape\":\"flow-chart-rect\",\"ports\":{\"groups\":{\"top\":{\"position\":\"top\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}},\"right\":{\"position\":\"right\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}},\"bottom\":{\"position\":\"bottom\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}},\"left\":{\"position\":\"left\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}}},\"items\":[{\"group\":\"top\",\"id\":\"e27f7b0d-43f3-463e-9d78-13f0ce6bf7e0\"},{\"group\":\"right\",\"id\":\"174a600d-557c-4413-8c2a-821e7a0f87af\"},{\"group\":\"bottom\",\"id\":\"7a1183df-cd2e-4f2e-9e5d-c478a7aa8fe5\"},{\"group\":\"left\",\"id\":\"a219b358-3396-4b49-a89f-c41813485e94\"}]},\"id\":\"3810db62-419b-4b0a-a5fa-d95479165a3c\",\"zIndex\":4},{\"position\":{\"x\":380,\"y\":518},\"size\":{\"width\":80,\"height\":42},\"attrs\":{\"text\":{\"text\":\"流程节点\"}},\"shape\":\"flow-chart-rect\",\"ports\":{\"groups\":{\"top\":{\"position\":\"top\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}},\"right\":{\"position\":\"right\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}},\"bottom\":{\"position\":\"bottom\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}},\"left\":{\"position\":\"left\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}}},\"items\":[{\"group\":\"top\",\"id\":\"f4a12414-7e05-4596-8979-7976e118d4a6\"},{\"group\":\"right\",\"id\":\"647c432a-7c33-4ed0-9a1f-f3eb5d7a29b0\"},{\"group\":\"bottom\",\"id\":\"dddfdb88-1787-4c4c-8c39-d8323d748ba4\"},{\"group\":\"left\",\"id\":\"46c21946-3e1b-4570-bf93-f633ba642172\"}]},\"id\":\"30a54084-1968-48cb-936f-f53ecee7615b\",\"zIndex\":5},{\"position\":{\"x\":380,\"y\":399},\"size\":{\"width\":80,\"height\":42},\"attrs\":{\"text\":{\"text\":\"循环节点\"}},\"shape\":\"flow-chart-rect\",\"ports\":{\"groups\":{\"top\":{\"position\":\"top\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}},\"right\":{\"position\":\"right\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}},\"bottom\":{\"position\":\"bottom\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}},\"left\":{\"position\":\"left\",\"attrs\":{\"circle\":{\"r\":3,\"magnet\":true,\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"fill\":\"#fff\",\"style\":{\"visibility\":\"hidden\"}}}}},\"items\":[{\"group\":\"top\",\"id\":\"e27f7b0d-43f3-463e-9d78-13f0ce6bf7e0\"},{\"group\":\"right\",\"id\":\"174a600d-557c-4413-8c2a-821e7a0f87af\"},{\"group\":\"bottom\",\"id\":\"7a1183df-cd2e-4f2e-9e5d-c478a7aa8fe5\"},{\"group\":\"left\",\"id\":\"a219b358-3396-4b49-a89f-c41813485e94\"}]},\"id\":\"dca6c789-e077-46c0-9c5c-2f5c0e81a418\",\"zIndex\":6},{\"shape\":\"edge\",\"attrs\":{\"line\":{\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"targetMarker\":{\"name\":\"classic\",\"size\":8}}},\"id\":\"1b45d3aa-f2f1-45a3-8c3d-78c7070c1c1e\",\"router\":{\"name\":\"manhattan\"},\"source\":{\"cell\":\"fa3084dc-0463-4a5a-8503-b8182b5538dc\",\"port\":\"12e835bd-9b4b-41da-9eee-0c2534aa4c14\"},\"target\":{\"cell\":\"d72519f2-66b7-4e58-9f17-98733cd70a29\",\"port\":\"f4a12414-7e05-4596-8979-7976e118d4a6\"},\"zIndex\":7},{\"shape\":\"edge\",\"attrs\":{\"line\":{\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"targetMarker\":{\"name\":\"classic\",\"size\":8}}},\"id\":\"284f84d9-2784-465c-9045-945925dd3d2a\",\"router\":{\"name\":\"manhattan\"},\"source\":{\"cell\":\"d72519f2-66b7-4e58-9f17-98733cd70a29\",\"port\":\"dddfdb88-1787-4c4c-8c39-d8323d748ba4\"},\"target\":{\"cell\":\"3810db62-419b-4b0a-a5fa-d95479165a3c\",\"port\":\"e27f7b0d-43f3-463e-9d78-13f0ce6bf7e0\"},\"zIndex\":8},{\"shape\":\"edge\",\"attrs\":{\"line\":{\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"targetMarker\":{\"name\":\"classic\",\"size\":8}}},\"id\":\"faccd6e2-27a1-4fbe-9e1d-4b37a7859b83\",\"router\":{\"name\":\"manhattan\"},\"source\":{\"cell\":\"3810db62-419b-4b0a-a5fa-d95479165a3c\",\"port\":\"7a1183df-cd2e-4f2e-9e5d-c478a7aa8fe5\"},\"target\":{\"cell\":\"dca6c789-e077-46c0-9c5c-2f5c0e81a418\",\"port\":\"e27f7b0d-43f3-463e-9d78-13f0ce6bf7e0\"},\"zIndex\":9},{\"shape\":\"edge\",\"attrs\":{\"line\":{\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"targetMarker\":{\"name\":\"classic\",\"size\":8}}},\"id\":\"96359812-faf7-4edc-b069-9e3dba66951a\",\"router\":{\"name\":\"manhattan\"},\"source\":{\"cell\":\"dca6c789-e077-46c0-9c5c-2f5c0e81a418\",\"port\":\"7a1183df-cd2e-4f2e-9e5d-c478a7aa8fe5\"},\"target\":{\"cell\":\"30a54084-1968-48cb-936f-f53ecee7615b\",\"port\":\"f4a12414-7e05-4596-8979-7976e118d4a6\"},\"zIndex\":10},{\"shape\":\"edge\",\"attrs\":{\"line\":{\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"targetMarker\":{\"name\":\"classic\",\"size\":8}}},\"id\":\"17a7b149-9339-4ece-9513-d423294f0cfc\",\"router\":{\"name\":\"manhattan\"},\"source\":{\"cell\":\"30a54084-1968-48cb-936f-f53ecee7615b\",\"port\":\"dddfdb88-1787-4c4c-8c39-d8323d748ba4\"},\"target\":{\"cell\":\"c3af1391-2875-44a9-a98d-82aa48c8470a\",\"port\":\"d3ed3e51-d8a0-4480-9859-cba3c6175362\"},\"zIndex\":11},{\"shape\":\"edge\",\"attrs\":{\"line\":{\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"targetMarker\":{\"name\":\"classic\",\"size\":8}}},\"id\":\"5ed4ebd7-1020-402f-8de8-24598d45e1e6\",\"router\":{\"name\":\"manhattan\"},\"source\":{\"cell\":\"30a54084-1968-48cb-936f-f53ecee7615b\",\"port\":\"46c21946-3e1b-4570-bf93-f633ba642172\"},\"target\":{\"cell\":\"dca6c789-e077-46c0-9c5c-2f5c0e81a418\",\"port\":\"a219b358-3396-4b49-a89f-c41813485e94\"},\"zIndex\":12},{\"shape\":\"edge\",\"attrs\":{\"line\":{\"stroke\":\"#5F95FF\",\"strokeWidth\":1,\"targetMarker\":{\"name\":\"classic\",\"size\":8}}},\"id\":\"3cc799ea-0331-48cd-9018-4a2a18befb5d\",\"router\":{\"name\":\"manhattan\"},\"source\":{\"cell\":\"30a54084-1968-48cb-936f-f53ecee7615b\",\"port\":\"46c21946-3e1b-4570-bf93-f633ba642172\"},\"target\":{\"cell\":\"3810db62-419b-4b0a-a5fa-d95479165a3c\",\"port\":\"a219b358-3396-4b49-a89f-c41813485e94\"},\"zIndex\":13}]}";
//        JSONObject jsonObject = JSON.parseObject(json);
//        Object cells = jsonObject.get("cells");
//        JSONArray objects = (JSONArray) cells;
//        Map<String, JSONObject> nodeMap = new HashMap<>();
//        Map<String, List<String>> edgeMap = new HashMap<>();
//        String startId = "";
//        String endId = "";
//        for (Object object : objects) {
//            JSONObject item = (JSONObject) object;
//            if (item.get("shape").equals("flow-chart-rect")) {
//                String id = item.getString("id");
//                nodeMap.put(id, item);
//                JSONObject attrs = (JSONObject) item.get("attrs");
//                JSONObject text = (JSONObject) attrs.get("text");
//                if (text.get("text").equals("起始节点")) {
//                    startId = id;
//                }
//                if (text.get("text").equals("结束节点")) {
//                    endId = id;
//                }
//            }
//            if (item.get("shape").equals("edge")) {
//                JSONObject source = (JSONObject) item.get("source");
//                String sourceCell = source.getString("cell");
//                JSONObject target = (JSONObject) item.get("target");
//                String targetCell = target.getString("cell");
//                if (null != edgeMap.get(sourceCell)) {
//                    edgeMap.get(sourceCell).add(targetCell);
//                } else {
//                    List<String> jsonObjectList = new ArrayList<>();
//                    jsonObjectList.add(targetCell);
//                    edgeMap.put(sourceCell, jsonObjectList);
//                }
//            }
//        }
//        Map<String,Boolean> forNodMap = new HashMap<>();
//        List<String> targetIds = edgeMap.get(startId);
//        StringBuffer script = new StringBuffer("开始节点--->");
//        createScript(nodeMap,edgeMap,targetIds,script,endId,forNodMap);
//        script.append("结束节点");
//        System.out.printf(script.toString());
//    }
//
//    public static StringBuffer createScript(Map<String, JSONObject> nodeMap,Map<String, List<String>> edgeMap,List<String> targetIds, StringBuffer buffer,String endId,Map<String,Boolean> forNodMap) {
//        if (CollectionUtils.isEmpty(targetIds)){
//            return buffer;
//        }
//        for (String id : targetIds){
//            if (id.equals(endId)){
//                continue;
//            }
//            //获取节点
//            JSONObject object = nodeMap.get(id);
//            //生成节点脚本
//            JSONObject attrs = (JSONObject) object.get("attrs");
//            JSONObject text = (JSONObject) attrs.get("text");
//
//            if (text.getString("text").equals("循环节点")){
//               if (null != forNodMap.get(id)){
//                   //循环结束
//                   buffer.append("循环节点结束" + "--->");
//               }else {
//                   forNodMap.put(id,true);
//                   buffer.append(text.getString("text") + "--->");
//                   //获取节点下一个节点
//                   createScript(nodeMap,edgeMap,edgeMap.get(object.getString("id")),buffer,endId,forNodMap);
//               }
//            }else {
//                buffer.append(text.getString("text") + "--->");
//                //获取节点下一个节点
//                createScript(nodeMap,edgeMap,edgeMap.get(object.getString("id")),buffer,endId,forNodMap);
//            }
//
//        }
//        return buffer;
//    }
//}
