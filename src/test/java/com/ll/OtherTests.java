//package com.ll;
//
//import com.ll.admin.mapper.OtherMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//import java.util.Map;
//
//@SpringBootTest
//class OtherTests {
//
//    @Test
//    void contextLoads() {
//        System.out.println("ok!");
//    }
//
//    @Autowired
//    private OtherMapper otherMapper;
//
//    @Test
//    void cityTests() {
//        List<Map<String, Object>> allCitys = otherMapper.findAllCitys( 12 );
//        allCitys.forEach( System.out::println );
//
//        System.out.println("-------------------");
//        List<Map<String, Object>> allNations = otherMapper.findAllNations( 11 );
//        allNations.forEach( System.out::println );
//    }
//
//}
