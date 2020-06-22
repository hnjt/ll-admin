package com.ll.admin.controller;

import com.commons.ResultVo;
import com.commons.BaseController;
import com.ll.admin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(description = "用户")
@RequestMapping("/user")
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userServiceImpl;

    @ApiOperation(value = "用户列表", notes = "[ 获取用户基础信息结果集 ]")
    @GetMapping(value = "/findAllUser")
    public String findAllUser (
            HttpServletRequest request
    ){
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess( true );
        resultVo.setData( this.userServiceImpl.findAllUser() );
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
        resultVo.setData( this.userServiceImpl.getUser(userId) );
        return resultVo.toJSONString();
    }
}
