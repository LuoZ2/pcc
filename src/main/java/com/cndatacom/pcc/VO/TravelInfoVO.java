package com.cndatacom.pcc.VO;

import java.util.List;

import com.cndatacom.pcc.domain.Student;
import com.cndatacom.pcc.domain.Traveldetail;
import com.cndatacom.pcc.domain.Travels;

import lombok.Data;
/**
 * 
 * @author luo
 *行程详情中的行程详情视图实体
 */
@Data
public class TravelInfoVO {

	//组队学生，第一个是发起人
	private List<Student> stus;
	
	//所在的行程
	private Travels travels;
	
	//是否可以查看对方的联系方式
	private Boolean isContect;
	
	//行程详情
	private List<Traveldetail> traveldetails;
	
	//是否为队长
	private Boolean isCaptain;
}
