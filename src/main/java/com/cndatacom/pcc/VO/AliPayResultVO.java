package com.cndatacom.pcc.VO;

import java.io.Serializable;

import lombok.Data;

@Data
public class AliPayResultVO implements Serializable {

    private Long orderId;
    private Integer orderStatus;
    private String orderMsg;

}
