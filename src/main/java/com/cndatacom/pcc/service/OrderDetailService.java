package com.cndatacom.pcc.service;

import java.util.List;

import com.cndatacom.pcc.VO.OrderInfo;

public interface OrderDetailService {
	// 查询订单详情
	OrderInfo getOrderDetailInfo(Long oid);

	// 获取订单里学生的id,司机接单时用到
	List<String> getOrderStuid(Long oid);

	// 学生查询自己待接单
	List<OrderInfo> waitTakeOrder(List<Long> oidList);

	int delOrderdetailById(Long oid, String followId);
	

}
