/*
package com.ll;

import com.ll.admin.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class UserTests {

    @Test
    void contextLoads() {
        System.out.println("ok!");
    }

    @Autowired
    private UserMapper userMapper;

    @Test
    void loginAndUsers() {
        List<Map<String,Object>> allUser = userMapper.findAllUser();
        allUser.forEach( System.out::println );
    }

    @Test
    void getUser() {
        Map<String, Object> user = userMapper.getUser("9C1E37A2366E3441B1947F6364541212");
        System.out.println(user);
    }

}
*/
