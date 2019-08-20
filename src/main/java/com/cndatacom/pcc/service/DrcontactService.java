package com.cndatacom.pcc.service;

import java.util.List;

public interface DrcontactService {
	//司机接单时，添加联系
	boolean addContactDr(List<String> stuId_list,String drId);
	//完成订单时，删除联系
	boolean deleteContactDr(List<String> stuId_list,String drId);
}
