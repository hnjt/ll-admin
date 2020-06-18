package com.ll.admin.controller;

import com.commons.ResultVo;
import com.ll.admin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(description = "用户")
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userServiceImpl;

    @ApiOperation(value = "用户列表", notes = "[ 获取用户结果集 ]")
    @GetMapping(value = "/findAllUser")
    public String findAllUser (){
        ResultVo resultVo = new ResultVo();
        resultVo.setData( this.userServiceImpl.findAll() );
        return resultVo.toJSONString();
    }
}
