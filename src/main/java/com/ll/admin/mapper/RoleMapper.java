package com.ll.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleMapper {

    /**
     * 获取与角色相关的数据数量
     * @param roleId
     * @return
     */
    Integer roleJoinCount(@Param( "roleId" ) String roleId);
}
