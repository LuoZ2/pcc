package com.cndatacom.pcc.service;

import java.util.List;

import com.cndatacom.pcc.domain.Traveldetail;

public interface TraveldetailService {
	//检查该学生是否已经加入了当前队伍
	boolean checkJoinTeam(Long travelid,String myId);
	//添加traveldetail(申请加入队伍)
	public int addTraveldetail(Traveldetail traveldetail);
	//更具travelid查询行程详情
	public List<Traveldetail> getTraveldetails(Long travelid);
	//查找traveldetail记录
	Traveldetail getBytravelidAndStuid(long travelid,String stu_id);
	//不同意加入队伍
	boolean deleteTraveldetail(Traveldetail traveldetail);
	//更新信息
	boolean updateTraveldetail(Traveldetail trade);
}
