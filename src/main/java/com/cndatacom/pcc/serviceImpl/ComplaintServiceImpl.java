package com.cndatacom.pcc.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndatacom.pcc.dao.ComplaintsMapper;
import com.cndatacom.pcc.domain.Complaints;
import com.cndatacom.pcc.service.ComplaintService;
@Service
public class ComplaintServiceImpl implements ComplaintService{

	@Autowired
	private ComplaintsMapper complaintMapper;
	
	@Override
	public int addComplaint(Complaints complaint) {
		// TODO Auto-generated method stub
		long id=System.currentTimeMillis();
		complaint.setComId(id);
		complaint.setCreated(new Date());
		complaint.setStatus(0);
		return complaintMapper.insert(complaint);
	}
	
	@Override
	public List<Complaints> getAllComplaints() {
		// TODO Auto-generated method stub
		return complaintMapper.selectList(null);
	}

	@Override
	public boolean updateComplaint(Complaints complaint) {
		// TODO Auto-generated method stub
		if(complaintMapper.updateById(complaint)>0) {
			return true;
		}
		return false;
	}


}
