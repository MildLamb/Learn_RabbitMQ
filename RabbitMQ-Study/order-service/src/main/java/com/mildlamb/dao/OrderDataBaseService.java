package com.mildlamb.dao;

import com.mildlamb.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("all")
public class OrderDataBaseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 保存订单信息
    public void saveOrder(Order orderInfo) throws Exception{
        // 保存订单的sql语句
        String sqlString = "insert into my_order(order_id,user_id,order_content) values(?,?,?)";

        // 添加记录
        int count = jdbcTemplate.update(sqlString,orderInfo.getOrderId(),orderInfo.getUserId(),orderInfo.getOrderContent());

        if (count != 1){
            throw new Exception("订单创建失败");
        }

        // 保存一份冗余数据
        saveLocalMessage(orderInfo);
    }

    public void saveLocalMessage(Order order) throws Exception{
        String sqlString = "insert into ksd_order_message(order_id,order_content,status,unique_id) values(?,?,?,?)";
        int count = jdbcTemplate.update(sqlString,order.getOrderId(),order.getOrderContent(),0,1);
        if (count != 1){
            throw new Exception("冗余数据备份异常");
        }
    }
}
