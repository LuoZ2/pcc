package com.cndatacom.pcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cndatacom.pcc.VO.OrderInfo;
import com.cndatacom.pcc.VO.ResponseVO;
import com.cndatacom.pcc.service.OrderDetailService;

@RestController
public class OrderDetailController {

	@Autowired
	private OrderDetailService orderDetailService;

	@RequestMapping(value = "/getOrderInfoByoid")
	public OrderInfo getOrderInfoByoid(Long oid) {
		OrderInfo oi = orderDetailService.getOrderDetailInfo(oid);
		return oi;
	}

	@RequestMapping(value = "/delOrderdetail")
	public ResponseVO delOrderdetail(Long oid, String followId) {
		int row = orderDetailService.delOrderdetailById(oid, followId);
		if (row > 0) {
			return ResponseVO.success("删除队员行程成功");
		} else {
			return ResponseVO.serviceFail("业务异常，请稍后再试");
		}
	}

}
