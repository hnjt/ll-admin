package com.ll.admin.controller;

import com.ll.commons.ResultVo;
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
@Api(tags = "其它[城市、民族]")
@RequestMapping("/other")
@RestController
public class OhterController {

    @Autowired
    private OtherService otherServiceImpl;

    @ApiOperation(value = "城市获取", notes = "[ 支持异步树 ]")
    @GetMapping(value = "/findAllCitys")
    public String findAllCitys (
            HttpServletRequest request,
            @ApiParam(required = false, name = "id", value = "城市ID(null：查询所有省级单位；ID查询所有子集城市)") @RequestParam(name = "id", required = false) String id
    ){
        ResultVo resultVo = new ResultVo();
        resultVo.setData( this.otherServiceImpl.findAllCitys( id ) );
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "民族获取", notes = "[ 支持回显/下拉表单 ]")
    @GetMapping(value = "/findAllNations")
    public String findAllNations (
            HttpServletRequest request,
            @ApiParam(required = false, name = "id", value = "民族ID(null：查询所有；ID查询指定信息)") @RequestParam(name = "id", required = false) String id
    ){
        ResultVo resultVo = new ResultVo();
        resultVo.setData( this.otherServiceImpl.findAllNations( id ) );
        return resultVo.toJSONString();
    }
}
