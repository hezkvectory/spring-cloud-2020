CREATE TABLE `goods` (
                         `goodsId` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
                         `goodsName` varchar(500) CHARACTER SET utf8mb4  NOT NULL DEFAULT '' COMMENT 'name',
                         `stock` int(11) NOT NULL DEFAULT '0' COMMENT 'stock',
                         PRIMARY KEY (`goodsId`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4  COMMENT='商品表'



INSERT INTO `goods` (`goodsId`, `goodsName`, `stock`) VALUES
(3, 'green cup1', 70);


CREATE TABLE `t_order_1` (
                             `orderId` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                             `goodsName` varchar(250) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT 'name',
                             PRIMARY KEY (`orderId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_order_2` (
                             `orderId` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                             `goodsName` varchar(250) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT 'name',
                             PRIMARY KEY (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
