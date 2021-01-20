package com.hezk.h2.mapper;

import com.hezk.h2.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    @Select("select * from user where id= #{id}")
    User findById(Integer id);

}