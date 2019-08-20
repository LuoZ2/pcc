package com.cndatacom.pcc.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cndatacom.pcc.VO.DriverVO;
import com.cndatacom.pcc.VO.ResponseVO;
import com.cndatacom.pcc.domain.Drivers;
import com.cndatacom.pcc.domain.Student;
import com.cndatacom.pcc.service.DriverService;
import com.cndatacom.pcc.util.MD5Util;
import com.cndatacom.pcc.util.UUIDUtils;

@RestController
public class DriverController {

	@Autowired
	private DriverService driverService;
	@Autowired
	private StringRedisTemplate redis;

	@Value("${server.url}")
	private String urlHead;
	
	// 司机登陆
	@RequestMapping(value = "/driver/login")
	public ResponseVO dirverLogin(String drTel, String drPassword) {
		if(StringUtils.isBlank(drTel)||StringUtils.isBlank(drPassword)) {
			return ResponseVO.appFail("用户名或密码为空，请再次输入！");
		}
		Drivers driver=driverService.driverLogin(drTel, drPassword);
		if(driver==null) {
			return ResponseVO.appFail("用户名或密码错误，请再次输入！");
		}else {
			if(driver.getDrStatus()==0) {
				return ResponseVO.appFail("已经被封号，请联系管理员解封！");
			}else if(driver.getDrStatus()==3) {
				return ResponseVO.appFail("注册信息尚未审核，请等待...");
			}else if(driver.getDrStatus()==2) {
				return ResponseVO.appFail("账号处于被投诉状态，请等待管理员审核...");
			}else {
				driver.setDrPassword("");
				DriverVO driverVO=setStuRedisSessionToken(driver);
				return ResponseVO.success(driverVO);
			}
		}
	}

	// 司机注册
	@RequestMapping(value = "/driver/register")
	public ResponseVO driverRegister(@RequestBody Drivers driver) {
		String MDpassword=MD5Util.encode(driver.getDrPassword());
		driver.setDrPassword(MDpassword);
		driver.setCreated(new Date());
		driver.setDrNum(0);
		driver.setDrStatus(3);
		boolean isSuccess = driverService.driverRegister(driver);
		if (isSuccess) {
			String drId = driver.getDrId();
			return ResponseVO.success(drId);// 注册成功
		} else {
			return ResponseVO.serviceFail("注册失败");
		}
	}

	// 上传驾照
	@RequestMapping(value = "/driver/register1", headers = "content-type=multipart/form-data")
	public ResponseVO driverRegister1(MultipartFile uploadLicense, String drTel)
			throws IllegalStateException, IOException {
		String url = null;
		System.out.println("999:"+uploadLicense.getOriginalFilename());
		if (!uploadLicense.isEmpty()) {
			String originFileName = uploadLicense.getOriginalFilename();
			String newName = UUIDUtils.createUUID2() + originFileName.substring(originFileName.indexOf("."));
			url = "D:\\学习\\xinghuo\\images\\licenseImages\\" + newName;
			// 上传图片
			uploadLicense.transferTo(new File(url));
			// 然后把映射路径设置进去
			url = urlHead+":8081/uploadImages/" + newName;
		}
		Drivers driver = driverService.getDriverBytel(drTel);
		driver.setDrLicense(url);
		driverService.updateDriverInfo(driver);
		return ResponseVO.success(driver);
	}

	/*
	 * 设置sessionid，存入redis
	 */
	public DriverVO setStuRedisSessionToken(Drivers driver) {
		String uniqueToken = UUID.randomUUID().toString();
		redis.opsForValue().set("driver-redis-session:" + driver.getDrId(), uniqueToken, 24 * 60 * 60 * 30);
		DriverVO driverVO = new DriverVO();
		BeanUtils.copyProperties(driver, driverVO);
		driverVO.setUserToken(uniqueToken);
		return driverVO;
	}
	/*
	 * 司机注销
	 */
	@RequestMapping(value="/driver/logout")
	public ResponseVO stuRegister(String myId) {

		Boolean delete = redis.delete("driver-redis-session:" + myId);
        if(delete){
            return  ResponseVO.success("注销成功！！");//注销成功
        }else {
            return ResponseVO.serviceFail("注销失败！！");
        }
		
	}
	
	/*
	 * 司机修改密码
	 */
	@RequestMapping(value="/driver/updatePwd")
	public ResponseVO updatePwd(String myId,String oldPwd,String newPwd) {
		Drivers driver = driverService.getDriverByid(myId);
		String oldPwdMD5 = MD5Util.encode(oldPwd);
		if(!driver.getDrPassword().equals(oldPwdMD5)) {
			return ResponseVO.serviceFail("原密码错误");
		}else {
			driver.setDrPassword(MD5Util.encode(newPwd));
			boolean isSuccess =driverService.updateDriverInfo(driver);
			if(isSuccess) {
				return ResponseVO.success(driver);
			}else {
				return ResponseVO.serviceFail("修改失败");
			}	
		}
		
		
	}
}
