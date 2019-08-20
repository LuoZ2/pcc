package com.cndatacom.pcc.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cndatacom.pcc.dao.DrivercontactMapper;
import com.cndatacom.pcc.domain.Drivercontact;
import com.cndatacom.pcc.service.DrcontactService;
@Service
public class DrcontactServiceImpl implements DrcontactService{

	@Autowired
	private DrivercontactMapper drivercontactMapper;
	
	@Override
	public boolean addContactDr(List<String> stuId_list, String drId) {
		int num=0;
		for (String stuId : stuId_list) {
			Drivercontact dc=new  Drivercontact();
			dc.setStuId(stuId);
			dc.setDrId(drId);
			drivercontactMapper.insert(dc);
			num++;
		}
		if(num==stuId_list.size()) {
			return true;
		}
		return false;
	}

	
	@Override
	public boolean deleteContactDr(List<String> stuId_list, String drId) {
		int num=0;
		QueryWrapper<Drivercontact> wrapper=new QueryWrapper<Drivercontact>();
		wrapper.eq("dr_id", drId);
		for (String stuId : stuId_list) {
			wrapper.eq("stu_id", stuId);
			drivercontactMapper.delete(wrapper);
			num++;
		}
		if(num==stuId_list.size()) {
			return true;
		}
		return false;
	}
	
	

}
