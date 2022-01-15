package com.mildlamb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
@SuppressWarnings("all")
public class DispatchService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void dispatch(String orderId) throws Exception{
        System.out.println("-----------------派送记录被保存数据库-----------------");
        // 定义保存订单的sql
        String sqlString = "insert into my_dispather(order_id,dispatch_id,status,order_content,user_id) values(?,?,?,?,?)";
        // 添加运单记录
        int count = jdbcTemplate.update(sqlString,orderId, UUID.randomUUID().toString(),0,"有一个订单","1000001");
        if (count != 1){
            throw new Exception("添加运单错误");
        }
    }
}
