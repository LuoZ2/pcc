package com.cndatacom.pcc.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndatacom.pcc.dao.OrderdetailMapper;
import com.cndatacom.pcc.dao.OrdersMapper;
import com.cndatacom.pcc.domain.Orderdetail;
import com.cndatacom.pcc.domain.Orders;
import com.cndatacom.pcc.service.AssistOrderService;
@Service
public class AssistOrderServiceImpl implements AssistOrderService{

	@Autowired
	private OrdersMapper orderMapper;
	@Autowired
	private OrderdetailMapper orderDetailMapper;
	
	@Override
	public boolean addOrder(Orders order) {
		// TODO Auto-generated method stub
		if(orderMapper.insert(order)>0) {
			return true;
		}
		return false;
	}

	@Override
	public int addOrderDetail(Orderdetail orderDetail) {
		// TODO Auto-generated method stub
		return orderDetailMapper.insert(orderDetail);
	}

}
