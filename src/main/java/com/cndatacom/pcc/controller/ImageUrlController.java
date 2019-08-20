package com.cndatacom.pcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cndatacom.pcc.domain.Advertisment;
import com.cndatacom.pcc.domain.Complaints;
import com.cndatacom.pcc.domain.Drivers;
import com.cndatacom.pcc.domain.Student;
import com.cndatacom.pcc.service.AdvertismentService;
import com.cndatacom.pcc.service.ComplaintService;
import com.cndatacom.pcc.service.DriverService;
import com.cndatacom.pcc.service.StudentService;

@RestController
public class ImageUrlController {
	@Autowired
	private ComplaintService complaintService;
	@Autowired
	private DriverService driverService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private AdvertismentService adsService;
	
	@Value("${server.url}")
	private String urlHead;
	
	@RequestMapping(value="/change/imageUrl1")
	public String changeImageUrl1() {
		List<Student> stu_listt = studentService.getAllStudent();
		int num=0;
		for (Student student : stu_listt) {
			String str=student.getStuImage();
			if(str!=null) {
				int second=str.indexOf(":",5);
				String url_foot = str.substring(second);
				String imageUrl=urlHead+url_foot;
				student.setStuImage(imageUrl);
				int n=studentService.updateStudent(student);
				num=num+n;
			}else {
				num=num+1;
			}
		}
		if(num==stu_listt.size()) {
			return "成功了";
		}
		return "失败了";
	}
	
	@RequestMapping(value="/change/imageUrl2")
	public String changeImageUrl2() {
		List<Drivers> dr_listt = driverService.getAllDriver();
		int num=0;
		for (Drivers driver : dr_listt) {
			String str=driver.getDrLicense();
			int second=str.indexOf(":",5);
			String url_foot = str.substring(second);
			String imageUrl=urlHead+url_foot;
			driver.setDrLicense(imageUrl);
			boolean f=driverService.updateDriverInfo(driver);
			if(f) {
				num++;
			}
		}
		if(num==dr_listt.size()) {
			return "成功了";
		}
		return "失败了";
	}
	
	@RequestMapping(value="/change/imageUrl3")
	public String changeImageUrl3() {
		List<Complaints> com_list = complaintService.getAllComplaints();
		int num=0;
		for (Complaints complaint : com_list) {
			String str=complaint.getMaterial();
			int second=str.indexOf(":",5);
			String url_foot = str.substring(second);
			String imageUrl=urlHead+url_foot;
			complaint.setMaterial(imageUrl);
			boolean f=complaintService.updateComplaint(complaint);
			if(f) {
				num++;
			}
		}
		if(num==com_list.size()) {
			return "成功了";
		}
		return "失败了";
	}
	@RequestMapping(value="/change/imageUrl4")
	public String changeImageUrl4() {
		List<Advertisment> ads_list = adsService.getAds();
		int num=0;
		for (Advertisment ads : ads_list) {
			String str=ads.getAdverimage();
			int second=str.indexOf(":",5);
			String url_foot = str.substring(second);
			String imageUrl=urlHead+url_foot;
			ads.setAdverimage(imageUrl);
			boolean f=adsService.updateAds(ads);
			if(f) {
				num++;
			}
		}
		if(num==ads_list.size()) {
			return "成功了";
		}
		return "失败了";
	}
		
}
