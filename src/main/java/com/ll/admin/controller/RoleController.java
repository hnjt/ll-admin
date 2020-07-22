package com.ll.admin.controller;

import com.ll.admin.domain.Login;
import com.ll.commons.BaseController;
import com.ll.commons.ResultVo;
import com.ll.admin.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Api(tags = "角色")
@RequestMapping("/role")
@RestController
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleServiceImpl;

    @ApiOperation(value = "添加/编辑角色", notes = "[ 对角色信息编辑或者添加 ]")
    @PostMapping(value = "/createOrUpdateRole")
    public String createOrUpdateRole (
            HttpServletRequest request,
            @ApiParam(required = false, name = "id", value = "角色ID") @RequestParam(name = "id", required = false) String id,
            @ApiParam(required = true, name = "order", value = "权重") @RequestParam(name = "order", required = true) String order,
            @ApiParam(required = true, name = "name", value = "角色称谓") @RequestParam(name = "name", required = true) String name
    ){
        Map<String, String> params = initParams( request );
        Login details = super.getDetails( request );
        params.put( "creator", details.getId());
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess( this.roleServiceImpl.createOrUpdateRole(params) );
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "获取所有角色", notes = "[ 获取所有角色信息 ]")
    @GetMapping(value = "/findAllRole")
    public String findAllRole (
            HttpServletRequest request
    ){
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess( true );
        resultVo.setData( this.roleServiceImpl.findAllRole() );
        return resultVo.toJSONString();
    }

    //TODO 角色关联业务相对多，暂时不提供删除接口
    @ApiOperation(value = "删除角色信息", notes = "[ 根据ID删除角色信息，有管理的不允许删除，可以强制删除 ]")
    @PostMapping(value = "/deleteRole")
    public String deleteRole (
            HttpServletRequest request,
            @ApiParam(required = true, name = "id", value = "角色ID") @RequestParam(name = "id", required = true) String id,
            @ApiParam(required = true, name = "isCoerce", value = "是否强制") @RequestParam(name = "isCoerce", required = true) Boolean isCoerce
    ){
        Map<String, String> params = initParams( request );
        ResultVo resultVo = new ResultVo();
        this.roleServiceImpl.deleteRole(params);
        return resultVo.toJSONString();
    }
}
