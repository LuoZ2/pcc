package com.cndatacom.pcc.service;

import com.cndatacom.pcc.domain.Orderdetail;
import com.cndatacom.pcc.domain.Orders;

public interface AssistOrderService {
	
	//添加订单
	boolean addOrder(Orders order);
	
	//添加订单项
	int addOrderDetail(Orderdetail orderDetail);
	
}
