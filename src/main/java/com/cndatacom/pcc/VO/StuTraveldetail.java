package com.cndatacom.pcc.VO;

import com.cndatacom.pcc.domain.Student;
import com.cndatacom.pcc.domain.Traveldetail;

import lombok.Data;
/**
 * 
 * @author luo
 * 我的队伍中 学生与行程详情一一对应的实体
 */
@Data
public class StuTraveldetail {
	private Student stu;
	
	private Traveldetail traveldetail;
	
	public StuTraveldetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StuTraveldetail(Student stu, Traveldetail traveldetail) {
		super();
		this.stu = stu;
		this.traveldetail = traveldetail;
	}

	
}
