/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : dev_drools

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2020-10-22 16:53:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for rule_action_info
-- ----------------------------
DROP TABLE IF EXISTS `rule_action_info`;
CREATE TABLE `rule_action_info` (
  `ACTION_ID` bigint(20) NOT NULL COMMENT '主键',
  `ACTION_TYPE` int(11) NOT NULL COMMENT '动作类型(1实现2自身)',
  `ACTION_NAME` varchar(200) NOT NULL COMMENT '动作名称',
  `ACTION_DESC` varchar(3000) DEFAULT NULL COMMENT '动作描述',
  `ACTION_CLASS` varchar(200) NOT NULL COMMENT '动作实现类(包路径)',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ACTION_ID`),
  KEY `ACTION_TYPE` (`ACTION_TYPE`),
  KEY `ACTION_NAME` (`ACTION_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则动作定义信息';

-- ----------------------------
-- Records of rule_action_info
-- ----------------------------
INSERT INTO `rule_action_info` VALUES ('1', '1', '测试实现类', '测试实现类', 'com.sky.bluesky.service.impl.action.TestActionImpl', '1', '1', '2017-07-24 17:12:32', '2');
INSERT INTO `rule_action_info` VALUES ('2', '2', '自身', '测试自身', 'com.sky.bluesky.model.fact.TestRule', '1', '1', '2017-07-27 10:07:03', '3');
INSERT INTO `rule_action_info` VALUES ('3', '3', '照会', '行政照会', 'com.sky.bluesky.model.fact.Claim', '1', '1', '2020-07-15 14:13:44', null);
INSERT INTO `rule_action_info` VALUES ('4', '3', '照会', '行政照会', 'com.sky.bluesky.model.fact.Claim', '1', '1', '2020-07-15 14:13:44', null);
INSERT INTO `rule_action_info` VALUES ('5', '3', '照会', '行政照会', 'com.sky.bluesky.model.fact.Claim', '1', '1', '2020-07-15 14:13:44', null);
INSERT INTO `rule_action_info` VALUES ('6', '3', '照会', '行政照会', 'com.sky.bluesky.model.fact.Claim', '1', '1', '2020-07-15 14:13:44', null);

-- ----------------------------
-- Table structure for rule_action_param_info
-- ----------------------------
DROP TABLE IF EXISTS `rule_action_param_info`;
CREATE TABLE `rule_action_param_info` (
  `ACTION_PARAM_ID` bigint(20) NOT NULL COMMENT '主键',
  `ACTION_ID` bigint(20) NOT NULL COMMENT '动作id',
  `ACTION_PARAM_NAME` varchar(200) NOT NULL COMMENT '参数名称',
  `ACTION_PARAM_DESC` varchar(3000) DEFAULT NULL COMMENT '参数描述',
  `PARAM_IDENTIFY` varchar(200) NOT NULL COMMENT '标识',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ACTION_PARAM_ID`),
  KEY `ACTION_ID` (`ACTION_ID`),
  KEY `ACTION_PARAM_NAME` (`ACTION_PARAM_NAME`),
  KEY `PARAM_IDENTIFY` (`PARAM_IDENTIFY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动作参数信息表';

-- ----------------------------
-- Records of rule_action_param_info
-- ----------------------------
INSERT INTO `rule_action_param_info` VALUES ('1', '1', '测试', '测试', 'message', '1', '1', '2017-07-24 18:24:28', null);
INSERT INTO `rule_action_param_info` VALUES ('2', '2', '测试', '测试', 'score', '1', '1', '2017-07-17 10:07:46', null);
INSERT INTO `rule_action_param_info` VALUES ('3', '1', 'ces', 'ces', 'amount', '1', '1', '2020-07-03 16:21:15', null);
INSERT INTO `rule_action_param_info` VALUES ('4', '3', '照会类型', '照会类型', 'addNote', '1', '1', '2020-07-15 14:16:15', null);
INSERT INTO `rule_action_param_info` VALUES ('5', '4', '照会码', '照会码', 'addNote', '1', '1', '2020-07-15 14:16:38', null);
INSERT INTO `rule_action_param_info` VALUES ('6', '5', '照会日期', '照会日期', 'addNote', '1', '1', '2020-07-15 14:17:12', null);
INSERT INTO `rule_action_param_info` VALUES ('7', '6', '照会类型', '照会类型', 'addNote', '1', '1', '2020-07-15 14:16:15', null);

-- ----------------------------
-- Table structure for rule_action_param_value_info
-- ----------------------------
DROP TABLE IF EXISTS `rule_action_param_value_info`;
CREATE TABLE `rule_action_param_value_info` (
  `ACTION_PARAM_VALUE_ID` bigint(20) NOT NULL COMMENT '主键',
  `RULE_ACTION_REL_ID` bigint(20) NOT NULL COMMENT '动作规则关系主键',
  `ACTION_PARAM_ID` bigint(20) NOT NULL COMMENT '动作参数',
  `PARAM_VALUE` varchar(200) NOT NULL COMMENT '参数值',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ACTION_PARAM_VALUE_ID`),
  KEY `RULE_ACTION_REL_ID` (`RULE_ACTION_REL_ID`),
  KEY `ACTION_PARAM_ID` (`ACTION_PARAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动作参数对应的属性值信息表';

-- ----------------------------
-- Records of rule_action_param_value_info
-- ----------------------------
INSERT INTO `rule_action_param_value_info` VALUES ('1', '1', '1', 'hello', '1', '1', '2017-07-24 19:29:17', null);
INSERT INTO `rule_action_param_value_info` VALUES ('2', '2', '2', '#3#*5', '1', '1', '2017-07-27 10:10:17', null);
INSERT INTO `rule_action_param_value_info` VALUES ('3', '3', '3', '#2#+100', '1', '1', '2020-07-03 16:22:33', null);
INSERT INTO `rule_action_param_value_info` VALUES ('4', '4', '4', '(\"SE001\",\"8\")', '1', '1', '2020-07-15 14:19:56', null);
INSERT INTO `rule_action_param_value_info` VALUES ('5', '5', '5', '(\"SE002\",\"8\")', '1', '1', '2020-07-15 14:20:34', null);
INSERT INTO `rule_action_param_value_info` VALUES ('6', '6', '6', '(\"SE003\",\"8\")', '1', '1', '2020-07-15 14:21:36', null);
INSERT INTO `rule_action_param_value_info` VALUES ('7', '7', '7', '(\"SE004\",\"8\")', '1', '1', '2020-07-15 14:19:56', null);

-- ----------------------------
-- Table structure for rule_action_rule_rel
-- ----------------------------
DROP TABLE IF EXISTS `rule_action_rule_rel`;
CREATE TABLE `rule_action_rule_rel` (
  `RULE_ACTION_REL_ID` bigint(20) NOT NULL COMMENT '主键',
  `ACTION_ID` bigint(20) NOT NULL COMMENT '动作',
  `RULE_ID` bigint(20) NOT NULL COMMENT '规则',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`RULE_ACTION_REL_ID`),
  KEY `ACTION_ID` (`ACTION_ID`),
  KEY `RULE_ID` (`RULE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动作与规则信息关系表';

-- ----------------------------
-- Records of rule_action_rule_rel
-- ----------------------------
INSERT INTO `rule_action_rule_rel` VALUES ('1', '1', '1', '1', '1', '2017-07-24 18:59:11', null);
INSERT INTO `rule_action_rule_rel` VALUES ('2', '2', '1', '1', '1', '2017-07-27 10:08:25', null);
INSERT INTO `rule_action_rule_rel` VALUES ('3', '1', '2', '1', '1', '2020-07-03 09:11:01', null);
INSERT INTO `rule_action_rule_rel` VALUES ('4', '3', '3', '1', '1', '2020-07-15 14:18:28', null);
INSERT INTO `rule_action_rule_rel` VALUES ('5', '4', '4', '1', '1', '2020-07-15 14:18:39', null);
INSERT INTO `rule_action_rule_rel` VALUES ('6', '5', '5', '1', '1', '2020-07-15 14:18:51', null);
INSERT INTO `rule_action_rule_rel` VALUES ('7', '6', '6', '1', '1', '2020-07-15 14:19:02', null);

-- ----------------------------
-- Table structure for rule_condition_info
-- ----------------------------
DROP TABLE IF EXISTS `rule_condition_info`;
CREATE TABLE `rule_condition_info` (
  `CONDITION_ID` bigint(20) NOT NULL COMMENT '主键',
  `RULE_ID` bigint(20) NOT NULL COMMENT '规则',
  `CONDITION_NAME` varchar(3000) NOT NULL COMMENT '条件名称',
  `CONDITION_EXPRESSION` varchar(3000) NOT NULL COMMENT '条件表达式',
  `CONDITION_DESC` varchar(3000) NOT NULL COMMENT '条件描述',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`CONDITION_ID`),
  KEY `RULE_ID` (`RULE_ID`),
  KEY `CONDITION_NAME` (`CONDITION_NAME`(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则条件信息表';

-- ----------------------------
-- Records of rule_condition_info
-- ----------------------------
INSERT INTO `rule_condition_info` VALUES ('1', '1', '测试', '$2$==100', '$金额$==100', '1', '1', '2017-07-24 20:04:52', null);
INSERT INTO `rule_condition_info` VALUES ('2', '1', '测试1', '$1$==lihao', '$msg$==lihao', '1', '1', '2020-07-03 16:17:51', null);
INSERT INTO `rule_condition_info` VALUES ('3', '2', 'ces', '$2$==100', '$金额$==100', '1', '1', '2020-07-03 16:32:00', null);
INSERT INTO `rule_condition_info` VALUES ('4', '3', '日期比较', '$7$<=$NOW$', '当天>离职日期', '1', '1', '2020-07-15 14:03:56', null);
INSERT INTO `rule_condition_info` VALUES ('5', '4', '理赔型态', '$8$!=N', '理赔型态!=N', '1', '1', '2020-07-15 14:07:39', null);
INSERT INTO `rule_condition_info` VALUES ('6', '5', '同一天多次手术', '$9$==true', 'eval(同一天多次手术==true)', '1', '1', '2020-07-15 14:11:38', null);
INSERT INTO `rule_condition_info` VALUES ('7', '6', '给付方式', '$10$!=5', '给付方式!=5', '1', '1', '2020-07-15 14:12:27', null);

-- ----------------------------
-- Table structure for rule_entity_info
-- ----------------------------
DROP TABLE IF EXISTS `rule_entity_info`;
CREATE TABLE `rule_entity_info` (
  `ENTITY_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ENTITY_NAME` varchar(50) NOT NULL COMMENT '名称',
  `ENTITY_DESC` varchar(3000) NOT NULL COMMENT '描述',
  `ENTITY_IDENTIFY` varchar(50) NOT NULL COMMENT '标识',
  `PKG_NAME` varchar(200) NOT NULL COMMENT '包路径',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效(1是0否)',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ENTITY_ID`),
  KEY `ENTITY_IDENTIFY` (`ENTITY_IDENTIFY`),
  KEY `ENTITY_NAME` (`ENTITY_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='规则引擎实体信息表';

-- ----------------------------
-- Records of rule_entity_info
-- ----------------------------
INSERT INTO `rule_entity_info` VALUES ('1', '测试规则', '测试规则引擎', 'testRule', 'com.sky.bluesky.model.fact.TestRule', '1', '2017-07-20 11:41:32', '1', null);
INSERT INTO `rule_entity_info` VALUES ('8', '行政规则', '行政规则', 'claim', 'com.sky.bluesky.model.fact.Claim', '1', '2020-07-15 13:45:10', '1', null);

-- ----------------------------
-- Table structure for rule_entity_item_info
-- ----------------------------
DROP TABLE IF EXISTS `rule_entity_item_info`;
CREATE TABLE `rule_entity_item_info` (
  `ITEM_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ENTITY_ID` bigint(20) NOT NULL COMMENT '实体ID',
  `ITEM_NAME` varchar(50) NOT NULL COMMENT '字段名称',
  `ITEM_IDENTIFY` varchar(50) NOT NULL COMMENT '字段标识',
  `ITEM_DESC` varchar(50) DEFAULT NULL COMMENT '属性描述',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `REMARK` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ITEM_ID`),
  KEY `ENTITY_ID` (`ENTITY_ID`),
  KEY `ITEM_IDENTIFY` (`ITEM_IDENTIFY`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='实体属性信息';

-- ----------------------------
-- Records of rule_entity_item_info
-- ----------------------------
INSERT INTO `rule_entity_item_info` VALUES ('1', '1', '消息', 'message', '消息信息', '1', '2017-07-20 16:20:56', '1', null);
INSERT INTO `rule_entity_item_info` VALUES ('2', '1', '金额', 'amount', '消费金额', '1', '2017-07-20 16:21:47', '1', null);
INSERT INTO `rule_entity_item_info` VALUES ('3', '1', '积分', 'score', '积分', '1', '2017-07-20 16:23:55', '1', null);
INSERT INTO `rule_entity_item_info` VALUES ('7', '8', '离职日期', 'agtToDate', '离职日期', '1', '2020-07-15 13:45:56', '1', null);
INSERT INTO `rule_entity_item_info` VALUES ('8', '8', '理赔型态', 'claimType', '理赔型态', '1', '2020-07-15 13:47:04', '1', null);
INSERT INTO `rule_entity_item_info` VALUES ('9', '8', '同一天多次手术', 'hasSameDaySurgery', '同一天多次手术', '1', '2020-07-15 13:47:57', '1', null);
INSERT INTO `rule_entity_item_info` VALUES ('10', '8', '给付方式', 'reqnType', '给付方式', '1', '2020-07-15 13:48:27', '1', null);

-- ----------------------------
-- Table structure for rule_info
-- ----------------------------
DROP TABLE IF EXISTS `rule_info`;
CREATE TABLE `rule_info` (
  `RULE_ID` bigint(20) NOT NULL COMMENT '主键',
  `SCENE_ID` bigint(20) NOT NULL COMMENT '场景',
  `RULE_NAME` varchar(50) NOT NULL COMMENT '名称',
  `RULE_DESC` varchar(3000) DEFAULT NULL COMMENT '描述',
  `RULE_ENABLED` int(11) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`RULE_ID`),
  KEY `SCENE_ID` (`SCENE_ID`),
  KEY `RULE_NAME` (`RULE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则信息';

-- ----------------------------
-- Records of rule_info
-- ----------------------------
INSERT INTO `rule_info` VALUES ('1', '1', '测试', null, '1', '1', '1', '2017-07-25 10:55:36', null);
INSERT INTO `rule_info` VALUES ('2', '1', '日期测试', null, '1', '1', '1', '2020-07-03 09:10:33', null);
INSERT INTO `rule_info` VALUES ('3', '3', '营销员不在职', null, '1', '1', '1', '2020-07-15 13:49:11', null);
INSERT INTO `rule_info` VALUES ('4', '3', '理赔型态非一般医疗', null, '1', '1', '1', '2020-07-15 13:49:38', null);
INSERT INTO `rule_info` VALUES ('5', '3', '同一天有多次手术', null, '1', '1', '1', '2020-07-15 13:50:17', null);
INSERT INTO `rule_info` VALUES ('6', '3', '转账支付不为自动转账', null, '1', '1', '1', '2020-07-15 13:50:32', null);

-- ----------------------------
-- Table structure for rule_property_info
-- ----------------------------
DROP TABLE IF EXISTS `rule_property_info`;
CREATE TABLE `rule_property_info` (
  `RULE_PROPERTY_ID` bigint(20) NOT NULL COMMENT '主键',
  `RULE_PROPERTY_IDENTIFY` varchar(200) NOT NULL COMMENT '标识',
  `RULE_PROPERTY_NAME` varchar(200) NOT NULL COMMENT '名称',
  `RULE_PROPERTY_DESC` varchar(3000) DEFAULT NULL COMMENT '描述',
  `DEFAULT_VALUE` varchar(200) DEFAULT NULL COMMENT '默认值',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  `CRE_USER_ID` bigint(20) DEFAULT NULL,
  `CRE_TIME` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`RULE_PROPERTY_ID`),
  KEY `RULE_PROPERTY_IDENTIFY` (`RULE_PROPERTY_IDENTIFY`),
  KEY `RULE_PROPERTY_NAME` (`RULE_PROPERTY_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则基础属性信息表';

-- ----------------------------
-- Records of rule_property_info
-- ----------------------------
INSERT INTO `rule_property_info` VALUES ('1', 'salience', '优先级', '用来设置规则执行的优先级，salience 属性的值是一个数字，数字越大执行优先级越高，同时它的值可以是一个负数。默认情况下，规则的ssalience 默认值为0，所以如果我们不手动设置规则的salience 属性，那么它的执行顺序是随机（但是一般都是按照加载顺序。）', '0', '1', null, null, null);
INSERT INTO `rule_property_info` VALUES ('2', 'no-loop', '是否可被重复执行', '是否允许规则多次执行, 值为布尔类型, 默认是false, 即当前的规则只要满足条件, 可以无限次执行; 对当前传入workingMemory中的Fact对象进行修改或者个数的增减时, 就会触发规则重新匹配执行; 设置属性no-loop true, 表示当前规则只执行一次, 即使RHS中更新了当前Fact对象也不会再次执行该规则了. 不过当其他规则里更新了Fact对象时, 即使有no-loop true也会触发, 即no-loop true仅表示本规的RHS中有更新时不重复执行', 'false', '1', null, null, null);
INSERT INTO `rule_property_info` VALUES ('3', 'date-effective', '生效时间', '设置规则的生效时间, 当前系统时间>=date-effective时才会触发执行, 值是一个日期格式的字符串, 推荐用法是手动在java代码中设置当前系统的时间格式, 然后按照格式指定时间. 比如: date-effective \"2016-01-31 23:59:59\"', null, '1', null, null, null);
INSERT INTO `rule_property_info` VALUES ('4', 'date-expires', '失效时间', '设置规则的失效时间, 跟生效时间正好相反', null, '1', null, null, null);
INSERT INTO `rule_property_info` VALUES ('5', 'enabled', '是否可用', '表示该规则是否可用, 值为布尔类型, 默认是true, 设置成false则该规则就不会被执行了', 'true', '0', '规则信息上已经有此属性，所以默认当前属性无效', null, null);
INSERT INTO `rule_property_info` VALUES ('6', 'dialect', '语言类型', '设置语言类型, 值为字符串, 一般有两种语言,mvel和java, 默认为java', 'java', '1', null, null, null);
INSERT INTO `rule_property_info` VALUES ('7', 'duration', '规则定时', '规则定时, 值为长整型, 单位为毫秒, 如 duration 3000, 表示规则在3秒后执行(另外的线程中执行)', '3000', '1', null, null, null);
INSERT INTO `rule_property_info` VALUES ('8', 'lock-on-active', '确认规则只执行一次', '是no-loop的增强版, 与其他属性配合使用;规则的重复执行不一定是本身触发的, 也可能是其他规则触发的, 当在规则上使用ruleflow-group属性或agenda-group属性时, 将lock-on-active属性值设置为true，可避免因某些Fact对象被修改而使已经执行过的规则再次被激活执行', 'false', '1', null, null, null);
INSERT INTO `rule_property_info` VALUES ('9', 'activation-group', '规则分组', '作用是将规则分组, 值为字符串表示组名,这样在执行的时候,具有相同activation-group属性的规则中只要有一个会被执行,其它的规则都将不再执行。即在具有相同activation-group属性的规则当中,只有一个规则会被执行,其它规则都将不会被执行.相同activation-group属性的规则中哪一个会先执行,则可以用salience之类的属性来实现', null, '1', null, null, null);
INSERT INTO `rule_property_info` VALUES ('10', 'agenda-group', '议程分组', 'Agenda Group 是用来在Agenda 的基础之上，对现在的规则进行再次分组，具体的分组方法可以采用为规则添加agenda-group 属性来实现。 \r\nagenda-group 属性的值也是一个字符串，通过这个字符串，可以将规则分为若干个Agenda Group，默认情况下，引擎在调用这些设置了agenda-group 属性的规则的时候需要显示的指定某个Agenda Group 得到Focus（焦点），这样位于该Agenda Group 当中的规则才会触发执行，否则将不执行', null, '1', null, null, null);
INSERT INTO `rule_property_info` VALUES ('11', 'ruleflow-group', '规则流分组', '在使用规则流的时候要用到ruleflow-group 属性，该属性的值为一个字符串，作用是用来将规则划分为一个个的组，然后在规则流当中通过使用ruleflow-group 属性的值，从而使用对应的规则', null, '1', null, null, null);
INSERT INTO `rule_property_info` VALUES ('12', 'auto-focus', '自动获取焦点', '前面我们也提到auto-focus 属性，它的作用是用来在已设置了agenda-group 的规则上设置该规则是否可以自动独取Focus，如果该属性设置为true，那么在引擎执行时，就不需要显示的为某个Agenda Group 设置Focus，否则需要', 'true', '1', null, null, null);

-- ----------------------------
-- Table structure for rule_property_rel
-- ----------------------------
DROP TABLE IF EXISTS `rule_property_rel`;
CREATE TABLE `rule_property_rel` (
  `RULE_PRO_REL_ID` bigint(20) NOT NULL COMMENT '主键',
  `RULE_ID` bigint(20) NOT NULL COMMENT '规则',
  `RULE_PROPERTY_ID` bigint(20) NOT NULL COMMENT '规则属性',
  `RULE_PROPERTY_VALUE` varchar(200) NOT NULL COMMENT '规则属性值',
  PRIMARY KEY (`RULE_PRO_REL_ID`),
  KEY `RULE_ID` (`RULE_ID`),
  KEY `RULE_PROPERTY_ID` (`RULE_PROPERTY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则属性配置表';

-- ----------------------------
-- Records of rule_property_rel
-- ----------------------------
INSERT INTO `rule_property_rel` VALUES ('1', '1', '1', '1');
INSERT INTO `rule_property_rel` VALUES ('2', '1', '2', 'true');
INSERT INTO `rule_property_rel` VALUES ('3', '1', '8', 'true');
INSERT INTO `rule_property_rel` VALUES ('4', '2', '1', '2');
INSERT INTO `rule_property_rel` VALUES ('5', '2', '2', 'true');
INSERT INTO `rule_property_rel` VALUES ('6', '2', '3', '2020-07-04 00:00:00');
INSERT INTO `rule_property_rel` VALUES ('7', '2', '4', '2020-12-12  23:59:59');
INSERT INTO `rule_property_rel` VALUES ('8', '1', '9', 'group1');
INSERT INTO `rule_property_rel` VALUES ('9', '2', '9', 'group1');
INSERT INTO `rule_property_rel` VALUES ('10', '3', '2', 'true');
INSERT INTO `rule_property_rel` VALUES ('11', '3', '1', '1');
INSERT INTO `rule_property_rel` VALUES ('12', '4', '2', 'true');
INSERT INTO `rule_property_rel` VALUES ('13', '4', '1', '2');
INSERT INTO `rule_property_rel` VALUES ('14', '5', '2', 'true');
INSERT INTO `rule_property_rel` VALUES ('15', '5', '1', '3');
INSERT INTO `rule_property_rel` VALUES ('16', '6', '2', 'true');
INSERT INTO `rule_property_rel` VALUES ('17', '6', '1', '4');
INSERT INTO `rule_property_rel` VALUES ('18', '3', '8', 'true');
INSERT INTO `rule_property_rel` VALUES ('19', '4', '8', 'true');
INSERT INTO `rule_property_rel` VALUES ('20', '5', '8', 'true');
INSERT INTO `rule_property_rel` VALUES ('21', '6', '8', 'true');

-- ----------------------------
-- Table structure for rule_scene_entity_rel
-- ----------------------------
DROP TABLE IF EXISTS `rule_scene_entity_rel`;
CREATE TABLE `rule_scene_entity_rel` (
  `SCENE_ENTITY_REL_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `SCENE_ID` bigint(20) DEFAULT NULL COMMENT '场景',
  `ENTITY_ID` bigint(20) DEFAULT NULL COMMENT '实体',
  PRIMARY KEY (`SCENE_ENTITY_REL_ID`),
  KEY `SCENE_ID` (`SCENE_ID`),
  KEY `ENTITY_ID` (`ENTITY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='场景实体关联表';

-- ----------------------------
-- Records of rule_scene_entity_rel
-- ----------------------------
INSERT INTO `rule_scene_entity_rel` VALUES ('1', '1', '1');
INSERT INTO `rule_scene_entity_rel` VALUES ('2', '2', '1');
INSERT INTO `rule_scene_entity_rel` VALUES ('3', '3', '8');

-- ----------------------------
-- Table structure for rule_scene_info
-- ----------------------------
DROP TABLE IF EXISTS `rule_scene_info`;
CREATE TABLE `rule_scene_info` (
  `SCENE_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `SCENE_IDENTIFY` varchar(50) NOT NULL COMMENT '标识',
  `SCENE_TYPE` int(11) DEFAULT NULL COMMENT '类型(暂不使用)',
  `SCENE_NAME` varchar(50) NOT NULL COMMENT '名称',
  `SCENE_DESC` varchar(3000) DEFAULT NULL COMMENT '描述',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`SCENE_ID`),
  KEY `SCENE_IDENTIFY` (`SCENE_IDENTIFY`),
  KEY `SCENE_TYPE` (`SCENE_TYPE`),
  KEY `SCENE_NAME` (`SCENE_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='规则引擎使用场景';

-- ----------------------------
-- Records of rule_scene_info
-- ----------------------------
INSERT INTO `rule_scene_info` VALUES ('1', 'test', null, '测试', '测试规则引擎', '1', '1', '2017-07-20 16:56:10', '测试');
INSERT INTO `rule_scene_info` VALUES ('2', 'rule1', null, '测试1', '测试规则1', '1', '1', '2020-07-03 09:08:36', '测试1');
INSERT INTO `rule_scene_info` VALUES ('3', 'audit', null, '行政规则I', '行政规则I', '1', '1', '2020-07-15 13:36:47', '行政规则I');

-- ----------------------------
-- Table structure for rule_variable
-- ----------------------------
DROP TABLE IF EXISTS `rule_variable`;
CREATE TABLE `rule_variable` (
  `VARIABLE_ID` bigint(20) NOT NULL COMMENT '主键',
  `VARIABLE_NAME` varchar(200) NOT NULL COMMENT '变量名称',
  `VARIABLE_TYPE` int(11) NOT NULL COMMENT '变量类型（1条件2动作）',
  `DEFAULT_VALUE` varchar(200) NOT NULL COMMENT '默认值',
  `VALUE_TYPE` int(11) NOT NULL COMMENT '数值类型（ 1字符型 2数字型 3 日期型）',
  `VARIABLE_VALUE` varchar(200) NOT NULL COMMENT '变量值',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`VARIABLE_ID`),
  KEY `VARIABLE_TYPE` (`VARIABLE_TYPE`),
  KEY `VALUE_TYPE` (`VALUE_TYPE`),
  KEY `VARIABLE_NAME` (`VARIABLE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则引擎常用变量';

-- ----------------------------
-- Records of rule_variable
-- ----------------------------
INSERT INTO `rule_variable` VALUES ('1', '增加积分100', '2', '100', '2', '100', '1', '1', '2017-07-20 18:38:01', null);
INSERT INTO `rule_variable` VALUES ('2', '金额大于100', '1', '100', '2', '100', '1', '1', '2017-07-20 18:39:18', null);
