package com.ll.admin.service.impl;

import com.ll.admin.dao.RoleMenusRepository;
import com.ll.admin.dao.UserRolesRepository;
import com.ll.admin.domain.RoleMenus;
import com.ll.admin.domain.UserRoles;
import com.ll.admin.mapper.AuthMapper;
import com.ll.admin.service.AuthService;
import com.ll.commons.BaseService;
import com.ll.commons.CommonsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
    public List<Map<String, Object>> getMeuns(String type,String id) {
        String typetoUpperCase = type.toUpperCase();
        List<String> list = new ArrayList<>(  );
        switch (typetoUpperCase){
            case "USER":
                List<UserRoles> logins = this.userRolesRepository.findByLoginId( id );
                if (logins == null || logins.size() == 0)
                    throw new RuntimeException( "当前用户没有绑定任何角色！" );
                list = Arrays.asList( logins.stream().map(UserRoles::getRolesId ).collect( Collectors.joining( "," )).split( "," ));
                break;
            case "ROLE":
                list.add( id );
                break;
            case "ROLES":
                String[] split = id.split( "," );
                list = Arrays.asList( split );
                break;
        }

        if (typetoUpperCase.contains( "ROLE" ))
            return this.authMapper.getMeuns( list );//角色ID获取权限码，不需要过滤

        List<Map<String, Object>> meuns = this.authMapper.getMeuns( list );
        for (int i = 0; i < meuns.size(); i++) {
            String resultId = meuns.get( i ).getOrDefault( "id", "" ).toString();
            String resultRoleId = meuns.get( i ).getOrDefault( "roleId", "" ).toString();
            char[] resultFunCodes = meuns.get( i ).getOrDefault( "funCode", "" ).toString().toCharArray();
            for (int j = 0; j < meuns.size(); j++) {
                String paramId = meuns.get( j ).getOrDefault( "id", "" ).toString();
                String paramRoleId = meuns.get( j ).getOrDefault( "roleId", "" ).toString();
                char[] paramFunCodes = meuns.get( j ).getOrDefault( "funCode", "" ).toString().toCharArray();
                if (resultId.equals( paramId ) && !resultRoleId.equals( paramRoleId )){//同样的菜单权限对比权限码
                    resultFunCodes = this.getFunCode( resultFunCodes, paramFunCodes );//获取的funCode替换原有的funCode值，准备下次做比计较
                }
            }
            //最后的funCode值就是最大权限
            meuns.get( i ).put( "funCode",String.valueOf( resultFunCodes ));
        }

        return meuns.stream().distinct().collect( Collectors.toList());
    }

    //获取最大权限权限码
    private char[] getFunCode(char[] resultFunCodes,char[] paramFunCodes){
        StringBuffer sb = new StringBuffer();
        int length = resultFunCodes.length >= paramFunCodes.length ? resultFunCodes.length : paramFunCodes.length;
        for (int i = 0; i < length; i++) {
            char resultFunCode = '0';
            char paramFunCode = '0';

            try {
                resultFunCode = resultFunCodes[i];
            }catch (Exception e){
                resultFunCode = '0';
            }

            try {
                paramFunCode = paramFunCodes[i];
            }catch (Exception e){
                paramFunCode = '0';
            }

            if (resultFunCode == '1'||paramFunCode == '1')
                sb.append('1');
            else
                sb.append('1');
        }
        return sb.toString().toCharArray();
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
