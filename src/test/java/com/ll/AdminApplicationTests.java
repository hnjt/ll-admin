package com.ll;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class AdminApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("ok!");
        String uuid = UUID.randomUUID().toString().replace( "-", "" );
        System.out.println(uuid);
        System.out.println(uuid.length());
    }
}
