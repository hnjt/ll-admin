package com.ll.admin.controller;

import com.commons.BaseController;
import com.commons.ResultVo;
import com.ll.admin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 系统接口
 * 配置的接口可以匿名访问
 */
@Slf4j
@Api(description = "系统")
@RequestMapping("/sys")
@RestController
public class SysController extends BaseController {

    @Autowired
    private UserService userServiceImpl;

    @ApiOperation(value = "创建账号", notes = "[ 创建账号接口,返回帐户信息 ]")
    @PostMapping(value = "/createAccount")
    public String createAccount (
            HttpServletRequest request,
            @ApiParam(required = true, name = "name", value = "姓名") @RequestParam(name = "name", required = true) String name,
            @ApiParam(required = true, name = "username", value = "帐户") @RequestParam(name = "username", required = true) String username,
            @ApiParam(required = true, name = "password", value = "密码") @RequestParam(name = "password", required = true) String password,
            @ApiParam(required = false, name = "phone", value = "电话") @RequestParam(name = "phone", required = false) String phone,
            @ApiParam(required = false, name = "email", value = "邮箱") @RequestParam(name = "email", required = false) String email,
            @ApiParam(required = false, name = "wechat", value = "微信") @RequestParam(name = "wechat", required = false) String wechat,
            @ApiParam(required = false, name = "identity", value = "身份证号") @RequestParam(name = "identity", required = false) String identity,
            @ApiParam(required = false, name = "nation", value = "民族[int类型]") @RequestParam(name = "nation", required = false) String nation,
            @ApiParam(required = false, name = "sex", value = "性别[int类型]") @RequestParam(name = "sex", required = false) String sex,
            @ApiParam(required = false, name = "address1", value = "省份/直辖市") @RequestParam(name = "address1", required = false) String address1,
            @ApiParam(required = false, name = "address2", value = "市/区") @RequestParam(name = "address2", required = false) String address2,
            @ApiParam(required = false, name = "address3", value = "县/乡/镇") @RequestParam(name = "address3", required = false) String address3,
            @ApiParam(required = false, name = "address4", value = "详细地址") @RequestParam(name = "address4", required = false) String address4,
            @ApiParam(required = true, name = "isMatchRoles", value = "是否匹配角色") @RequestParam(name = "isMatchRoles", required = true) Boolean isMatchRoles
    ){
        Map<String, String> params = initParams( request );
        ResultVo resultVo = new ResultVo();
        resultVo.setData( this.userServiceImpl.createAccount(params,isMatchRoles) );
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "密码重置", notes = "[ 提供信息进行打分，分数达标允许重置 ]")
    @PostMapping(value = "/resetPassword")
    public String resetPassword (
            HttpServletRequest request,
            @ApiParam(required = false, name = "name", value = "姓名") @RequestParam(name = "name", required = false) String name,
            @ApiParam(required = true, name = "username", value = "帐户") @RequestParam(name = "username", required = true) String username,
            @ApiParam(required = true, name = "password", value = "密码") @RequestParam(name = "password", required = true) String password,
            @ApiParam(required = false, name = "phone", value = "电话") @RequestParam(name = "phone", required = false) String phone,
            @ApiParam(required = false, name = "email", value = "邮箱") @RequestParam(name = "email", required = false) String email,
            @ApiParam(required = false, name = "wechat", value = "微信") @RequestParam(name = "wechat", required = false) String wechat,
            @ApiParam(required = false, name = "identity", value = "身份证号") @RequestParam(name = "identity", required = false) String identity,
            @ApiParam(required = false, name = "nation", value = "民族[int类型]") @RequestParam(name = "nation", required = false) String nation,
            @ApiParam(required = false, name = "sex", value = "性别[int类型]") @RequestParam(name = "sex", required = false) String sex,
            @ApiParam(required = false, name = "address1", value = "省份/直辖市") @RequestParam(name = "address1", required = false) String address1,
            @ApiParam(required = false, name = "address2", value = "市/区") @RequestParam(name = "address2", required = false) String address2,
            @ApiParam(required = false, name = "address3", value = "县/乡/镇") @RequestParam(name = "address3", required = false) String address3,
            @ApiParam(required = false, name = "address4", value = "详细地址") @RequestParam(name = "address4", required = false) String address4
    ){
        Map<String, String> params = initParams( request );
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess( this.userServiceImpl.resetPassword(params) );
        return resultVo.toJSONString();
    }
}
