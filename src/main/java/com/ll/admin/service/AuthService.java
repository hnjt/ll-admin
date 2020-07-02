package com.ll.admin.service;

import java.util.List;
import java.util.Map;

public interface AuthService {

    /**
     * 用户列表展示
     * @return
     */
    List<Map<String,Object>> findAllUser();

    /**
     * 用户详细信息查询
     * 根据用户ID
     * @param userId
     * @return
     */
    Map<String,Object> getUser(String userId);

    /**
     * 角色信息
     * @param userId
     * @return
     */
    List<Map<String,Object>> getRoles(String userId);

    /**
     * 菜单信息&操作权限
     * @param roleId
     * @return
     */
    List<Map<String,Object>> getMeuns(String roleId);

    /**
     * 用户绑定角色
     * @param userId
     * @param roleId
     */
    Boolean userBangdingRoles (String userId,String roleIds,String creator);

    /**
     * 角色绑定菜单
     * 生成操作权限码
     * @param params
     * @return
     */
    Boolean roleBangdingMenusJoinFun(Map<String, String> params);
}
