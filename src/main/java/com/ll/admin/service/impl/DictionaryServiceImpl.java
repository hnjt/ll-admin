package com.ll.admin.service.impl;

import com.commons.BaseService;
import com.commons.CommonsUtil;
import com.ll.admin.dao.DictionaryRepository;
import com.ll.admin.domain.Dictionary;
import com.ll.admin.service.DictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class DictionaryServiceImpl extends BaseService implements DictionaryService {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Override
    public List<Dictionary> findAllDictionaryByPCode(Map<String, String> params) {
        String code = params.getOrDefault( "code", null );
        List<Dictionary> all = dictionaryRepository.findAll();
        return all.stream()
                .filter( d ->{
                    if(StringUtils.isBlank( code ))
                        return true;//空值则不过滤
                    else if ("NULL".equals( code.toUpperCase() )  && null == d.getpCode())
                        return true;//字符串“NULL”,则遍历所有顶级字典
                    else if (null != d.getpCode() && code.toUpperCase().equals( d.getpCode().toUpperCase() ))
                        return true;//正常传值则返回下级数据结构
                    return false;
                } )
                .collect( Collectors.toList());
    }

    @Override
    public Boolean deleteDictionary(Map<String, String> params) {
        String id = params.getOrDefault( "id", null );
        Dictionary dictionary = dictionaryRepository.findById( id ).orElse( new Dictionary() );
        if (StringUtils.isBlank( dictionary.getId() ))
            throw new RuntimeException( "对应数据不存在" );
        else if ( dictionary.getIsPermitDelete() == 0)
            throw new RuntimeException( "数据不允许删除" );

        this.dictionaryRepository.delete( dictionary );
        return true;
    }

    @Override
    public Boolean saveOrUpdateDictionary(Map<String, String> params) {

        String id = params.getOrDefault( "id", null );
        Dictionary dictionary = null;

        if (StringUtils.isBlank( id ))
            dictionary = this.save(params);
        else {
            dictionary = this.update(params,id);
        }

        return null != dictionary && StringUtils.isNotBlank( dictionary.getId() );
    }

    private Dictionary update(Map<String, String> params,String id) {
        Dictionary dictionary = this.dictionaryRepository.findById( id ).orElse( new Dictionary() );
        dictionary.setId( id );
        String code = params.getOrDefault( "code", null );
        if (StringUtils.isNotBlank( code ))
            dictionary.setCode( code );
        String pCode = params.getOrDefault( "pCode", null );
        if (StringUtils.isNotBlank( pCode ))
            dictionary.setpCode( pCode );
        String orderNoStr = params.getOrDefault( "orderNo", null );
        Integer orderNo = StringUtils.isNotBlank( orderNoStr ) ? Integer.valueOf( orderNoStr ) : null;
        if (null != orderNo)
            dictionary.setOrderNo( orderNo );
        String name = params.getOrDefault( "name", null );
        if (StringUtils.isNotBlank( name ))
            dictionary.setName( name );
        String isPermitDeleteStr = params.getOrDefault( "isPermitDelete", null );
        Integer isPermitDelete = StringUtils.isNotBlank( isPermitDeleteStr ) ? Integer.valueOf( isPermitDeleteStr ) : null;
        if (null != isPermitDelete)
            dictionary.setIsPermitDelete( isPermitDelete );
        String remarks = params.getOrDefault( "remarks", null );
        if (StringUtils.isNotBlank( remarks ))
            dictionary.setRemarks( remarks );
        dictionary.setUpdateTime( new Date(  ) );
        String creator = params.getOrDefault( "creator", null );
        if (StringUtils.isNotBlank( creator ))
            dictionary.setCreator( creator );

        return super.entityManager.merge( dictionary );
    }

    private Dictionary save(Map<String, String> params) {

        String code = params.getOrDefault( "code", null );
        String pCode = params.getOrDefault( "pCode", null );
        String orderNoStr = params.getOrDefault( "orderNo", null );
        Integer orderNo = StringUtils.isNotBlank( orderNoStr ) ? Integer.valueOf( orderNoStr ) : null;
        String name = params.getOrDefault( "name", null );
        String isPermitDeleteStr = params.getOrDefault( "isPermitDelete", null );
        Integer isPermitDelete = StringUtils.isNotBlank( isPermitDeleteStr ) ? Integer.valueOf( isPermitDeleteStr ) : null;
        String remarks = params.getOrDefault( "remarks", null );
        String creator = params.getOrDefault( "creator", null );

        Dictionary dictionary = new Dictionary();
        dictionary.setId( CommonsUtil.getUUID() );
        dictionary.setCode( code );
        dictionary.setpCode( pCode );
        dictionary.setOrderNo( orderNo );
        dictionary.setName( name );
        dictionary.setIsPermitDelete( isPermitDelete );
        dictionary.setRemarks( remarks );
        dictionary.setCreateTime( new Date(  ) );
        dictionary.setCreator( creator );
        return this.dictionaryRepository.save( dictionary );
    }
}
