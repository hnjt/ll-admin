package com.ll.admin.service;

import com.ll.admin.domain.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService {

    /**
     * 添加/编辑菜单
     * @param params
     * @return
     */
    Boolean saveOrUpdateMenu (Map<String,String> params);

    /**
     * 查询所有菜单
     * 指定父级ID查询所有子集
     * @param params
     * @return
     */
    List<Menu> findAllMenu (Map<String,String> params);

    /**
     * 根据id删除菜单
     * @param params
     */
    Boolean deleteMenu(Map<String,String > params);

}
