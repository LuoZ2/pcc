package com.cndatacom.pcc.VO;

import java.util.List;

import com.cndatacom.pcc.domain.Drivers;
import com.cndatacom.pcc.domain.Orders;
import com.cndatacom.pcc.domain.Student;

public class OrderInfo {
	private Orders order;
	private Student stu;
	public Drivers getDriver() {
		return driver;
	}
	public void setDriver(Drivers driver) {
		this.driver = driver;
	}
	private Drivers driver;
	private List<OrderDetailInfo> detail_list;
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	public List<OrderDetailInfo> getDetail_list() {
		return detail_list;
	}
	public void setDetail_list(List<OrderDetailInfo> detail_list) {
		this.detail_list = detail_list;
	}
	public Student getStu() {
		return stu;
	}
	public void setStu(Student stu) {
		this.stu = stu;
	}
	
	
}
