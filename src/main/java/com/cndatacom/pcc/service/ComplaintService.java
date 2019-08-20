package com.cndatacom.pcc.service;

import java.util.List;

import com.cndatacom.pcc.domain.Complaints;

public interface ComplaintService {
	//发起投诉
	int addComplaint(Complaints complaint);
	//获取所有投诉
	List<Complaints> getAllComplaints();
	//修改投诉信息（图片）
	boolean updateComplaint(Complaints complaint);

}
