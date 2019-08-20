package com.cndatacom.pcc.VO;

import java.util.List;

import com.alipay.api.domain.OrderDetail;
import com.cndatacom.pcc.domain.Student;
import com.cndatacom.pcc.domain.Traveldetail;
import com.cndatacom.pcc.domain.Travels;

import lombok.Data;

@Data
public class TeamInfoVO {
	//所在的行程
	private Travels travels;
	
	//是否可以查看对方的联系方式
	private Boolean isContect;
	
	private List<StuTraveldetail> stuTraveldetails;
	
	//是否为队长
	private Boolean isCaptain;
}
