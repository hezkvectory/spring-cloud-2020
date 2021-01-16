package com.hezk.feign.server.mapper;

import com.hezk.feign.server.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    @Select("select * from user where id= #{id}")
    User findById(Integer id);
}