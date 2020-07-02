package com.ll.admin.service.impl;

import com.commons.CommonsUtil;
import com.commons.BaseService;
import com.ll.admin.dao.LoginRepository;
import com.ll.admin.dao.RoleMenusRepository;
import com.ll.admin.dao.RoleRepository;
import com.ll.admin.dao.UserRolesRepository;
import com.ll.admin.domain.Login;
import com.ll.admin.domain.Role;
import com.ll.admin.mapper.RoleMapper;
import com.ll.admin.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 角色业务层
 */
@Slf4j
@Service
@Transactional
public class RoleServiceImpl extends BaseService implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRolesRepository userRolesRepository;
    @Autowired
    private RoleMenusRepository roleMenusRepository;

    @Override
    public Boolean createOrUpdateRole(Map<String, String> params) {
        Boolean result = false;
        String id = params.getOrDefault( "id", null );
        String name = params.getOrDefault( "name", null );
        String creator = params.getOrDefault( "creator", null );
        if (StringUtils.isBlank( id ))
            result = this.save(name,creator);
        else
            result = this.update(id,name,creator);
        return result;
    }

    private Boolean update(String id, String name,String creator) {

        Boolean result = false;
        Role role = this.roleRepository.findById( id ).orElse( new Role() );
        if (null == role && StringUtils.isBlank( role.getId() ))
            throw new RuntimeException( "数据不存在" );
        this.isRoleNameExist(name);

        role.setName( name );
        role.setUpdateTime( new Date(  ) );
        role.setModifier( creator );

        if (null != super.entityManager.merge( role ))
            result = true;
        return result;
    }

    private Boolean save(String name,String creator) {

        Boolean result = false;
        this.isRoleNameExist(name);

        Role role = new Role();
        role.setId( CommonsUtil.getUUID() );
        role.setName( name );
        role.setCreateTime( new Date());
        role.setCreator( creator );

        if (null != this.roleRepository.save( role ))
            result = true;
        return result;
    }

    /**
     * 校验角色名称是否唯一
     * 已经存在则会抛出异常
     * @param name
     */
    private void isRoleNameExist(String name){
        if (null != this.roleRepository.getRoleByName( name ))
            throw new RuntimeException( "角色名称已存在" );
    }

    @Override
    public List<Role> findAllRole() {
        return this.roleRepository.findAll();
    }

    @Override
    public Boolean deleteRole(Map<String, String> params) {
        String id = params.getOrDefault( "id", null );
        String isCoerceStr = params.getOrDefault( "isCoerce", null );
        Boolean isCoerce = Boolean.valueOf( isCoerceStr );
        if (!isCoerce && this.roleMapper.roleJoinCount( id ) > 0)
            throw new RuntimeException( "当前角色存在关联关系不允许删除" );
        if (isCoerce){
            this.roleRepository.deleteById( id );
            this.userRolesRepository.deleteAllByRolesId( id );
            this.roleMenusRepository.deleteAllByRoleId(id);
            log.info( "正在强制删除ROLE_ID:{}的相关信息",id );
        }else{
            this.roleRepository.deleteById( id );
            log.info( "正在删除ROLE_ID:{}的角色信息，不涉及关联信息",id );
        }

        return true;
    }
}
