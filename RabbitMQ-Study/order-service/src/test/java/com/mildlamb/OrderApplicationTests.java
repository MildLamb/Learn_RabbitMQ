package com.mildlamb;

import com.mildlamb.pojo.Order;
import com.mildlamb.service.MQOrderService;
import com.mildlamb.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class OrderApplicationTests {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MQOrderService mqOrderService;

    @Test
    public void orderCreated() throws Exception {
        // 订单生成
        String orderId = "1000001";
        Order orderInfo = new Order();
        orderInfo.setOrderId(orderId);
        orderInfo.setUserId("mildlamb");
        orderInfo.setOrderContent("一朵金色绽灵花");
//        orderInfo.setCreateTime(new Date());
        orderService.createOrder(orderInfo);
        System.out.println("订单创建成功");
    }

    @Test
    public void orderCreatedMQ() throws Exception {
        // 订单生成
        String orderId = "1000002";
        Order orderInfo = new Order();
        orderInfo.setOrderId(orderId);
        orderInfo.setUserId("wildwolf");
        orderInfo.setOrderContent("一个青花瓷");
        mqOrderService.createOrder(orderInfo);
        System.out.println("订单创建成功");
    }

}
