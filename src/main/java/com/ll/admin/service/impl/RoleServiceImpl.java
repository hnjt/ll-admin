package com.ll.admin.service.impl;

import com.commons.CommonsUtil;
import com.commons.BaseService;
import com.ll.admin.dao.LoginRepository;
import com.ll.admin.dao.RoleRepository;
import com.ll.admin.domain.Login;
import com.ll.admin.domain.Role;
import com.ll.admin.service.RoleService;
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
@Service
@Transactional
public class RoleServiceImpl extends BaseService implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

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

        role.setName( name );
        role.setUpdateTime( new Date(  ) );
        role.setModifier( creator );

        if (null != super.entityManager.merge( role ))
            result = true;
        return result;
    }

    private Boolean save(String name,String creator) {

        Boolean result = false;
        if (null != this.roleRepository.getRoleByName( name ))
            throw new RuntimeException( "角色名称已存在" );

        Role role = new Role();
        role.setId( CommonsUtil.getUUID() );
        role.setName( name );
        role.setCreateTime( new Date());
        role.setCreator( creator );

        if (null != this.roleRepository.save( role ))
            result = true;
        return result;
    }

    @Override
    public List<Role> findAllRole() {
        return this.roleRepository.findAll();
    }
}
