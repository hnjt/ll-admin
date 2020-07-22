package com.ll.admin.controller;

import com.ll.admin.domain.Login;
import com.ll.commons.BaseController;
import com.ll.commons.ResultVo;
import com.ll.admin.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Api(tags = "权限")
@RequestMapping("/auth")
@RestController
public class AuthController extends BaseController {

    @Autowired
    private AuthService authServiceImpl;

    @ApiOperation(value = "用户列表", notes = "[ 获取用户基础信息结果集 ]")
    @GetMapping(value = "/findAllUser")
    public String findAllUser (
            HttpServletRequest request
    ){
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess( true );
        resultVo.setData( this.authServiceImpl.findAllUser() );
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "用户信息", notes = "[ 用户详细信息 ]")
    @GetMapping(value = "/getUser")
    public String getUser (
            HttpServletRequest request,
            @ApiParam(required = true, name = "userId", value = "用户ID") @RequestParam(name = "userId", required = true) String userId
    ){
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess( true );
        resultVo.setData( this.authServiceImpl.getUser(userId) );
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "角色信息", notes = "[ 根据用户ID获取对应角色信息 ]")
    @GetMapping(value = "/getRoles")
    public String getRoles (
            HttpServletRequest request,
            @ApiParam(required = true, name = "userId", value = "用户ID") @RequestParam(name = "userId", required = true) String userId
    ){
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess( true );
        resultVo.setData( this.authServiceImpl.getRoles(userId) );
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "菜单与操作权限信息", notes = "[ 根据角色ID获取菜单与操作权限信息 ]")
    @GetMapping(value = "/getMeuns")
    public String getMeuns (
            HttpServletRequest request,
            @ApiParam(required = true, name = "type", value = "切换ID类型(user:用户ID；role:角色ID；roles:多个角色ID[多个角色ID需要‘,’分离])") @RequestParam(name = "type", required = true) String type,
            @ApiParam(required = true, name = "id", value = "用户/角色ID(多个角色ID'，'分离)") @RequestParam(name = "id", required = true) String id
    ){
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess( true );
        resultVo.setData( this.authServiceImpl.getMeuns(type,id) );
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "用户绑定角色", notes = "[ 根据用户ID、角色ID建立绑定关系 ]")
    @PostMapping(value = "/userBangdingRoles")
    public String userBangdingRoles (
            HttpServletRequest request,
            @ApiParam(required = true, name = "userId", value = "用户ID") @RequestParam(name = "userId", required = true) String userId,
            @ApiParam(required = true, name = "roleIds", value = "角色ID(多个用','分离，空串则表示解除当前用户所有角色绑定关系)") @RequestParam(name = "roleIds", required = true) String roleIds
    ){
        Login details = super.getDetails( request );
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess( true );
        resultVo.setData( this.authServiceImpl.userBangdingRoles(userId,roleIds,details.getId()));
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "角色绑定菜单建立操作权限", notes = "[ 根据角色ID、菜单ID、操作序号建立角色绑定菜单、操作权限 ]")
    @PostMapping(value = "/roleBangdingMenusJoinFun")
    public String roleBangdingMenusJoinFun (
            HttpServletRequest request,
            @ApiParam(required = true, name = "roleId", value = "角色ID") @RequestParam(name = "roleId", required = true) String roleId,
            @ApiParam(required = true, name = "menuId", value = "菜单ID") @RequestParam(name = "menuId", required = true) String menuId,
            @ApiParam(required = true, name = "funOrderNos", value = "操作序号(多个','分离)") @RequestParam(name = "funOrderNos", required = true) String funOrderNos
    ){
        Login details = super.getDetails( request );
        Map<String, String> params = super.initParams( request );
        params.put( "creator",details.getId() );
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess( true );
        resultVo.setData( this.authServiceImpl.roleBangdingMenusJoinFun(params));
        return resultVo.toJSONString();
    }
}
