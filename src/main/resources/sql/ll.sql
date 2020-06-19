/*
Navicat MySQL Data Transfer

Source Server         : localhostmysql
Source Server Version : 50647
Source Host           : localhost:3306
Source Database       : ll

Target Server Type    : MYSQL
Target Server Version : 50647
File Encoding         : 65001

Date: 2020-06-19 08:51:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for login
-- ----------------------------
DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `ID` varchar(32) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `USER_NAME` varchar(255) DEFAULT NULL,
  `PASS_WORD` varchar(255) DEFAULT NULL,
  `LOGIN_IP` varchar(255) DEFAULT NULL,
  `STARTS` int(11) DEFAULT NULL,
  `CREATOR` varchar(255) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `MODIFIER` varchar(255) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `LOGIN_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `LOGOUT_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `CNT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of login
-- ----------------------------
INSERT INTO `login` VALUES ('9C1E37A2366E3441B1947F6364541112', '陈永斌', 'chenyb', '5b06db4f98ec906a859ecdccfc069d88', '127.0.0.1', '200', null, '2020-06-18 15:55:44', null, '2020-06-18 15:55:44', '2020-06-18 16:55:50', '2020-06-18 15:55:44', '1');
INSERT INTO `login` VALUES ('9C1E37A2366E3441B1947F6364541212', 'TEST', 'test', '5b06db4f98ec906a859ecdccfc069d88', '127.0.0.1', '200', null, '2020-06-18 15:55:46', null, '2020-06-18 15:55:46', '2020-06-18 16:03:55', '2020-06-18 15:55:46', null);

-- ----------------------------
-- Table structure for login_roles
-- ----------------------------
DROP TABLE IF EXISTS `login_roles`;
CREATE TABLE `login_roles` (
  `LOGIN_ID` varchar(32) NOT NULL,
  `ROLES_ID` varchar(32) NOT NULL,
  PRIMARY KEY (`LOGIN_ID`,`ROLES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of login_roles
-- ----------------------------
INSERT INTO `login_roles` VALUES ('9C1E37A2366E3441B1947F6364541112', '9C1E37A2366E3441B1947F6364541117');
INSERT INTO `login_roles` VALUES ('9C1E37A2366E3441B1947F6364541212', '1C1E37A2366E3441B1947F6364541212');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `ID` varchar(32) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1C1E37A2366E3441B1947F6364541212', 'ppx');
INSERT INTO `role` VALUES ('9C1E37A2366E3441B1947F6364541117', 'admin');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `ID` varchar(32) NOT NULL,
  `PHONE` varchar(11) DEFAULT NULL,
  `IDENTITY` varchar(255) DEFAULT NULL,
  `ADDRESS1` int(11) DEFAULT NULL,
  `ADDRESS2` int(11) DEFAULT NULL,
  `ADDRESS3` int(11) DEFAULT NULL,
  `ADDRESS4` int(11) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `WECHAT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
