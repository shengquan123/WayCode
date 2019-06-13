/*
 Navicat Premium Data Transfer

 Source Server         : 39.104.18.117
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : 39.104.18.117:3306
 Source Schema         : zrlog

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : 65001

 Date: 03/06/2019 18:27:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `commentId` int(11) NOT NULL AUTO_INCREMENT,
  `commTime` datetime(0) NULL DEFAULT NULL,
  `hide` bit(1) NULL DEFAULT NULL,
  `td` datetime(0) NULL DEFAULT NULL,
  `userComment` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userHome` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userIp` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userMail` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userName` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `logId` int(11) NULL DEFAULT NULL,
  `postId` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '多说评论使用',
  `header` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论者头像',
  PRIMARY KEY (`commentId`) USING BTREE,
  UNIQUE INDEX `postId`(`postId`) USING BTREE,
  INDEX `logId`(`logId`) USING BTREE,
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`logId`) REFERENCES `log` (`logId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for fido_model
-- ----------------------------
DROP TABLE IF EXISTS `fido_model`;
CREATE TABLE `fido_model`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '设备id',
  `user_id` bigint(10) NOT NULL COMMENT '用户id',
  `num` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编号',
  `status` int(1) NOT NULL COMMENT '是否可用',
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密钥名称',
  `version` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'U2F_V2' COMMENT '密钥版本',
  `key_handle` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '句柄',
  `public_key` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公钥',
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '网址',
  `attestation_cert` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '证书',
  `compromised` int(1) NOT NULL DEFAULT 0 COMMENT '是否损坏 0否 1是',
  `find_num` int(10) NOT NULL COMMENT '计数器(设备登录多少次）',
  `last_login_time` datetime(0) NOT NULL COMMENT '最后登录时间',
  `add_time` datetime(0) NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fido_model
-- ----------------------------
INSERT INTO `fido_model` VALUES (4, 1, '1810180649083795', 1, '', 'U2F_V2', 'UeJPGFfwu0s1tTI8okAvkqrpzWo4ur-J3APusnTkWfzHCQrR8M9TBANyUrvHS6y23jIGzFNGZVdWTgt8IwfsLe3UGcSBZXCnPnySB7qUjww', 'BAX8MoYo20xjszBMxJct22bQqqnb8oC7accv0xw_LSVP5Vxh1rs_mPxOS7QbpJvVeF1ytc5W2eqzOZHw1hWAqP4', 'https://localhost:8443', 'MIIBNDCB26ADAgECAgoBjsUsf8vm04SfMAoGCCqGSM49BAMCMBUxEzARBgNVBAMTClUyRiBJc3N1ZXIwGhcLMDAwMTAxMDAwMFoXCzAwMDEwMTAwMDBaMBUxEzARBgNVBAMTClUyRiBEZXZpY2UwWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAATm9ud4UW1JEevy_CPBoNBK458a5NrqEyO21jzfe597Em-7T4fu-sIxXDkZsWPeS6ObJTOKIv2r9t8yb2QkFxdooxcwFTATBgsrBgEEAYLlHAIBAQQEAwIEMDAKBggqhkjOPQQDAgNIADBFAiEAwaOmji8WpyFGJwV_YrtyjJ4D56G6YtBGUk5FbSwvP3MCIAtfeOURqhgSn28jbZITIn2StOZ-31PoFt-wXZ3IuQ_e', 0, 524, '2018-10-18 18:49:26', '2018-10-18 18:49:08');

-- ----------------------------
-- Table structure for link
-- ----------------------------
DROP TABLE IF EXISTS `link`;
CREATE TABLE `link`  (
  `linkId` int(11) NOT NULL AUTO_INCREMENT,
  `alt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `linkName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sort` int(11) NULL DEFAULT NULL,
  `status` bit(1) NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`linkId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `logId` int(11) NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `canComment` bit(1) NULL DEFAULT b'1',
  `click` int(11) NULL DEFAULT 0,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `mdContent` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `digest` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `keywords` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `thumbnail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `recommended` bit(1) NULL DEFAULT b'0',
  `releaseTime` datetime(0) NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `typeId` int(11) NULL DEFAULT NULL,
  `userId` int(11) NULL DEFAULT NULL,
  `hot` bit(1) NULL DEFAULT NULL,
  `rubbish` bit(1) NULL DEFAULT NULL,
  `private` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`logId`) USING BTREE,
  INDEX `typeId`(`typeId`) USING BTREE,
  INDEX `userId`(`userId`) USING BTREE,
  CONSTRAINT `log_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `type` (`typeId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `log_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES (1, 'first', b'1', 51, '每天进步一点', NULL, '每天进步一点', '记录', NULL, b'0', '2017-12-29 12:16:45', '记录学习记录', 1, 1, NULL, b'0', b'0');
INSERT INTO `log` VALUES (2, 'second', b'1', 15, '<p>&nbsp; 哎哎哎dsfa&nbsp;</p>', '11去', '22去', '33去', NULL, b'0', '2018-03-22 12:11:10', '44人', 1, 1, NULL, b'0', b'0');
INSERT INTO `log` VALUES (3, '啦啦', b'1', 0, '<p>这是一个测试文章</p><table><tbody><tr class=\"firstRow\"><td width=\"128\" valign=\"top\" style=\"word-break: break-all;\">访问</td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td></tr><tr><td width=\"128\" valign=\"top\" style=\"word-break: break-all;\">发送的</td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td></tr><tr><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td></tr><tr><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td></tr><tr><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td><td width=\"128\" valign=\"top\"><br/></td></tr></tbody></table><p><br/></p>', NULL, ' 这是一个测试文章     访问                      发送的                                                                                              ', '', NULL, b'0', '2018-06-25 11:22:20', '测试一下', 1, 1, NULL, b'1', b'0');
INSERT INTO `log` VALUES (4, '4', b'1', 3, '<p><img src=\"http://39.104.18.117:8080/admin/ueditor/jsp/upload/image/20180625/1529899746830027005.jpg\" title=\"1529899746830027005.jpg\" alt=\"3.jpg\"/></p><p><span style=\"text-decoration: underline;\">这是一个测试图片111</span></p>', NULL, '    这是一个测试图片 ', '', '/WayWebSite/admin/ueditor/jsp/upload/image/20180625/1529899746830027005_thumbnail.jpg?v=1', b'0', '2018-06-25 12:09:29', '图片测试111', 1, 1, NULL, b'0', b'0');

-- ----------------------------
-- Table structure for lognav
-- ----------------------------
DROP TABLE IF EXISTS `lognav`;
CREATE TABLE `lognav`  (
  `navId` int(11) NOT NULL AUTO_INCREMENT,
  `navName` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sort` int(11) NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`navId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lognav
-- ----------------------------
INSERT INTO `lognav` VALUES (1, '主页', 1, '/');
INSERT INTO `lognav` VALUES (2, '管理', 2, '/admin/login');

-- ----------------------------
-- Table structure for plugin
-- ----------------------------
DROP TABLE IF EXISTS `plugin`;
CREATE TABLE `plugin`  (
  `pluginId` int(11) NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `isSystem` bit(1) NULL DEFAULT NULL,
  `pTitle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sort` int(11) NULL DEFAULT NULL,
  `pluginName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `level` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`pluginId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plugin
-- ----------------------------
INSERT INTO `plugin` VALUES (1, '系统提供的插件', b'1', '分类菜单', NULL, 'types', 3);
INSERT INTO `plugin` VALUES (2, NULL, b'1', '标签云', NULL, 'tags', 3);
INSERT INTO `plugin` VALUES (3, NULL, b'1', '友链', NULL, 'links', 2);
INSERT INTO `plugin` VALUES (4, NULL, b'1', '存档', NULL, 'archives', 3);

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `tagId` int(11) NOT NULL AUTO_INCREMENT,
  `count` int(11) NOT NULL DEFAULT 0,
  `text` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`tagId`) USING BTREE,
  UNIQUE INDEX `text`(`text`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (1, 1, '记录');

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `typeId` int(11) NOT NULL AUTO_INCREMENT,
  `alias` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `typeName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`typeId`) USING BTREE,
  INDEX `pid`(`pid`) USING BTREE,
  CONSTRAINT `type_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `type` (`typeId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, 'notes', '', '记录', NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `header` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `secretKey` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密钥',
  PRIMARY KEY (`userId`) USING BTREE,
  UNIQUE INDEX `userName`(`userName`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin@example.com', '96e79218965eb72c92a549dd5a330112', 'admin', NULL, '98755016-f0c0-4ebd-8744-31e28d5ab809');

-- ----------------------------
-- Table structure for website
-- ----------------------------
DROP TABLE IF EXISTS `website`;
CREATE TABLE `website`  (
  `siteId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `value` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `remark` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`siteId`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of website
-- ----------------------------
INSERT INTO `website` VALUES (1, 'rows', '10', NULL);
INSERT INTO `website` VALUES (2, 'template', '/include', NULL);
INSERT INTO `website` VALUES (3, 'autoUpgradeVersion', '86400', NULL);
INSERT INTO `website` VALUES (4, 'pseudo_staticStatus', 'false', NULL);
INSERT INTO `website` VALUES (5, 'title', '互啊佑', NULL);
INSERT INTO `website` VALUES (6, 'second_title', '互啊佑官网', NULL);
INSERT INTO `website` VALUES (7, 'home', 'http://182.254.147.223:8080/', NULL);
INSERT INTO `website` VALUES (8, 'zrlogSqlVersion', '5', NULL);
INSERT INTO `website` VALUES (9, 'plugin_core_db_key', '{\"pluginInfoMap\":{},\"setting\":{\"disableAutoDownloadLostFile\":false}}', NULL);
INSERT INTO `website` VALUES (10, 'language', 'zh_CN', NULL);

SET FOREIGN_KEY_CHECKS = 1;
