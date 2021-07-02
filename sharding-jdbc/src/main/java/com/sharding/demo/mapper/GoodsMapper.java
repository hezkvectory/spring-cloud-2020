package com.sharding.demo.mapper;

import com.sharding.demo.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface GoodsMapper {
    Goods selectOneGoods(Long goodsId);

    int updateGoodsStock(@Param("goodsId") Long goodsId, @Param("changeAmount") int changeAmount);

    void save(Goods goods);
}
