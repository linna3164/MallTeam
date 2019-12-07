

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(11) unsigned DEFAULT NULL,
  `ip` varchar(31) DEFAULT NULL,
  `type` tinyint(4) unsigned DEFAULT NULL,
  `action` varchar(63) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `gmt_create` datetime(2) DEFAULT NULL,
  `gmt_modified` datetime(2) DEFAULT NULL,
  `action_id` bigint(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
);
