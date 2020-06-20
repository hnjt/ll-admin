package com.ll.admin.service.impl;

import com.commons.BaseService;
import com.commons.CommonsUtil;
import com.ll.admin.dao.LoginRepository;
import com.ll.admin.dao.RoleRepository;
import com.ll.admin.dao.UserRepository;
import com.ll.admin.dao.UserRolesRepository;
import com.ll.admin.domain.Login;
import com.ll.admin.domain.Role;
import com.ll.admin.domain.User;
import com.ll.admin.domain.UserRoles;
import com.ll.admin.mapper.UserMapper;
import com.ll.admin.service.UserService;
import com.utils.EncryptionUtil;
import com.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRolesRepository userRolesRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Map<String, Object>> findAllUser() {
        return userMapper.findAllUser();
    }

    @Override
    public Map<String, Object> getUser(String userId) {
        return userMapper.getUser( userId );
    }

    //创建帐户
    @Override
    public Login createAccount(Map<String, String> params , Boolean isMatchRoles) {

        //必要条件
        String name = params.getOrDefault( "name", null );
        String username = params.getOrDefault( "username", null );
        String password = params.getOrDefault( "password", null );
        String identity = params.getOrDefault( "identity", null );

        //可选条件
        String phone = params.getOrDefault( "phone", null );
        String email = params.getOrDefault( "email", null );
        String wechat = params.getOrDefault( "wechat", null );
        Integer sex = StringUtils.isNotEmpty( params.get("sex"))?Integer.valueOf( params.get("sex")):null;
        Integer nation = StringUtils.isNotEmpty( params.get("nation"))?Integer.valueOf( params.get("nation")):null;
        Integer address1 = StringUtils.isNotEmpty( params.get("address1"))?Integer.valueOf( params.get("address1")):null;
        Integer address2 = StringUtils.isNotEmpty( params.get("address2"))?Integer.valueOf( params.get("address2")):null;
        Integer address3 = StringUtils.isNotEmpty( params.get("address3"))?Integer.valueOf( params.get("address3")):null;
        String address4 = params.getOrDefault( "address4", null );

        //帐户创建
        Login login = new Login();
        String userId = CommonsUtil.getUUID();
        login.setId( userId );
        login.setName( name );
        login.setUsername( username );
        password = EncryptionUtil.encrypt( password );
        password = MD5.getMD5( password );
        login.setPassword( password );
        login.setStarts( "200" );
        Date newDate = new Date();
        login.setCreateTime( newDate );
        login.setCreator( "--" );

        //默认角色
        if (null != isMatchRoles && isMatchRoles){
            Role role = this.roleRepository.getRoleByName( "ORDINARY" );
            if (null != role){
                UserRoles userRoles = new UserRoles();
                userRoles.setId( CommonsUtil.getUUID() );
                userRoles.setLoginId( userId );
                userRoles.setRolesId( role.getId() );
                userRoles.setRolesId( role.getId() );
                userRoles.setCreateTime( newDate );
                userRoles.setCreator( "--" );
                this.userRolesRepository.save( userRoles );
            }
        }
        Login save = loginRepository.save( login );

        //个人信息
        User user = new User();
        user.setId( userId );
        user.setIdentity( identity );
        user.setSex( sex );
        user.setEmail( email );
        user.setNation( nation );
        user.setPhone(phone);
        user.setWechat( wechat );
        user.setAddress1( address1 );
        user.setAddress2( address2 );
        user.setAddress3( address3 );
        user.setAddress4( address4 );
        user.setCreator( "--" );
        user.setCreateTime( newDate );
        userRepository.save( user );
        return save;
    }

    @Override
    public Boolean resetPassword(Map<String, String> params) {

        Boolean result = false;
        String username = params.getOrDefault( "username", null );
        Login login = this.loginRepository.findByUsername( username );
        if (null == login)
            return result;

        int i = 0;
        String name = params.getOrDefault( "name", null );
        if (StringUtils.isNotBlank( name )){
            if (login.getName().equals( name ))
                i += 20;
        }

        User user = this.userRepository.findById(login.getId()).orElse(new User());
        if (null != user){
            String identity = params.getOrDefault( "identity", null );
            if (StringUtils.isNotBlank( identity )){
                if (user.getIdentity().equals( identity ))
                    i += 30;
            }

            String phone = params.getOrDefault( "phone", null );
            if (StringUtils.isNotBlank( phone )){
                if (user.getPhone().equals( phone ))
                    i += 10;
            }
            String email = params.getOrDefault( "email", null );
            if (StringUtils.isNotBlank( email )){
                if (user.getEmail().equals( email ))
                    i += 10;
            }
            String wechat = params.getOrDefault( "wechat", null );
            if (StringUtils.isNotBlank( wechat )){
                if (user.getWechat().equals( wechat ))
                    i += 10;
            }
            Integer sex = StringUtils.isNotEmpty( params.get("sex"))?Integer.valueOf( params.get("sex")):null;
            if (null != sex){
                if (user.getSex() == sex )
                    i += 10;
            }
            Integer nation = StringUtils.isNotEmpty( params.get("nation"))?Integer.valueOf( params.get("nation")):null;
            if (null != nation){
                if (user.getNation() == nation )
                    i += 10;
            }
            Integer address1 = StringUtils.isNotEmpty( params.get("address1"))?Integer.valueOf( params.get("address1")):null;
            if (null != address1){
                if (user.getAddress1() == address1 )
                    i += 10;
            }
            Integer address2 = StringUtils.isNotEmpty( params.get("address2"))?Integer.valueOf( params.get("address2")):null;
            if (null != address2){
                if (user.getAddress2() == address2 )
                    i += 10;
            }
            Integer address3 = StringUtils.isNotEmpty( params.get("address3"))?Integer.valueOf( params.get("address3")):null;
            if (null != address3){
                if (user.getAddress3() == address3 )
                    i += 10;
            }
            String address4 = params.getOrDefault( "address4", null );
            if (StringUtils.isNotBlank( address4 )){
                if (user.getAddress4().equals( address4 ))
                    i += 10;
            }
        }
        if (i >= 40){
            String password = params.getOrDefault( "password", null );
            password = EncryptionUtil.encrypt( password );
            password = MD5.getMD5( password );
            login.setPassword( password );
            if (null != super.entityManager.merge( login ))
                result = true;
        }

        return result;
    }
}
