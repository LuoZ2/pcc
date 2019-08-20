package com.cndatacom.pcc.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cndatacom.pcc.VO.StuInfoModel;
import com.cndatacom.pcc.domain.Student;


public interface StudentService {
	
	//按id查询学生
	Student findStuByid(String stuId);
	
	//修改用户信息(在添加投诉记录时修改用户的状态码时增加的方法)
	int updateStudent(Student stu);
	
    //查询用户信息
	public Student queryStu(Student stu);

    //用户注册
    boolean register(Student stuModel);

    //检查用户名是否存在
    boolean checkNickName(String nickName);

    //查询用户信息
    StuInfoModel getStuInfo(int stuId);

    //更新用户信息
    StuInfoModel updateStuInfo(StuInfoModel stuInfoModel);
    
    //查询那些学生可以看到保密信息，和正在申请
    List<Student> getContactStudent(List<String> stuId_list,String name);
    
    //查询所有学生
    List<Student> getAllStudent();

    
}
