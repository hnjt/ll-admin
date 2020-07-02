package com.ll.admin.service.impl;

import com.commons.BaseService;
import com.commons.CommonsUtil;
import com.ll.admin.dao.RoleMenusRepository;
import com.ll.admin.dao.UserRolesRepository;
import com.ll.admin.domain.RoleMenus;
import com.ll.admin.domain.UserRoles;
import com.ll.admin.mapper.AuthMapper;
import com.ll.admin.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class AuthServiceImpl extends BaseService implements AuthService {

    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private UserRolesRepository userRolesRepository;
    @Autowired
    private RoleMenusRepository roleMenusRepository;

    @Override
    public List<Map<String, Object>> findAllUser() {
        return authMapper.findAllUser();
    }

    @Override
    public Map<String,Object> getUser(String userId) {
        return authMapper.getUser( userId );
    }

    @Override
    public List<Map<String, Object>> getRoles(String userId) {
        return this.authMapper.getRoles( userId );
    }

    @Override
    public List<Map<String, Object>> getMeuns(String roleId) {
        return this.authMapper.getMeuns( roleId );
    }

    @Override
    public Boolean userBangdingRoles(String userId, String roleIds,String creator) {
        this.userRolesRepository.deleteByLoginId( userId );
        Date newDate = new Date();
        if (roleIds.contains( "," )) {
            String[] split = roleIds.split( "," );
            List<String> list = Arrays.asList( split );
            list.forEach( roleId->{
                this.saveUserRoles(userId,roleId,creator,newDate);
            } );
        }else
            this.saveUserRoles(userId,roleIds,creator,newDate);
        return true;
    }

    private void saveUserRoles (String userId,String roleId,String reator,Date newDate){
        UserRoles ur = new UserRoles();
        ur.setId( CommonsUtil.getUUID() );
        ur.setLoginId( userId );
        ur.setRolesId( roleId );
        ur.setCreator( reator );
        ur.setCreateTime( newDate );
        this.userRolesRepository.save( ur );
    }

    @Override
    public Boolean roleBangdingMenusJoinFun(Map<String, String> params) {
        String roleId = params.getOrDefault( "roleId", null );
        String menuId = params.getOrDefault( "menuId", null );
        String funOrderNos = params.getOrDefault( "funOrderNos", null );
        String[] funs = null;
        if (funOrderNos.contains( "," )){
            funs = funOrderNos.split( "," );
        }else{
            funs = new String[1];
            funs[0] = funOrderNos;
        }

        this.roleMenusRepository.deleteAllByRoleIdAndMenusId(roleId,menuId);
        RoleMenus rm = new RoleMenus();
        rm.setId( CommonsUtil.getUUID() );
        rm.setRoleId( roleId );
        rm.setMenusId( menuId );
        rm.setCreateTime( new Date(  ) );
        rm.setCreator( params.get( "creator" ) );
        //决定生产的权限码的最大长度
        int funOrderNosLength = Arrays.asList( funs ).stream().mapToInt( map -> Integer.valueOf( map ) ).max().orElse( 0 );
        StringBuffer funSb = new StringBuffer();
        for (int i = 0; i < funOrderNosLength; i++) {
            for (int j = 0; j < funs.length; j++) {
                Integer fun = Integer.valueOf( funs[j] );
                if (i == (fun-1)){//序号转下标
                    funSb.append( "1" );//有权限的该位设置为1，表示启用
                    break;
                }
            }
            if (funSb.length() - i == 1)//对应位已经被开启，跳过赋初始值
                continue;
            funSb.append( "0" );
        }
        rm.setFunCode( funSb.toString() );
        this.roleMenusRepository.save(rm);
        return true;
    }

}
