package com.cndatacom.pcc.service;

import com.cndatacom.pcc.VO.AliPayInfoVO;
import com.cndatacom.pcc.VO.AliPayResultVO;

public interface AliPayService {

	AliPayInfoVO getQRCode(Long orderId);

	AliPayResultVO getOrderStatus(Long orderId);

}
