package com.ll.admin.service;

import com.ll.admin.domain.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {

    /**
     * 添加/修改角色
     * 根据ID取值断言编辑/添加
     * @param params
     * @return
     */
    Boolean createOrUpdateRole(Map<String, String> params);

    /**
     * 获取所有角色
     * @return
     */
    List<Role> findAllRole();
}
