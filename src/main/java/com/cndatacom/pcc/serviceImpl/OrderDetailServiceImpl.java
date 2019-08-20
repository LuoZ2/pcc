package com.cndatacom.pcc.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cndatacom.pcc.VO.OrderDetailInfo;
import com.cndatacom.pcc.VO.OrderInfo;
import com.cndatacom.pcc.dao.DriversMapper;
import com.cndatacom.pcc.dao.OrderdetailMapper;
import com.cndatacom.pcc.dao.OrdersMapper;
import com.cndatacom.pcc.dao.StudentMapper;
import com.cndatacom.pcc.domain.Drivers;
import com.cndatacom.pcc.domain.Orderdetail;
import com.cndatacom.pcc.domain.Orders;
import com.cndatacom.pcc.domain.Student;
import com.cndatacom.pcc.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	private OrdersMapper orderMapper;
	@Autowired
	private OrderdetailMapper orderDetailMapper;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private DriversMapper driverMapper;

	@Override
	public OrderInfo getOrderDetailInfo(Long oid) {
		OrderInfo oi = new OrderInfo();
		Orders order = orderMapper.selectById(oid);
		oi.setOrder(order);
		Student studnet = studentMapper.selectById(order.getOid());
		oi.setStu(studnet);
		Drivers driver = driverMapper.selectById(order.getDrId());
		oi.setDriver(driver);
		QueryWrapper<Orderdetail> wrapper = new QueryWrapper<Orderdetail>();
		wrapper.eq("oid", oid);
		List<OrderDetailInfo> odi_list = new ArrayList<>();
		List<Orderdetail> list = orderDetailMapper.selectList(wrapper);
		for (Orderdetail orderdetail : list) {
			OrderDetailInfo odi = new OrderDetailInfo();
			odi.setOrderDetail(orderdetail);
			Student stu = studentMapper.selectById(orderdetail.getFollowId());
			odi.setStudent(stu);
			odi_list.add(odi);
		}
		oi.setDetail_list(odi_list);
		return oi;
	}

	@Override
	public List<String> getOrderStuid(Long oid) {
		QueryWrapper<Orderdetail> wrapper = new QueryWrapper<Orderdetail>();
		wrapper.eq("oid", oid);
		List<String> stuId_list = new ArrayList<>();
		List<Orderdetail> list = orderDetailMapper.selectList(wrapper);
		for (Orderdetail orderdetail : list) {
			stuId_list.add(orderdetail.getFollowId());
		}
		return stuId_list;
	}

	@Override
	public List<OrderInfo> waitTakeOrder(List<Long> oidList) {
		QueryWrapper<Orders> wrapper = new QueryWrapper<Orders>();
		wrapper.eq("ostatus", 2);
		wrapper.in("oid", oidList);
		List<OrderInfo> oiList = new ArrayList<OrderInfo>();
		List<Orders> orderList = orderMapper.selectList(wrapper);
		for (Orders order : orderList) {
			oiList.add(getOrderDetailInfo(order.getOid()));
		}
		return oiList;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int delOrderdetailById(Long oid, String followId) {
		Orders order = orderMapper.selectById(oid);
		//num减1
		order.setPeoplenum(order.getPeoplenum()-1);
		orderMapper.updateById(order);
		QueryWrapper<Orderdetail> wrapper = new QueryWrapper<>();
		wrapper.eq("follow_id", followId);
		wrapper.eq("oid", oid);
		int  row = orderDetailMapper.delete(wrapper);
		return row;
	}

}
