package com.ll;

import com.ll.admin.feign.FeignService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class AdminApplicationTests {

    private FeignService feignService;

    @Autowired
    public void setFeignService(FeignService feignService) {
        this.feignService = feignService;
    }

    @Test
    void contextLoads() {
        System.out.println("ok!");
        String allDictionary = feignService.findAllDictionary();
        System.out.println(allDictionary);
        System.out.println("------------------------------");
        String job = feignService.getJob( "7035580d45304068b0407dbf67ece1a6" );
        System.out.println(job);
    }
}
