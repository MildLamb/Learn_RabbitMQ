package com.mildlamb;

import com.mildlamb.service.DispatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FenbushiApplicationTests {

    @Autowired
    private DispatchService dispatchService;

    @Test
    void contextLoads() throws Exception {
//        dispatchService.dispatch("test");
    }

}
