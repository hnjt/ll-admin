package com.ll.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AuthMapper {

    //用户列表
    List<Map<String,Object>> findAllUser ();

    //用户信息
    Map<String,Object> getUser(@Param( "userId" ) String userId);

    //角色信息
    List<Map<String,Object>> getRoles(@Param( "userId" ) String userId);

    //菜单信息&操作权限
    List<Map<String,Object>> getMeuns(@Param( "roleId" ) String roleId);

}
