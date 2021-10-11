/*
 Navicat Premium Data Transfer

 Source Server         : 本地连接
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : bigdata_filesystem

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 29/05/2021 20:34:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for action_log
-- ----------------------------
DROP TABLE IF EXISTS `action_log`;
CREATE TABLE `action_log`  (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `log_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录名',
  `log_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录类型',
  `ipaddr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问地址',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问链接',
  `os` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `clazz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产生日志的类',
  `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产生日志的方法',
  `message` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志信息',
  `create_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '日志记录时间',
  `status` int(1) NULL DEFAULT NULL COMMENT '日志状态：1正常、3删除',
  `line_num` int(11) NULL DEFAULT NULL COMMENT '错误行号',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 357 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `auto`;
CREATE TABLE `auto`
(
   `AUTO_ID` bigint(20) NOT NULL AUTO_INCREMENT,
   `TABLE_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
   `CONTENT` blob NULL COMMENT '表格内容',
   `CREATE_TIME` timestamp(0) NULL DEFAULT NULL,
   `CREATE_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
   `UPDATE_TIME` timestamp(0) NULL DEFAULT NULL,
   `UPDATE_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
   `COMPLETE_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`AUTO_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;







-- ----------------------------
-- Table structure for catalog
-- ----------------------------
DROP TABLE IF EXISTS `catalog`;
CREATE TABLE `catalog`  (
  `CATALOG_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CATALOG_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PARENT_CATALOG_ID` bigint(20) NULL DEFAULT NULL,
  `STATUS` int(11) NULL DEFAULT NULL,
  `CREATE_TIME` timestamp(0) NULL DEFAULT NULL,
  `CREATE_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UPDATE_TIME` timestamp(0) NULL DEFAULT NULL,
  `UPDATE_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `COMPLETE_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`CATALOG_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for catalog_right
-- ----------------------------
DROP TABLE IF EXISTS `catalog_right`;
CREATE TABLE `catalog_right`  (
  `USER_ID` bigint(20) NOT NULL,
  `CATALOG_ID` bigint(20) NOT NULL,
  `RIGHT_ID` bigint(20) NOT NULL,
  `CREATE_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATE_TIME` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`USER_ID`, `CATALOG_ID`, `RIGHT_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for document
-- ----------------------------
DROP TABLE IF EXISTS `document`;
CREATE TABLE `document`  (
  `FILE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DOCUMENT_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `FILE_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PARENT_CATALOG_ID` bigint(20) NULL DEFAULT NULL,
  `FILE_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `FILE_SIZE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MIME_TYPE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SHA256` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `IS_PARSE` tinyint(1) NULL DEFAULT NULL,
  `STATUS` tinyint(1) NULL DEFAULT NULL,
  `CREATE_TIME` timestamp(0) NULL DEFAULT NULL,
  `CREATE_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UPDATE_TIME` timestamp(0) NULL DEFAULT NULL,
  `UPDATE_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`FILE_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for file_right
-- ----------------------------
DROP TABLE IF EXISTS `file_right`;
CREATE TABLE `file_right`  (
  `USER_ID` bigint(20) NOT NULL,
  `FILE_ID` bigint(20) NOT NULL,
  `RIGHT_ID` bigint(20) NOT NULL,
  `CREATE_TIME` timestamp(0) NULL DEFAULT NULL,
  `CREATE_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`USER_ID`, `FILE_ID`, `RIGHT_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for parse_doc
-- ----------------------------
DROP TABLE IF EXISTS `parse_doc`;
CREATE TABLE `parse_doc`  (
  `parse_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '解析文档主键',
  `file_id` bigint(20) NULL DEFAULT NULL COMMENT '文档主键',
  `file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '解析后文件存储路径',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '解析时间',
  `file_text` blob NULL COMMENT '文本内容',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态：1正常，3删除',
  PRIMARY KEY (`parse_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `RIGHT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `RIGHT_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `RIGHT_TYPE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `RIGHT_URL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATE_TIME` timestamp(0) NULL DEFAULT NULL,
  `CREATE_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`RIGHT_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `ROLE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REMARK` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STATUS` int(11) NULL DEFAULT NULL,
  `CREATE_TIME` timestamp(0) NULL DEFAULT NULL,
  `CREATE_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UPDATE_TIME` timestamp(0) NULL DEFAULT NULL,
  `UPDATE_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role_right
-- ----------------------------
DROP TABLE IF EXISTS `role_right`;
CREATE TABLE `role_right`  (
  `ROLE_ID` bigint(20) NOT NULL,
  `RIGHT_ID` bigint(20) NOT NULL,
  `CREATE_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATE_TIME` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`, `RIGHT_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `dm_user`;
CREATE TABLE `dm_user`  (
  `USER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_NUM` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `USERNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `USER_PWD` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PWD_SALT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SEX` int(11) NULL DEFAULT NULL,
  `EMAIL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PHONE_NUM` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REMARK` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `AVATAR_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STATUS` int(11) NULL DEFAULT NULL,
  `CREATE_TIME` timestamp(0) NULL DEFAULT NULL,
  `CREATE_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UPDATE_TIME` timestamp(0) NULL DEFAULT NULL,
  `UPDATE_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`USER_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_right
-- ----------------------------
DROP TABLE IF EXISTS `user_right`;
CREATE TABLE `user_right`  (
  `USER_ID` bigint(20) NOT NULL,
  `RIGHT_ID` bigint(20) NOT NULL,
  `CREATE_TIME` timestamp(0) NULL DEFAULT NULL,
  `CREATE_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`USER_ID`, `RIGHT_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `USER_ID` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  `CREATE_TIME` timestamp(0) NULL DEFAULT NULL,
  `CREATE_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`USER_ID`, `ROLE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
