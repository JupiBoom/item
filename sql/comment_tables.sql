DROP TABLE IF EXISTS `tb_item_comment`;
CREATE TABLE `tb_item_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评价ID',
  `item_id` int(11) NOT NULL COMMENT '商品ID',
  `order_id` int(11) NOT NULL COMMENT '订单ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `score` tinyint(1) NOT NULL COMMENT '评分（1-5星）',
  `content` text COMMENT '评价内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_item_id` (`item_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品评价表';

DROP TABLE IF EXISTS `tb_item_comment_stat`;
CREATE TABLE `tb_item_comment_stat` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '统计ID',
  `item_id` int(11) NOT NULL COMMENT '商品ID',
  `total_comments` int(11) NOT NULL DEFAULT 0 COMMENT '总评价数',
  `good_comments` int(11) NOT NULL DEFAULT 0 COMMENT '好评数（4-5星）',
  `medium_comments` int(11) NOT NULL DEFAULT 0 COMMENT '中评数（3星）',
  `bad_comments` int(11) NOT NULL DEFAULT 0 COMMENT '差评数（1-2星）',
  `avg_score` decimal(3,2) NOT NULL DEFAULT 0.00 COMMENT '平均评分',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品评价统计表';