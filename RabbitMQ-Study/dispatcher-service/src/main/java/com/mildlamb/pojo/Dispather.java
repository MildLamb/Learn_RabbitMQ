package com.mildlamb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dispather {
    private String dispatchId;
    private String orderId;
    private Integer status;
    private String orderContent;
    private Date createTime;
    private String userId;
}
