package com.cndatacom.pcc.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cndatacom.pcc.dao.DriversMapper;
import com.cndatacom.pcc.domain.Complaints;
import com.cndatacom.pcc.domain.Drivers;
import com.cndatacom.pcc.service.DriverService;
import com.cndatacom.pcc.util.MD5Util;
@Service
public class DriverServiceImpl implements DriverService{

	@Autowired
	private DriversMapper driverMapper;
	
	@Override
	public Drivers getDriverByid(String drId) {
		// TODO Auto-generated method stub
		QueryWrapper<Drivers> wrapper=new QueryWrapper<Drivers>();
		wrapper.eq("dr_id", drId);
		Drivers driver = driverMapper.selectOne(wrapper);
		return driver;
	}

	@Override
	public boolean updateDriverInfo(Drivers driver) {
		// TODO Auto-generated method stub
		int num=driverMapper.updateById(driver);
		if(num>0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean driverRegister(Drivers driver) {
		// TODO Auto-generated method stub
		int num=driverMapper.insert(driver);
		if(num>0) {
			return true;
		}
		return false;
	}

	@Override
	public Drivers driverLogin(String drTel, String drPassword) {
		// TODO Auto-generated method stub
		String drPasswordMD5 = MD5Util.encode(drPassword);
		QueryWrapper<Drivers> wrapper=new QueryWrapper<>();
		wrapper.eq("dr_tel", drTel);
		wrapper.eq("dr_password", drPasswordMD5);
		Drivers driver = driverMapper.selectOne(wrapper);
		if(driver!=null) {
			return driver;
		}
		return null;
	}

	@Override
	public List<Drivers> getAllDriver() {
		// TODO Auto-generated method stub
		return driverMapper.selectList(null);
	}

	@Override
	public Drivers getDriverBytel(String drTel) {
		QueryWrapper<Drivers> wrapper=new QueryWrapper<Drivers>();
		wrapper.eq("dr_tel", drTel);
		Drivers driver = driverMapper.selectOne(wrapper);
		return driver;
	}

}
