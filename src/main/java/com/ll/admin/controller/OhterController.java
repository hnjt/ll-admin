package com.ll.admin.controller;

import com.commons.ResultVo;
import com.ll.admin.service.OtherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(description = "其它接口支持")
@RequestMapping("/other")
@RestController
public class OhterController {

    @Autowired
    private OtherService otherServiceImpl;

    @ApiOperation(value = "城市获取", notes = "[ 支持异步树 ]")
    @GetMapping(value = "/findAllCitys")
    public String findAllCitys (
            HttpServletRequest request,
            @ApiParam(required = false, name = "id", value = "城市ID") @RequestParam(name = "id", required = false) Integer id
    ){
        ResultVo resultVo = new ResultVo();
        resultVo.setData( this.otherServiceImpl.findAllCitys( id ) );
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "民族获取", notes = "[ 支持回显/下拉表单 ]")
    @GetMapping(value = "/findAllNations")
    public String findAllNations (
            HttpServletRequest request,
            @ApiParam(required = false, name = "id", value = "民族ID") @RequestParam(name = "id", required = false) Integer id
    ){
        ResultVo resultVo = new ResultVo();
        resultVo.setData( this.otherServiceImpl.findAllNations( id ) );
        return resultVo.toJSONString();
    }
}
