package com.ll.admin.controller;

import com.commons.ResultVo;
import com.commons.BaseController;
import com.ll.admin.domain.Login;
import com.ll.admin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

    @ApiOperation(value = "完善用户信息", notes = "[ 完善用户信息 (不包括帐户&密码)]")
    @PostMapping(value = "/updateUser")
    public String updateUser (
            HttpServletRequest request,
            @ApiParam(required = true, name = "id", value = "用户ID") @RequestParam(name = "id", required = true) String id,
            @ApiParam(required = true, name = "name", value = "姓名") @RequestParam(name = "name", required = true) String name,
            @ApiParam(required = false, name = "phone", value = "电话") @RequestParam(name = "phone", required = false) String phone,
            @ApiParam(required = false, name = "email", value = "邮箱") @RequestParam(name = "email", required = false) String email,
            @ApiParam(required = false, name = "wechat", value = "微信") @RequestParam(name = "wechat", required = false) String wechat,
            @ApiParam(required = false, name = "identity", value = "身份证号") @RequestParam(name = "identity", required = false) String identity,
            @ApiParam(required = false, name = "nation", value = "民族[int类型]") @RequestParam(name = "nation", required = false) String nation,
            @ApiParam(required = false, name = "sex", value = "性别[int类型；1：男；0：女]") @RequestParam(name = "sex", required = false) String sex,
            @ApiParam(required = false, name = "address1", value = "省份/直辖市") @RequestParam(name = "address1", required = false) String address1,
            @ApiParam(required = false, name = "address2", value = "市/区") @RequestParam(name = "address2", required = false) String address2,
            @ApiParam(required = false, name = "address3", value = "县/乡/镇") @RequestParam(name = "address3", required = false) String address3,
            @ApiParam(required = false, name = "address4", value = "详细地址") @RequestParam(name = "address4", required = false) String address4
    ){
        Map<String, String> params = initParams( request );
        Login details = super.getDetails(request);
        params.put( "creator" ,details.getId());
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess( this.userServiceImpl.updateUser( params ) );
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "删除/禁用帐户", notes = "[ 删除帐户信息]")
    @PostMapping(value = "/deleteUser")
    public String deleteUser (
            HttpServletRequest request,
            @ApiParam(required = true, name = "id", value = "用户ID") @RequestParam(name = "id", required = true) String id,
            @ApiParam(required = true, name = "code", value = "代码(200：开启帐户；500：禁用帐户；DEL：删除帐户)") @RequestParam(name = "code", required = true) String code
    ){
        Map<String, String> params = initParams( request );
        Login details = super.getDetails(request);
        params.put( "creator" ,details.getId());
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess( this.userServiceImpl.deleteUser( params ) );
        return resultVo.toJSONString();
    }
}
