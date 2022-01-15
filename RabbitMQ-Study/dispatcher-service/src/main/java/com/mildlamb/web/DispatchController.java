package com.mildlamb.web;

import com.mildlamb.service.DispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dispatch")
public class DispatchController {
    @Autowired
    public DispatchService dispatchService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    // 添加订单后，添加调度信息
    @GetMapping("/order/{orderId}")
    public String lock(@PathVariable("orderId") String orderId) throws Exception{
//        if (orderId.equals("1000001")){
//            Thread.sleep(4000L);
//        }
        dispatchService.dispatch(orderId);
        System.out.println("success");
        return "success";
    }
}
