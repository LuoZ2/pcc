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
import com.cndatacom.pcc.VO.GetPersonInfo;
import com.cndatacom.pcc.VO.ResponseVO;
import com.cndatacom.pcc.VO.StudentVO;
import com.cndatacom.pcc.domain.Student;
import com.cndatacom.pcc.service.StucontactService;
import com.cndatacom.pcc.service.StudentService;
import com.cndatacom.pcc.util.MD5Util;
import com.cndatacom.pcc.util.UUIDUtils;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author luozhongqian
 * @since 2019-07-26
 */
@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private StringRedisTemplate redis;

	@Autowired
	private StucontactService stucontactService;

	@Value("${server.url}")
	private String urlHead;

	/*
	 * 设置sessionid，存入redis
	 */
	public StudentVO setStuRedisSessionToken(Student stu) {
		String uniqueToken = UUID.randomUUID().toString();
		redis.opsForValue().set("stu-redis-session:" + stu.getStuId(), uniqueToken, 24 * 60 * 60 * 30);
		StudentVO stuVO = new StudentVO();
		BeanUtils.copyProperties(stu, stuVO);
		stuVO.setUserToken(uniqueToken);
		return stuVO;
	}

	/*
	 * 学生登陆
	 */
	@RequestMapping(value = "/student/login")
	public ResponseVO stuLogin(@RequestBody Student stu) {
		if (StringUtils.isBlank(stu.getStuId()) || StringUtils.isBlank(stu.getStuPassword())) {
			return ResponseVO.appFail("用户名或密码为空，请再次输入！");
		}
		Student queryStu = studentService.queryStu(stu);
		if (queryStu == null) {
			return ResponseVO.appFail("用户名或密码错误，请再次输入！");
		} else {

			if (queryStu.getStuStatus() == 0) {
				return ResponseVO.appFail("已经被封号，请联系管理员解封！");
			} else if (queryStu.getStuStatus() == 2) {
				return ResponseVO.appFail("账号处于被投诉状态，请等待管理员审核...");
			} else {
				queryStu.setStuPassword("");
				StudentVO stuVO = setStuRedisSessionToken(queryStu);
				return ResponseVO.success(stuVO);
			}
		}

	}

	/*
	 * 学生注销
	 */
	@RequestMapping(value = "/student/logout")
	public ResponseVO stuRegister(String myId) {

		Boolean delete = redis.delete("stu-redis-session:" + myId);
		if (delete) {
			return ResponseVO.success("注销成功！！");// 注册成功
		} else {
			return ResponseVO.serviceFail("注销失败！！");
		}

	}

	/*
	 * 学生注册
	 */
	@RequestMapping(value = "/student/register")
	public ResponseVO stuRegister(@RequestBody Student stu) {

		stu.setStuId(stu.getStuTel());
		stu.setCreated(new Date());
		boolean isSuccess = studentService.register(stu);
		if (isSuccess) {
			return ResponseVO.success(stu);// 注册成功
		} else {
			return ResponseVO.serviceFail("注册失败");
		}

	}

	/**
	 * 查看个人详细信息
	 */
	@RequestMapping(value = "/getPersonInfo")
	public ResponseVO getStudentInfo(String myId, String stuId) {
		boolean is_me = false;
		boolean is_contact = false;
		if (myId.equals(stuId)) {
			is_me = true;
		}
		is_contact = stucontactService.getStucontactBytofromId(myId, stuId);
		Student stu = studentService.findStuByid(stuId);
		GetPersonInfo gpi = new GetPersonInfo();
		gpi.setIs_contact(is_contact);
		gpi.setIs_me(is_me);
		gpi.setStudent(stu);
		return ResponseVO.success(gpi);
	}

	/**
	 * 
	 * @param stuImage
	 * @param myId
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 *             更换头像
	 */
	@RequestMapping(value = "/student/changeImage", headers = "content-type=multipart/form-data")
	public ResponseVO changeImage(MultipartFile stuImage, String myId) throws IllegalStateException, IOException {
		String url = null;
		if (!stuImage.isEmpty()) {
			String originFileName = stuImage.getOriginalFilename();
			String newName = UUIDUtils.createUUID2() + originFileName.substring(originFileName.indexOf("."));
			url = "D:\\学习\\xinghuo\\images\\stuImages\\" + newName;
			// 上传图片
			stuImage.transferTo(new File(url));
			// 然后把映射路径设置进去
			url = urlHead + ":8081/uploadImages/" + newName;
		}
		Student stu = studentService.findStuByid(myId);
		stu.setStuImage(url);
		int num = studentService.updateStudent(stu);
		if (num > 0) {
			return ResponseVO.successUrl("更换头像成功!", url);
		} else {
			return ResponseVO.serviceFail("更换头像失败");
		}
	}

	/*
	 * 学生修改密码
	 */
	@RequestMapping(value = "/student/updatePwd")
	public ResponseVO updatePwd(String myId, String oldPwd, String newPwd) {
		Student stu = studentService.findStuByid(myId);
		String oldPwdMD5 = MD5Util.encode(oldPwd);
		if (!stu.getStuPassword().equals(oldPwdMD5)) {
			return ResponseVO.serviceFail("原密码错误");
		} else {
			stu.setStuPassword(MD5Util.encode(newPwd));
			int num = studentService.updateStudent(stu);
			if (num > 0) {
				return ResponseVO.success(stu);
			} else {
				return ResponseVO.serviceFail("修改失败");
			}
		}

	}

}
