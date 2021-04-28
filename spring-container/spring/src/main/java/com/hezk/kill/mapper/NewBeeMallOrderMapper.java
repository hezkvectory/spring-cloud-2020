package com.hezk.kill.mapper;

import com.hezk.kill.domain.NewBeeMallOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewBeeMallOrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(NewBeeMallOrder record);

    int insertSelective(NewBeeMallOrder record);

    NewBeeMallOrder selectByPrimaryKey(Long orderId);

    NewBeeMallOrder selectByOrderNo(String orderNo);

    int updateByPrimaryKeySelective(NewBeeMallOrder record);

    int updateByPrimaryKey(NewBeeMallOrder record);

    List<NewBeeMallOrder> selectByPrimaryKeys(@Param("orderIds") List<Long> orderIds);

    int expireOrder(@Param("orderNo") String orderNo);
}