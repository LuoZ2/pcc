package com.cndatacom.pcc.serviceImpl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cndatacom.pcc.VO.StuInfoModel;
import com.cndatacom.pcc.dao.StudentMapper;
import com.cndatacom.pcc.domain.Student;
import com.cndatacom.pcc.service.StudentService;
import com.cndatacom.pcc.util.MD5Util;

@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentMapper studentMapper;


	@Override
	public boolean register(Student stuModel) {

        //数据加密MDS
        String md5Password  = MD5Util.encode(stuModel.getStuPassword());
        stuModel.setStuPassword(md5Password);
        //将数据实体存入数据库
        Integer insert = studentMapper.insert(stuModel);
        if(insert > 0){
            return true;
        }else {
            return false;
        }}



	@Override
	public StuInfoModel getStuInfo(int uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkNickName(String nickName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public StuInfoModel updateStuInfo(StuInfoModel stuInfoModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Student queryStu(Student stuModel) {
		// TODO Auto-generated method stub
		String md5Password  = MD5Util.encode(stuModel.getStuPassword());
        stuModel.setStuPassword(md5Password);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stu_id", stuModel.getStuId());
        queryWrapper.eq("stu_password", stuModel.getStuPassword());
        queryWrapper.eq("stu_status", 1);
        Student stu = studentMapper.selectOne(queryWrapper);
        return stu;
	}



	@Override
	public Student findStuByid(String stuId) {
		// TODO Auto-generated method stub
		return studentMapper.selectById(stuId);
	}



	@Override
	public int updateStudent(Student stu) {
		// TODO Auto-generated method stub
		return studentMapper.updateById(stu);
	}



	@Override
	public List<Student> getContactStudent(List<String> stuId_list,String name) {
		QueryWrapper<Student> wrapper=new QueryWrapper<Student>();
		wrapper.in("stu_id", stuId_list);
		System.out.println("name:"+name);
		if(name!=null) {
			System.out.println("input ______");
			wrapper.like("stu_name", name);
		}
		List<Student> list = studentMapper.selectList(wrapper);
		return list;
	}



	@Override
	public List<Student> getAllStudent() {
		// TODO Auto-generated method stub
		return studentMapper.selectList(null);
	}



}
