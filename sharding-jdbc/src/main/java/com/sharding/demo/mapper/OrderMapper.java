package com.sharding.demo.mapper;

import com.sharding.demo.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OrderMapper {
    int insertOneOrder(Order orderOne);

    List<Order> selectAllOrder();
}