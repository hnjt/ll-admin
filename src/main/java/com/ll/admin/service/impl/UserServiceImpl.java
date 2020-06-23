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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
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

        if (null != this.loginRepository.findByUsername( username ))
            throw new RuntimeException( "帐户已存在" );

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

    @Override
    public Boolean updateUser(Map<String, String> params) {
        String id = params.getOrDefault( "id", null );
        if (StringUtils.isBlank( id ))
            throw new RuntimeException( "ID为null" );
        Login login = this.loginRepository.findById( id ).orElse( new Login() );
        if (null == login && StringUtils.isBlank( login.getId() ))
            throw new RuntimeException( "编辑对象不存在" );
        String name = params.getOrDefault( "name", null );
        if (StringUtils.isNotBlank( name ))
            login.setName( name );
        Date newDate = new Date();
        login.setUpdateTime( newDate );
        String creator = params.getOrDefault( "creator", null );
        if (StringUtils.isNotBlank( creator ))
            login.setModifier( creator );
        Login m1 = super.entityManager.merge( login );

        User user = this.userRepository.findById( id ).orElse( new User() );
        String phone = params.getOrDefault( "phone", null );
        if (StringUtils.isNotBlank( phone ))
            user.setPhone( phone );
        String email = params.getOrDefault( "email", null );
        if (StringUtils.isNotBlank( email ))
            user.setEmail( email );
        String wechat = params.getOrDefault( "wechat", null );
        if (StringUtils.isNotBlank( wechat ))
            user.setWechat( wechat );
        String identity = params.getOrDefault( "identity", null );
        if (StringUtils.isNotBlank( identity ))
            user.setIdentity( identity );
        String nationStr = params.getOrDefault( "nation", null );
        Integer nation = StringUtils.isNotBlank( nationStr ) ? Integer.valueOf( nationStr ) : null;
        if (null != nation)
            user.setNation( nation );
        String sexStr = params.getOrDefault( "sex", null );
        Integer sex = StringUtils.isNotBlank( sexStr ) ? Integer.valueOf( sexStr ) : null;
        if (null != sex)
            user.setSex( sex );
        String address1Str = params.getOrDefault( "address1", null );
        Integer address1 = StringUtils.isNotBlank( address1Str ) ? Integer.valueOf( address1Str ) : null;
        if (null != address1)
            user.setAddress1( address1 );
        String address2Str = params.getOrDefault( "address2", null );
        Integer address2 = StringUtils.isNotBlank( address2Str ) ? Integer.valueOf( address2Str ) : null;
        if (null != address2)
            user.setAddress2( address2 );
        String address3Str = params.getOrDefault( "address3", null );
        Integer address3 = StringUtils.isNotBlank( address3Str ) ? Integer.valueOf( address3Str ) : null;
        if (null != address3)
            user.setAddress3( address3 );
        String address4Str = params.getOrDefault( "address4", null );
        Integer address4 = StringUtils.isNotBlank( address4Str ) ? Integer.valueOf( address4Str ) : null;
        if (null != address4Str)
            user.setAddress4( address4Str );
        user.setModifier( creator );
        user.setUpdateTime( newDate );

        User m2 = null;
        if (null != user && StringUtils.isNotBlank( user.getId() )){
            m2 = super.entityManager.merge( user );
        }else{
            user.setId( id );
            user.setCreateTime( newDate );
            user.setCreator( creator );
            m2 = this.userRepository.save( user );
        }

        return (null != m1 && StringUtils.isNotBlank( m1.getId() ) && null != m2 && StringUtils.isNotBlank( m2.getId() ));
    }

    @Override
    public Boolean deleteUser(Map<String, String> params) {
        boolean result = false;
        String id = params.getOrDefault( "id", null );
        String code = params.getOrDefault( "code", null );
        code = code.toUpperCase();
        if ("200".equals( code ) || "500".equals( code ))
            result = this.updateStarts( code,id );
        else if ("DEL".equals( code )){
            Login login = this.loginRepository.findById( id ).orElse( new Login() );
            if (StringUtils.isBlank( login.getId() ))
                throw new RuntimeException( login.getId() + "对象信息不存在" );
            this.loginRepository.delete( login );
            log.info( "ID：{}：帐户信息已删除" ,id);
            User user = this.userRepository.findById( id ).orElse( new User() );
            if (StringUtils.isBlank( user.getId() ))
                log.info( "ID：{}：无关联详细数据" ,id);
            else {
                this.userRepository.delete( user );
                log.info( "ID：{}：关联详细数据已删除" ,id);
            }
            List<UserRoles> ur = this.userRolesRepository.findByLoginId( id );
            if (null != ur && ur.size() > 0){
                this.userRolesRepository.deleteByLoginId( id );
                log.info( "LOGINID：{}：关联角色数据已删除" ,id);
            }else
                log.info( "LOGINID：{}：无关联角色数据" ,id);
            result = true;
        }

        return result;
    }

    private Boolean updateStarts(String code,String id){
        boolean result = false;
        Login login = this.loginRepository.findById( id ).orElse( new Login() );
        if (StringUtils.isBlank(login.getId()))
            throw new RuntimeException( login.getId() + "对象信息不存在" );
        login.setStarts( code );
        if (null != super.entityManager.merge( login ))
            result = true;
        return result;
    }
}
