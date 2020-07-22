package com.ll.admin.controller;

import com.ll.admin.domain.Login;
import com.ll.commons.BaseController;
import com.ll.commons.ResultVo;
import com.ll.admin.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Api(tags = "菜单")
@RequestMapping("/menu")
@RestController
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuServiceImpl;

    @ApiOperation(value = "获取菜单", notes = "[ 根据不同的pid传值获取菜单 ]")
    @GetMapping(value = "/findAllMenuByPid")
    public String findAllMenuByPid (
            HttpServletRequest request,
            @ApiParam(required = false, name = "pid", value = "代码(空串/null：所有数据；pid：有值时获取所有子集菜单)") @RequestParam(name = "pid", required = false) String pid
    ){
        Map<String, String> params = initParams( request );
        ResultVo resultVo = new ResultVo();
        resultVo.setData( this.menuServiceImpl.findAllMenu(params) );
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "删除菜单", notes = "[ 根据ID删除菜单 ]")
    @PostMapping(value = "/deleteMenu")
    public String deleteMenu (
            HttpServletRequest request,
            @ApiParam(required = true, name = "id", value = "菜单") @RequestParam(name = "id", required = true) String id
    ){
        Map<String, String> params = initParams( request );
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess( this.menuServiceImpl.deleteMenu(params));
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "添加/编辑菜单", notes = "[ 添加/编辑菜单 ]")
    @PostMapping(value = "/saveOrUpdateMenu")
    public String saveOrUpdateMenu (
            HttpServletRequest request,
            @ApiParam(required = false, name = "id", value = "菜单ID(添加/编辑区分字段)") @RequestParam(name = "id", required = false) String id,
            @ApiParam(required = false, name = "name", value = "菜单名称") @RequestParam(name = "name", required = false) String name,
            @ApiParam(required = false, name = "menuUrl", value = "菜单路径") @RequestParam(name = "menuUrl", required = false) String menuUrl,
            @ApiParam(required = false, name = "pid", value = "父级id") @RequestParam(name = "pid", required = false) String pid,
            @ApiParam(required = false, name = "orderNo", value = "排序") @RequestParam(name = "orderNo", required = false) String orderNo,
            @ApiParam(required = false, name = "icon", value = "图标") @RequestParam(name = "icon", required = false) String icon
    ){
        Map<String, String> params = initParams( request );
        Login details = super.getDetails( request );
        params.put( "creator", details.getId());
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess( this.menuServiceImpl.saveOrUpdateMenu( params ));
        return resultVo.toJSONString();
    }
}
