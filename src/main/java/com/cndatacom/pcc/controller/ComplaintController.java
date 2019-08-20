package com.cndatacom.pcc.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cndatacom.pcc.VO.ResponseVO;
import com.cndatacom.pcc.domain.Complaints;
import com.cndatacom.pcc.domain.Drivers;
import com.cndatacom.pcc.domain.Student;
import com.cndatacom.pcc.service.ComplaintService;
import com.cndatacom.pcc.service.DriverService;
import com.cndatacom.pcc.service.StudentService;
import com.cndatacom.pcc.util.UUIDUtils;

@RestController
public class ComplaintController {
	
	@Autowired
	private ComplaintService complaintService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private DriverService driverService;
	
	@Value("${server.url}")
	private String urlHead;
	
	@RequestMapping(value="/student/complaint",headers="content-type=multipart/form-data")
	public ResponseVO stuComplaint(MultipartFile uploadImage,String fromId,String toId,int roleid,String notes) throws IllegalStateException, IOException {
		String url=null;
		if(!uploadImage.isEmpty()) {
			String originFileName=uploadImage.getOriginalFilename();
			String newName=UUIDUtils.createUUID2()+originFileName.substring(originFileName.indexOf("."));
			url="D:\\学习\\xinghuo\\images\\complaintImages\\"+newName;
			//上传图片
			uploadImage.transferTo(new File(url));
			url=urlHead+":8081/uploadImages/"+newName;
		}
		Complaints complaint=new Complaints();
		complaint.setFromId(fromId);
		complaint.setNotes(notes);
		complaint.setRoleid(roleid);
		complaint.setToId(toId);
		complaint.setMaterial(url);
		//获取被投诉人的身份0司机1学生
		if(roleid==0) {
			//司机用户
			//是否存在该用户
			Drivers driver = driverService.getDriverBytel(toId);
			System.out.println(toId);
			if(driver!=null) {
				//如果存在，就添加投诉记录
				int num=complaintService.addComplaint(complaint);
				if(num>0) {
					driverService.updateDriverInfo(driver.setDrStatus(2));
					ResponseVO res=ResponseVO.success("投诉成功，等待管理员审核！");
					return res;
				}else {
					ResponseVO res=ResponseVO.serviceFail("投诉失败，请认真核对材料！");
					return res;
				}
			}else {
				//不存在就返回相关信息
				ResponseVO res=ResponseVO.serviceFail("投诉失败，该用户不存在！");
				return res;
			}
		}else {
			//学生用户
			Student stu = studentService.findStuByid(fromId);
			if(stu!=null) {
				//添加投诉记录
				int num=complaintService.addComplaint(complaint);
				if(num>0) {
					studentService.updateStudent(stu.setStuStatus(2));
					ResponseVO res=ResponseVO.success("投诉成功，等待管理员审核！");
					return res;
				}else {
					ResponseVO res=ResponseVO.serviceFail("投诉失败，请认真核对材料！");
					return res;
				}
			}else {
				ResponseVO res=ResponseVO.serviceFail("投诉失败，该用户不存在！");
				return res;
			}
		}
	}

	
	
	
}
