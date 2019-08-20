package com.cndatacom.pcc.service;

import java.util.List;

import com.cndatacom.pcc.domain.Stucontact;

public interface StucontactService {
	//通过发起人id和接收人id查找stucontact
	public boolean getStucontactBytofromId(String id1,String id2);
	//获取所有联系人的id
	List<String> findAllContact(String myId,Integer status);
	//获取向我提出申请的人的id
	List<String> findRequestContact(String myId,Integer status);
	//同意申请
	boolean yesrequest(String myId,String fromId);
	//拒绝申请
	boolean norequest(String myId,String fromId);
	//提出申请
	boolean myrequest(Stucontact contact);
	
	//删除好友
	Integer delFriendById(String myId,String friendId);
}
