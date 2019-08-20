package com.cndatacom.pcc.VO;

import java.io.Serializable;

import lombok.Data;
@Data
public class AliPayInfoVO implements Serializable{
    private Long orderId;
    private String QRCodeAddress;

}
