package com.ll.admin.controller;

import com.ll.admin.domain.Login;
import com.ll.commons.BaseController;
import com.ll.commons.ResultVo;
import com.ll.admin.service.DictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Api(tags = "数据字典")
@RequestMapping("/dictionary")
@RestController
public class DictionaryController extends BaseController {

    @Autowired
    private DictionaryService dictionaryServiceImpl;

    @ApiOperation(value = "获取数据字典", notes = "[ 根据不同的代码获取数据字典 ]")
    @GetMapping(value = "/findAllDictionaryByPCode")
    public String findAllDictionaryByPCode (
            HttpServletRequest request,
            @ApiParam(required = false, name = "code", value = "代码(空串/null：所有数据；字符串'null'获取所有顶级数据；code：获取所有子集)") @RequestParam(name = "code", required = false) String code
    ){
        Map<String, String> params = initParams( request );
        ResultVo resultVo = new ResultVo();
        resultVo.setData( this.dictionaryServiceImpl.findAllDictionaryByPCode(params) );
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "删除数据字典", notes = "[ 根据ID删除数据字典 ]")
    @PostMapping(value = "/deleteDictionary")
    public String deleteDictionary (
            HttpServletRequest request,
            @ApiParam(required = true, name = "id", value = "字典ID") @RequestParam(name = "id", required = true) String id
    ){
        Map<String, String> params = initParams( request );
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess( this.dictionaryServiceImpl.deleteDictionary(params) );
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "添加/编辑数据字典", notes = "[ 添加/编辑数据字典 ]")
    @PostMapping(value = "/saveOrUpdateDictionary")
    public String saveOrUpdateDictionary (
            HttpServletRequest request,
            @ApiParam(required = false, name = "id", value = "字典ID(添加/编辑区分字段)") @RequestParam(name = "id", required = false) String id,
            @ApiParam(required = true, name = "code", value = "代码") @RequestParam(name = "code", required = true) String code,
            @ApiParam(required = false, name = "pCode", value = "父级代码(null/''：顶级数据；pCode：创建子集)") @RequestParam(name = "pCode", required = false) String pCode,
            @ApiParam(required = false, name = "orderNo", value = "排序") @RequestParam(name = "orderNo", required = false) String orderNo,
            @ApiParam(required = true, name = "name", value = "名称") @RequestParam(name = "name", required = true) String name,
            @ApiParam(required = false, name = "isPermitDelete", value = "是否允许删除(1：允许；0：不允许)") @RequestParam(name = "isPermitDelete", required = false) String isPermitDelete,
            @ApiParam(required = false, name = "remarks", value = "描述") @RequestParam(name = "remarks", required = false) String remarks
    ){
        Map<String, String> params = initParams( request );
        Login details = super.getDetails( request );
        params.put( "creator", details.getId());
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess( this.dictionaryServiceImpl.saveOrUpdateDictionary(params) );
        return resultVo.toJSONString();
    }
}
