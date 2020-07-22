package com.ll.admin.service;

import com.ll.admin.domain.Login;

import java.util.Map;

public interface UserService {


    /**
     * 创建帐户
     * 可以匿名访问
     * @param params
     * @param isMatchRoles
     * @return
     */
    Login createAccount(Map<String, String> params, Boolean isMatchRoles);

    /**
     * 密码重置
     * 可以匿名访问
     * 根据提供的用户信息打分，满足分数要求可以密码重置
     * @param params
     * @return
     */
    Boolean resetPassword(Map<String, String> params);

    /**
     * 编辑用户信息
     * 用户名称和密码不可以编辑
     * @param params
     * @return
     */
    Boolean updateUser(Map<String, String> params);

    /**
     * 删除/禁用帐户
     * 根据用户id&code
     * @param params
     * @return
     */
    Boolean deleteUser(Map<String, String> params);

    Map<String,Object> findUsers();
}
