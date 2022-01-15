package com.mildlamb.service;

import com.mildlamb.dao.OrderDataBaseService;
import com.mildlamb.mq.OrderMQService;
import com.mildlamb.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQOrderService {

    @Autowired
    private OrderDataBaseService orderDataBaseService;
    @Autowired
    private OrderMQService orderMQService;

    // 创建订单
    public void createOrder(Order orderInfo) throws Exception{
        // 订单信息插入订单系统，订单数据库事务
        orderDataBaseService.saveOrder(orderInfo);
        // 通过http接口发送订单信息到运单系统
        orderMQService.sendMessage(orderInfo);
    }

}
