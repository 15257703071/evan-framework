
-- 表 broker_message.broker_message 结构
CREATE TABLE IF NOT EXISTS `broker_message` (
  `message_id` varchar(128) NOT NULL COMMENT 'mq消息id',
  `message` varchar(4000) COMMENT 'mq消息持久化内容',
  `biz_type` varchar(10) DEFAULT NULL COMMENT '业务类型',
  `biz_id` varchar(128) DEFAULT NULL COMMENT '业务id',
  `try_count` int(4) DEFAULT 0 COMMENT '重试次数',
  `status` varchar(10) DEFAULT '' COMMENT '消息状态，0：发送中 1：成功 2：失败',
  `next_retry` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '下次重试时间',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;