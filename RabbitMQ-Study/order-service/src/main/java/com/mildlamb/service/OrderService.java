package com.mildlamb.service;

import com.mildlamb.dao.OrderDataBaseService;
import com.mildlamb.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderDataBaseService orderDataBaseService;

    @Transactional(rollbackFor = Exception.class)
    public void createOrder(Order orderInfo) throws Exception{
        // 将订单插入订单数据库
        orderDataBaseService.saveOrder(orderInfo);

        // 通过Http接口发送订单信息到运单系统
        String result = dispatchHttpApi(orderInfo.getOrderId());
        if (!"success".equals(result)){
            throw new Exception("订单派发失败");
        }
    }

    private String dispatchHttpApi(String orderId){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        // 链接超时 > 3秒
        factory.setConnectTimeout(3000);
        // 处理超时 > 2秒
        factory.setReadTimeout(2000);
        // 发送http请求
        String url = "http://localhost:9000/dispatch/order/" + orderId;
        RestTemplate restTemplate = new RestTemplate(factory);
        String result = restTemplate.getForObject(url,String.class);
        System.out.println("============远程方法被调用=============");
        System.out.println("url:" + url);
        return result;
    }
}
