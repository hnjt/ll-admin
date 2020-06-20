package com.ll.admin.controller;

import com.commons.ResultVo;
import com.commons.BaseController;
import com.ll.admin.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Api(description = "角色")
@RequestMapping("/role")
@RestController
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleServiceImpl;

    @ApiOperation(value = "添加/编辑角色", notes = "[ 对角色信息编辑或者添加 ]")
    @PostMapping(value = "/createOrUpdateRole")
    public String createOrUpdateRole (
            HttpServletRequest request,
//            @ApiIgnore Authentication authentication,
            @ApiParam(required = false, name = "id", value = "角色ID") @RequestParam(name = "id", required = false) String id,
            @ApiParam(required = true, name = "name", value = "角色称谓") @RequestParam(name = "name", required = true) String name
    ){
        Map<String, String> params = initParams( request );
//        params.put( "userName", authentication.getName());
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
}
