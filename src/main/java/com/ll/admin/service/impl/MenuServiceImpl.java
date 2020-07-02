package com.ll.admin.service.impl;

import com.commons.BaseService;
import com.commons.CommonsUtil;
import com.ll.admin.dao.MenuRepository;
import com.ll.admin.domain.Menu;
import com.ll.admin.service.MenuService;
import com.utils.IMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class MenuServiceImpl extends BaseService implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public Boolean saveOrUpdateMenu(Map<String, String> params){
        log.info( "参数监控：{}",params.toString() );

        boolean result = false;
        String id = params.getOrDefault( "id", null );
        if (StringUtils.isBlank( id )){
            try {
                result = this.save(params);
            } catch (Exception e) {
                throw new RuntimeException( "map 转换实体对象异常" );
            }
        }else {
            result = this.update(id,params);
        }
        return result;
    }

    private boolean update(String id, Map<String, String> params) {
        Menu menu = this.menuRepository.findById( id ).orElse( new Menu() );
        String name = params.getOrDefault( "name", null );
        if (StringUtils.isNotBlank( name ))
            menu.setName( name );
        String menuUrl = params.getOrDefault( "menuUrl", null );
        if (StringUtils.isNotBlank( menuUrl ))
            menu.setMenuUrl( menuUrl );
        String pid = params.getOrDefault( "pid", null );
        if (StringUtils.isNotBlank( pid ))
            menu.setPid( pid );
        String orderNoStr = params.getOrDefault( "orderNo", null );
        Integer orderNo = StringUtils.isNotBlank( orderNoStr ) ? Integer.valueOf( orderNoStr ) : null;
        if (null != orderNo)
            menu.setOrderNo( orderNo );
        String icon = params.getOrDefault( "icon", null );
        if (StringUtils.isNotBlank( icon ))
            menu.setIcon( icon );
        String creator = params.getOrDefault( "creator", null );
        if (StringUtils.isNotBlank( creator ))
            menu.setModifier( creator );
        menu.setUpdateTime( new Date() );
        menu.setPath( this.getPath( menu ) );

        Menu merge = super.entityManager.merge( menu );

        /*
        旗下子集递归处理
         */
        params.put( "pid",menu.getId());
        List<Menu> allMenu = this.findAllMenu( params );
        allMenu.forEach( m ->{
            log.info( "处理{}关联菜单：{}",menu.getPath(), m.getPath());
            this.update( m.getId(),new HashMap<>() );
        } );
        return null != merge ? true : false;
    }

    private boolean save(Map<String, String> params) throws Exception {
        Menu menu = IMapUtil.mapToBean( Menu.class, params );
        if (isNameExist(menu.getName()))
            throw new RuntimeException( "菜单名称已经存在" );
        menu.setId( CommonsUtil.getUUID() );
        menu.setPath( this.getPath( menu ) );
        String creator = params.getOrDefault( "creator", null );
        if (StringUtils.isNotBlank( creator ))
            menu.setCreator( creator );
        menu.setCreateTime( new Date(  ) );
        return null != this.menuRepository.save( menu ) ? true : false;
    }

    private String getPath (Menu menu){
        String result = "";
        if (StringUtils.isNotBlank( menu.getPid() )){
            Menu parentMenu = this.menuRepository.findById(menu.getPid()).orElse( new Menu() );
            StringBuffer sb = new StringBuffer( parentMenu.getPath() );
            if (StringUtils.isBlank( sb ))
                sb.append( menu.getName() );
            else
                sb.append( "-" ).append( menu.getName() );
            result = sb.toString();
        }else {
            result = menu.getName();
        }
        return result;
    }

    private Boolean isNameExist(String name){
        Menu menu = this.menuRepository.findByName( name );
        return null != menu && StringUtils.isNotBlank( menu.getId() ) ? true : false;
    }


    @Override
    public List<Menu> findAllMenu(Map<String, String> params) {
        List<Menu> all = this.menuRepository.findAll();
        String pid = params.getOrDefault( "pid", null );
        if (StringUtils.isNotBlank( pid )){
            all = all.stream().filter( menu -> pid.equals( menu.getPid() ) ).collect( Collectors.toList());
        }
        return all.stream().sorted( Comparator.comparing(Menu::getOrderNo)).collect( Collectors.toList());
    }

    @Override
    public Boolean deleteMenu(Map<String,String > params) {
        Menu menu = this.menuRepository.findById( params.get( "id" ) ).orElse( new Menu() );
        params.put( "pid", menu.getId());
        List<Menu> allMenu = this.findAllMenu( params );
        if (null != allMenu && allMenu.size() > 0)
            throw new RuntimeException( "存在子集菜单不允许删除" );
        this.menuRepository.delete( menu );
        return true;
    }
}
