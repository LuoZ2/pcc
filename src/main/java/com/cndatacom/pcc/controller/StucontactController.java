package com.cndatacom.pcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cndatacom.pcc.VO.ResponseVO;
import com.cndatacom.pcc.domain.Stucontact;
import com.cndatacom.pcc.domain.Student;
import com.cndatacom.pcc.domain.Travels;
import com.cndatacom.pcc.service.StucontactService;
import com.cndatacom.pcc.service.StudentService;
import com.cndatacom.pcc.service.TravelsService;

@RestController
public class StucontactController {
	
	@Autowired
	private StucontactService stucontactService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private TravelsService travelService;
	
	/**
	 * 根据自己的id查询那些人可以看到自己的保密信息
	 * @param myId
	 * @param name 模糊搜索
	 */
	@RequestMapping(value="/stucontact/allByid")
	public List<Student> findAllContact(String myId,String name){
		//获取到所有有联系的学生id
		List<String> stuId_list = stucontactService.findAllContact(myId,1);
		if(stuId_list.size()==0) {
			return null;
		}else {
			//再获取到学生信息
			List<Student> stuList = studentService.getContactStudent(stuId_list,name);
			return stuList;
		}
	}
	/**
	 * 删除好友
	 * @param friendId
	 * @return
	 */
	@RequestMapping(value="/stucontact/delByid")
	public ResponseVO delFriend(String myId ,String friendId){
		Integer isSuccse = stucontactService.delFriendById(myId, friendId);
		if(isSuccse !=null && isSuccse>0) {
			return ResponseVO.success("删除成功");
		}else {
			return ResponseVO.success("删除失败");
		}
	}	
	/**
	 * 
	 * @param myId
	 * @param name 模糊搜索
	 * @return
	 * 获取所有向我提出申请的人
	 */
	@RequestMapping(value="/stucontact/getrequest")
	public List<Student> findRequestContact(String myId,String name){
		//获取到所有有联系的学生id
		List<String> stuId_list = stucontactService.findRequestContact(myId,0);
		if(stuId_list.size()==0) {
			return null;
		}else {
			//再获取到学生信息
			List<Student> stuList = studentService.getContactStudent(stuId_list,name);
			return stuList;
		}
	}
	
	/**
	 * 
	 * @param myId
	 * @param formId提出申请的人的id
	 * @return
	 * 同意申请
	 */
	@RequestMapping(value="/stucontact/yesrequest")
	public ResponseVO yesrequest(String myId,String fromId) {
		boolean f=stucontactService.yesrequest(myId, fromId);
		if(f) {
			return ResponseVO.success("已经同意");
		}
		return ResponseVO.serviceFail("操作失败");
	}
	
	/**
	 * 
	 * @param myId
	 * @param fromId
	 * @return
	 * 拒绝申请
	 */
	@RequestMapping(value="/stucontact/norequest")
	public ResponseVO norequest(String myId,String fromId) {
		boolean f=stucontactService.norequest(myId, fromId);
		if(f) {
			return ResponseVO.success("操作成功");
		}
		return ResponseVO.serviceFail("操作失败");
	}
	
	/**
	 * 点击获取联系方式
	 * @param myId
	 * @param travelid
	 * @return
	 */
	@RequestMapping(value="/stucontact/request")
	public ResponseVO addrequest(String myId,long travelid) {
		Travels travel = travelService.getTravel(travelid);
		String toId=travel.getInitId();
		Stucontact contact=new Stucontact();
		contact.setContactid(System.currentTimeMillis());
		contact.setFromId(myId);
		contact.setToId(toId);
		contact.setStatus(0);
		if(stucontactService.myrequest(contact)) {
			return ResponseVO.success("已经申请成功，等待别人通过！");
		}
		return ResponseVO.serviceFail("等待通过！");
	}
	
	
	/**
	 * 点击头像获取联系方式
	 * @param myId
	 * @param stuId
	 * @return
	 */
	@RequestMapping(value="/stucontact/request1")
	public ResponseVO addrequest(String myId,String stuId) {
		String toId=stuId;
		Stucontact contact=new Stucontact();
		contact.setContactid(System.currentTimeMillis());
		contact.setFromId(myId);
		contact.setToId(toId);
		contact.setStatus(0);
		if(stucontactService.myrequest(contact)) {
			return ResponseVO.success("已经申请成功，等待别人通过！");
		}
		return ResponseVO.serviceFail("等待通过！");
	}
	
	
	
	
	
	
}
