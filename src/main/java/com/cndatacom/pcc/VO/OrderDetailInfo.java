package com.cndatacom.pcc.VO;

import com.cndatacom.pcc.domain.Orderdetail;
import com.cndatacom.pcc.domain.Student;

public class OrderDetailInfo {
	private Orderdetail orderDetail;
	private Student student;
	public Orderdetail getOrderDetail() {
		return orderDetail;
	}
	public void setOrderDetail(Orderdetail orderDetail) {
		this.orderDetail = orderDetail;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}

}
