//package com.ll;
//
//import com.commons.CommonsUtil;
//import com.ll.admin.dao.DictionaryRepository;
//import com.ll.admin.domain.Dictionary;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Date;
//
//
//@SpringBootTest
//class DictionaryTests {
//
//    @Autowired
//    private DictionaryRepository dictionaryRepository;
//
//    @Test
//    void saveDictionary() {
//        Dictionary dictionary = new Dictionary();
//        String uuid = CommonsUtil.getUUID();
//        dictionary.setId( uuid );
//        dictionary.setOrderNo( 1 );
//        dictionary.setIsPermitDelete( 0 );
//        dictionary.setCode( "fun" );
//        dictionary.setName( "权限操作" );
//        dictionary.setRemarks( "操作权限数据字典" );
//        Date newDate = new Date();
//        dictionary.setCreateTime( newDate );
//        this.dictionaryRepository.save( dictionary );
//
//        Dictionary d1 = new Dictionary();
//        d1.setId( CommonsUtil.getUUID() );
//        d1.setPid( uuid );
//        d1.setOrderNo( 1 );
//        d1.setIsPermitDelete( 0 );
//        d1.setCode( "fun_add" );
//        d1.setName( "新增" );
//        d1.setRemarks( "新增权限操作" );
//        d1.setCreateTime(newDate);
//        this.dictionaryRepository.save( d1 );
//
//        Dictionary d3 = new Dictionary();
//        d3.setId( CommonsUtil.getUUID() );
//        d3.setPid( uuid );
//        d3.setOrderNo( 2 );
//        d3.setIsPermitDelete( 0 );
//        d3.setCode( "fun_del" );
//        d3.setName( "删除" );
//        d3.setRemarks( "删除权限操作" );
//        d3.setCreateTime(newDate);
//        this.dictionaryRepository.save( d3 );
//
//        Dictionary d2 = new Dictionary();
//        d2.setId( CommonsUtil.getUUID() );
//        d2.setPid( uuid );
//        d2.setOrderNo( 3 );
//        d2.setIsPermitDelete( 0 );
//        d2.setCode( "fun_edit" );
//        d2.setName( "编辑" );
//        d2.setRemarks( "编辑权限操作" );
//        d2.setCreateTime(newDate);
//        this.dictionaryRepository.save( d2 );
//
//        Dictionary d4 = new Dictionary();
//        d4.setId( CommonsUtil.getUUID() );
//        d4.setPid( uuid );
//        d4.setOrderNo( 4 );
//        d4.setIsPermitDelete( 0 );
//        d4.setCode( "fun_que" );
//        d4.setName( "查询" );
//        d4.setRemarks( "查询权限操作" );
//        d4.setCreateTime(newDate);
//        this.dictionaryRepository.save( d4 );
//    }
//}
