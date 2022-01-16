package com.mildlamb;

import com.mildlamb.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootRabbitmqOrderProducerApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    void fanout_test() {
        orderService.makeOrder("1","1",10);
    }

    @Test
    void direct_test(){
        orderService.makeOrder_direct("1","1",11);
    }

    @Test
    void topic_test(){
        orderService.makeOrder_topic("1","1",11);
    }

    @Test
    void ttl_test(){
        orderService.makeOrder_ttl("1","1",11);
    }

    @Test
    void ttlMessage_test(){
        orderService.makeOrder_ttlMessage("1","1",11);
    }

    @Test
    void ttl_dead_test(){
        orderService.makeOrder_ttl("1","1",11);
    }


}
