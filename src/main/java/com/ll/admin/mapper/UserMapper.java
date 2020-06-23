package com.ll.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    //用户列表
    List<Map<String,Object>> findAllUser ();

    //用户信息
    Map<String,Object> getUser(@Param( "userId" ) String userId);
}
