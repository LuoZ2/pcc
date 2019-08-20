package com.cndatacom.pcc.service;

import java.util.List;

import com.cndatacom.pcc.domain.Drivers;

public interface DriverService {

	//根据司机id获取到司机
	Drivers getDriverByid(String drId);
	
	//根据司机手机号获取司机
	Drivers getDriverBytel(String drTel);
	
	//更新司机信息
	boolean updateDriverInfo(Drivers driver);
	
	//司机注册
	boolean driverRegister(Drivers driver);
	
	//司机登陆
	Drivers driverLogin(String drTel,String drPassword);
	
	//获取所有的司机
	List<Drivers> getAllDriver();

}
