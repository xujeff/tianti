SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cms_article
-- ----------------------------
DROP TABLE IF EXISTS `cms_article`;
CREATE TABLE `cms_article` (
  `id` varchar(32) NOT NULL,
  `audit_flag` varchar(2) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `delete_flag` varchar(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `content` longtext,
  `cover_image_url` varchar(128) DEFAULT NULL,
  `href` varchar(128) DEFAULT NULL,
  `order_no` int(11) DEFAULT NULL,
  `publisher` varchar(64) DEFAULT NULL,
  `source_from` varchar(64) DEFAULT NULL,
  `summary` varchar(512) DEFAULT NULL,
  `title` varchar(256) DEFAULT NULL,
  `column_info_id` varchar(32) DEFAULT NULL,
  `institution_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bcwrpr0ji2q3en1mrahtqkjwn` (`column_info_id`),
  KEY `FK_rvbadcnutwj6u19m22ltahouf` (`institution_id`),
  CONSTRAINT `FK_rvbadcnutwj6u19m22ltahouf` FOREIGN KEY (`institution_id`) REFERENCES `ins_institution` (`id`),
  CONSTRAINT `FK_bcwrpr0ji2q3en1mrahtqkjwn` FOREIGN KEY (`column_info_id`) REFERENCES `cms_column_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cms_article
-- ----------------------------

-- ----------------------------
-- Table structure for cms_block
-- ----------------------------
DROP TABLE IF EXISTS `cms_block`;
CREATE TABLE `cms_block` (
  `id` varchar(32) NOT NULL,
  `audit_flag` varchar(2) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `delete_flag` varchar(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `institution_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sqliaerx14xtno1c5gfe85ith` (`institution_id`),
  CONSTRAINT `FK_sqliaerx14xtno1c5gfe85ith` FOREIGN KEY (`institution_id`) REFERENCES `ins_institution` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cms_block
-- ----------------------------

-- ----------------------------
-- Table structure for cms_column_info
-- ----------------------------
DROP TABLE IF EXISTS `cms_column_info`;
CREATE TABLE `cms_column_info` (
  `id` varchar(32) NOT NULL,
  `audit_flag` varchar(2) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `delete_flag` varchar(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `channel` int(11) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `icon` varchar(128) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `order_no` int(11) DEFAULT NULL,
  `path` varchar(512) DEFAULT NULL,
  `institution_id` varchar(32) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_h2vrdlrvrednthin23145no58` (`institution_id`),
  KEY `FK_8pcbm05c14nhwr1bu0ui96d85` (`parent_id`),
  CONSTRAINT `FK_8pcbm05c14nhwr1bu0ui96d85` FOREIGN KEY (`parent_id`) REFERENCES `cms_column_info` (`id`),
  CONSTRAINT `FK_h2vrdlrvrednthin23145no58` FOREIGN KEY (`institution_id`) REFERENCES `ins_institution` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cms_column_info
-- ----------------------------

-- ----------------------------
-- Table structure for ins_institution
-- ----------------------------
DROP TABLE IF EXISTS `ins_institution`;
CREATE TABLE `ins_institution` (
  `id` varchar(32) NOT NULL,
  `audit_flag` varchar(2) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `delete_flag` varchar(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `contact` varchar(64) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `ins_type` int(11) DEFAULT NULL,
  `introduction` longtext,
  `logo` varchar(128) DEFAULT NULL,
  `mobile` varchar(11) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `website` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ins_institution
-- ----------------------------

-- ----------------------------
-- Table structure for ins_institution_user
-- ----------------------------
DROP TABLE IF EXISTS `ins_institution_user`;
CREATE TABLE `ins_institution_user` (
  `id` varchar(32) NOT NULL,
  `institution_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_cn56leamnad8su95vxrlmomn5` (`institution_id`),
  CONSTRAINT `FK_he8fpyojh0pv71ygucw7dsvx4` FOREIGN KEY (`id`) REFERENCES `org_user` (`id`),
  CONSTRAINT `FK_cn56leamnad8su95vxrlmomn5` FOREIGN KEY (`institution_id`) REFERENCES `ins_institution` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ins_institution_user
-- ----------------------------

-- ----------------------------
-- Table structure for org_resource
-- ----------------------------
DROP TABLE IF EXISTS `org_resource`;
CREATE TABLE `org_resource` (
  `id` varchar(32) NOT NULL,
  `audit_flag` varchar(2) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `delete_flag` varchar(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `icon` varchar(512) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `order_no` int(11) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2qoscu42yxypno5iv9w9raj2n` (`parent_id`),
  CONSTRAINT `FK_2qoscu42yxypno5iv9w9raj2n` FOREIGN KEY (`parent_id`) REFERENCES `org_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of org_resource
-- ----------------------------
INSERT INTO `org_resource` VALUES ('10', null, '2016-08-17 17:06:19', '0', '2016-10-28 17:02:57', '', '权限管理', '10', 'module', '/user/list', null);
INSERT INTO `org_resource` VALUES ('11', null, '2016-09-07 15:15:58', '0', '2016-10-28 17:03:06', '', '用户列表', '11', 'page', '/user/list', '10');
INSERT INTO `org_resource` VALUES ('12', null, '2016-09-07 15:17:37', '0', '2016-10-28 17:03:13', '', '角色列表', '12', 'page', '/user/role_list', '10');
INSERT INTO `org_resource` VALUES ('13', null, '2016-09-18 16:34:14', '0', '2016-10-28 17:03:19', '', '菜单列表', '13', 'page', '/user/menu_list', '10');
INSERT INTO `org_resource` VALUES ('2c9025ab5b140c22015b140fbbd30000', null, '2017-03-28 16:37:07', '0', '2017-03-28 16:37:07', '', '换肤管理', '20', 'module', '/user/skin/list', null);
INSERT INTO `org_resource` VALUES ('90', null, '2017-03-17 15:32:19', '0', '2017-03-17 15:32:19', '', '修改密码', '90', 'module', '/user/update_pwd', null);

-- ----------------------------
-- Table structure for org_role
-- ----------------------------
DROP TABLE IF EXISTS `org_role`;
CREATE TABLE `org_role` (
  `id` varchar(32) NOT NULL,
  `audit_flag` varchar(2) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `delete_flag` varchar(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `code` varchar(128) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `no` int(11) DEFAULT NULL,
  `role_name` varchar(64) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of org_role
-- ----------------------------
INSERT INTO `org_role` VALUES ('402881e457f075530157f0791e2f0000', null, '2016-10-23 15:37:31', '0', '2017-03-28 16:37:35', null, '拥有所有的权限', '超级管理员', null, null, null);

-- ----------------------------
-- Table structure for org_role_resource_rel
-- ----------------------------
DROP TABLE IF EXISTS `org_role_resource_rel`;
CREATE TABLE `org_role_resource_rel` (
  `role_id` varchar(32) NOT NULL,
  `resources_id` varchar(32) NOT NULL,
  PRIMARY KEY (`role_id`,`resources_id`),
  KEY `FK_hpsdqtxbypycwcdrw23na40bp` (`resources_id`),
  CONSTRAINT `FK_ew2x71wsjwd939pdgqdsvnnsd` FOREIGN KEY (`role_id`) REFERENCES `org_role` (`id`),
  CONSTRAINT `FK_hpsdqtxbypycwcdrw23na40bp` FOREIGN KEY (`resources_id`) REFERENCES `org_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of org_role_resource_rel
-- ----------------------------
INSERT INTO `org_role_resource_rel` VALUES ('402881e457f075530157f0791e2f0000', '10');
INSERT INTO `org_role_resource_rel` VALUES ('402881e457f075530157f0791e2f0000', '11');
INSERT INTO `org_role_resource_rel` VALUES ('402881e457f075530157f0791e2f0000', '12');
INSERT INTO `org_role_resource_rel` VALUES ('402881e457f075530157f0791e2f0000', '13');
INSERT INTO `org_role_resource_rel` VALUES ('402881e457f075530157f0791e2f0000', '2c9025ab5b140c22015b140fbbd30000');
INSERT INTO `org_role_resource_rel` VALUES ('402881e457f075530157f0791e2f0000', '90');

-- ----------------------------
-- Table structure for org_user
-- ----------------------------
DROP TABLE IF EXISTS `org_user`;
CREATE TABLE `org_user` (
  `id` varchar(32) NOT NULL,
  `audit_flag` varchar(2) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `delete_flag` varchar(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `real_name` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `current_skin` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of org_user
-- ----------------------------
INSERT INTO `org_user` VALUES ('1', null, '2016-08-17 15:17:02', '0', '2017-03-28 17:55:54', null, '13800138000', 'E10ADC3949BA59ABBE56E057F20F883E', '超级管理员', '1', 'admin', '0', 'skin_blue');

-- ----------------------------
-- Table structure for org_user_role_rel
-- ----------------------------
DROP TABLE IF EXISTS `org_user_role_rel`;
CREATE TABLE `org_user_role_rel` (
  `user_id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_ikyyd9vv4u7y3m3yqvqo1vwdd` (`role_id`),
  CONSTRAINT `FK_92837trmh851io1pb73qjakvf` FOREIGN KEY (`user_id`) REFERENCES `org_user` (`id`),
  CONSTRAINT `FK_ikyyd9vv4u7y3m3yqvqo1vwdd` FOREIGN KEY (`role_id`) REFERENCES `org_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of org_user_role_rel
-- ----------------------------
INSERT INTO `org_user_role_rel` VALUES ('1', '402881e457f075530157f0791e2f0000');
