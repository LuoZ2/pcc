package com.cndatacom.pcc.service;

import com.cndatacom.pcc.VO.PagedResult;
import com.cndatacom.pcc.domain.Travels;

public interface TravelsService {

	
	//发布行程
	public int addTravel(Travels travels);
	
	//查询行程,分页
	public PagedResult getTravels(int nums, int nowPage,String originAddr,String destination,String stuSex,Integer actualNum );

	//根据travelid查询行程
	public Travels getTravel(Long travelid);
	
	//我的队伍
	PagedResult getMyTravels(int nums, int nowPage,String originAddr,String destination,String stuSex,
			Integer actualNum,String myId );

	public boolean updateTravle(Travels travel);
	
	//修改行程状态
	public int updateTravleStatus(Long travelid,Integer status);
}
