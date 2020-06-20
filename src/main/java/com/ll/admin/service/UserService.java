package com.ll.admin.service;

import com.ll.admin.domain.Login;

import java.util.List;
import java.util.Map;

public interface UserService {

    //用户列表
    List<Map<String,Object>> findAllUser ();

    //用户信息
    Map<String,Object> getUser(String userId);

    //创建帐户
    Login createAccount(Map<String, String> params,Boolean isMatchRoles);

    Boolean resetPassword(Map<String, String> params);
}
