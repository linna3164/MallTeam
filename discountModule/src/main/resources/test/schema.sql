-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
                        `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
                        `user_id` bigint(11) unsigned DEFAULT NULL,
                        `coupon_rule_id` bigint(11) unsigned DEFAULT NULL,
                        `coupon_sn` varchar(63) DEFAULT NULL,
                        `begin_time` datetime(2) DEFAULT NULL,
                        `end_time` datetime(2) DEFAULT NULL,
                        `used_time` datetime(2) DEFAULT NULL,
                        `name` varchar(31) DEFAULT NULL,
                        `pic_url` varchar(255) DEFAULT NULL,
                        `gmt_create` datetime(2) DEFAULT NULL,
                        `gmt_modified` datetime(2) DEFAULT NULL,
                        `is_deleted` tinyint(1) unsigned DEFAULT '0',
                        `status` tinyint(1) unsigned DEFAULT '0',
                        PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for coupon_rule
-- ----------------------------
DROP TABLE IF EXISTS `coupon_rule`;
CREATE TABLE `coupon_rule` (
                             `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
                             `name` varchar(31) DEFAULT NULL,
                             `brief` varchar(63) DEFAULT NULL,
                             `begin_time` datetime(2) DEFAULT NULL,
                             `end_time` datetime(2) DEFAULT NULL,
                             `pic_url` varchar(255) DEFAULT NULL,
                             `valid_period` int(4) unsigned DEFAULT NULL,
                             `strategy` varchar(5000) DEFAULT NULL,
                             `total` int(5) unsigned DEFAULT NULL,
                             `goods_list1` varchar(5000) DEFAULT NULL,
                             `goods_list2` varchar(5000) DEFAULT NULL,
                             `gmt_create` datetime(2) DEFAULT NULL,
                             `gmt_modified` datetime(2) DEFAULT NULL,
                             `is_deleted` tinyint(1) unsigned DEFAULT '0',
                             PRIMARY KEY (`id`)
);



-- ----------------------------
-- Table structure for groupon_rule
-- ----------------------------
DROP TABLE IF EXISTS `groupon_rule`;
CREATE TABLE `groupon_rule` (
                              `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
                              `gmt_create` datetime(2) DEFAULT NULL,
                              `gmt_modified` datetime(2) DEFAULT NULL,
                              `is_deleted` tinyint(1) unsigned DEFAULT '0',
                              `start_time` datetime(2) DEFAULT NULL,
                              `end_time` datetime(2) DEFAULT NULL,
                              `status` tinyint(2) unsigned DEFAULT NULL,
                              `groupon_level_strategy` varchar(255) DEFAULT NULL,
                              `goods_id` bigint(11) unsigned DEFAULT NULL,
                              PRIMARY KEY (`id`)
);


-- ----------------------------
-- Table structure for presale_rule
-- ----------------------------
DROP TABLE IF EXISTS `presale_rule`;
CREATE TABLE `presale_rule` (
                              `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
                              `strategy` varchar(5000) DEFAULT NULL,
                              `product_id` bigint(11) unsigned NOT NULL,
                              `start_time` datetime DEFAULT NULL,
                              `end_time` datetime DEFAULT NULL,
                              `status` tinyint(1) unsigned DEFAULT '0',
                              `gmt_create` datetime(2) DEFAULT NULL,
                              `gmt_modified` datetime(2) DEFAULT NULL,
                              `is_deleted` tinyint(1) unsigned DEFAULT '0',
                              `deposit` decimal(10,2) DEFAULT NULL,
                              PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for presale_rule
-- ----------------------------
DROP TABLE IF EXISTS `presale_rule`;
CREATE TABLE `presale_rule` (
                              `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
                              `goods_id` bigint(11) unsigned NOT NULL,
                              `start_time` datetime DEFAULT NULL,
                              `ad_end_time` datetime DEFAULT NULL,
                              `final_start_time` datetime DEFAULT NULL,
                              `end_time` datetime DEFAULT NULL,
                              `status` tinyint(1) unsigned DEFAULT '0',
                              `gmt_create` datetime(2) DEFAULT NULL,
                              `gmt_modified` datetime(2) DEFAULT NULL,
                              `is_deleted` tinyint(1) unsigned DEFAULT '0',
                              `deposit` decimal(10,2) DEFAULT NULL,
                              `final_payment` decimal(10,2) DEFAULT NULL,
                              PRIMARY KEY (`id`)
);