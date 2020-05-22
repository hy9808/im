CREATE TABLE `chat_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录表id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `recipient_id` bigint(20) DEFAULT NULL COMMENT '接收者用户ID',
  `has_read` int(2) DEFAULT '1' COMMENT '是否已读(0.已读 1.未读)',
  `has_delete` int(2) DEFAULT '0' COMMENT '是否删除(0未删除,1已删除)',
  `msg_type` int(2) DEFAULT '0' COMMENT '消息类型(0.文字 1.图片,2.视频)',
  `message` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '消息内容(如果是图片则为文件路径)',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（0逻辑删除、1数据有效）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='聊天记录表';


CREATE TABLE `chat_sys_msg` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sys_msg_type` int(2) DEFAULT '0' COMMENT '消息类型 0.系统消息 1.订单消息',
  `msg_type` int(2) DEFAULT '0' COMMENT '消息类型(0.文字 1.图片,2.视频)',
  `message` varchar(500) NOT NULL COMMENT '消息内容(如果是图片则为文件路径)',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（0逻辑删除、1数据有效）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='系统级消息记录';

CREATE TABLE `chat_sys_read` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `read_count` int(8) DEFAULT '0' COMMENT '读取的条数',
  `read_type` int(2) DEFAULT '0' COMMENT '读取消息类型 0.系统消息 1.订单消息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='系统级消息表读取表';



CREATE TABLE `chat_friend` (
  `friend_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_friend_id` bigint(20) NOT NULL COMMENT '朋友id',
  `friend_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '朋友昵称',
  `remarks` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  `friend_group_id` bigint(20) NOT NULL COMMENT '聊天分组表id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间 yyyy-MM-dd hh:mm:ss',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间 yyyy-MM-dd hh:mm:ss',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（0逻辑删除、1数据有效）',
  PRIMARY KEY (`friend_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='好友表';

CREATE TABLE `chat_friend_grouping` (
  `friend_group_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '好友表主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `group_name` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '分组名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间 yyyy-MM-dd hh:mm:ss',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间 yyyy-MM-dd hh:mm:ss',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（0逻辑删除、1数据有效）',
  PRIMARY KEY (`friend_group_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='好友分组表';

CREATE TABLE `chat_friend_request` (
  `friend_request_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '好友请求表id',
  `requester_id` bigint(20) NOT NULL COMMENT '请求者id',
  `user_id` bigint(20) NOT NULL COMMENT '接收请求的用户id',
  `request_content` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '请求内容',
  `request_status` int(10) NOT NULL COMMENT '请求状态  1:同意；2:拒绝 3:未处理',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '请求时间 yyyy-MM-dd hh:mm:ss',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间 yyyy-MM-dd hh:mm:ss',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（0逻辑删除、1数据有效）',
  PRIMARY KEY (`friend_request_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='好友请求表';

CREATE TABLE `chat_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '群主键id',
  `group_name` varchar(50) NOT NULL COMMENT '群名',
  `group_create_user_id` bigint(20) NOT NULL COMMENT '群主',
  `group_announcement` varchar(500) DEFAULT NULL COMMENT '群公告',
  `group_image` varchar(500) DEFAULT NULL COMMENT '群头像',
  `group_introduction` varchar(500) DEFAULT NULL COMMENT '群简介',
  `expand` varchar(500) DEFAULT NULL COMMENT '拓展字段',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态（0逻辑删除、1数据有效）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='群组基础表';

CREATE TABLE `chat_user_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `group_id` bigint(20) NOT NULL COMMENT '群id',
  `user_group_remarks` varchar(50) DEFAULT NULL COMMENT '用户群昵称',
  `expand` varchar(500) DEFAULT NULL COMMENT '拓展字段',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态（0逻辑删除、1数据有效）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='用户和群关联表';