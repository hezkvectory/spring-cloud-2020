create
DATABASE provider;
create
DATABASE consumer;

CREATE TABLE `account`
(
    `user_id`        varchar(256) NOT NULL,
    `amount`         int(11) DEFAULT NULL,
    `freezed_amount` double DEFAULT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE `undo_log`
(
    `branch_id`     bigint(20) NOT NULL COMMENT 'branch transaction id',
    `xid`           varchar(100) NOT NULL COMMENT 'global transaction id',
    `context`       varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` longblob     NOT NULL COMMENT 'rollback info',
    `log_status`    int(11) NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   datetime(6) NOT NULL COMMENT 'create datetime',
    `log_modified`  datetime(6) NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='AT transaction mode undo table';

CREATE TABLE `account_transaction`
(
    `tx_id`        varchar(100) NOT NULL COMMENT '事物TxId',
    `action_id`    varchar(20)  NOT NULL COMMENT '事物TxId',
    `gmt_create`   datetime     NOT NULL COMMENT '创建时间',
    `gmt_modified` datetime     NOT NULL COMMENT '更新时间',
    `user_id`      varchar(100) NOT NULL COMMENT '账户UID',
    `amount`       varchar(100) NOT NULL COMMENT '变动金额',
    `type`         varchar(100) NOT NULL COMMENT '变动类型',
    `state`        smallint(5) NOT NULL COMMENT '状态: 1.初始化;2.已提交;3.已回滚',
    PRIMARY KEY (`tx_id`, `action_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务流水表';









