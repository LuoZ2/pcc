package com.cndatacom.pcc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cndatacom.pcc.VO.PagedResult;
import com.cndatacom.pcc.VO.ResponseVO;
import com.cndatacom.pcc.VO.StuTraveldetail;
import com.cndatacom.pcc.VO.TeamInfoVO;
import com.cndatacom.pcc.VO.TravelInfo;
import com.cndatacom.pcc.VO.TravelInfoVO;
import com.cndatacom.pcc.dao.StudentMapper;
import com.cndatacom.pcc.domain.Student;
import com.cndatacom.pcc.domain.Traveldetail;
import com.cndatacom.pcc.domain.Travels;
import com.cndatacom.pcc.service.StucontactService;
import com.cndatacom.pcc.service.StudentService;
import com.cndatacom.pcc.service.TraveldetailService;
import com.cndatacom.pcc.service.TravelsService;

@RestController
public class TraveldetailController {

	@Autowired
	private TravelsService travelsService;

	@Autowired
	private TraveldetailService traveldetailService;

	@Autowired
	private TravelsService travelService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private StucontactService stucontactService;

	/*
	 * 查询行程详情 同时点击我的队伍详情也是调用这个接口
	 */
	@RequestMapping(value = "/traveldetail/queryTraveldeatil")
	public ResponseVO publishTravel(Long travelid, String myId) throws ParseException {
		TravelInfoVO travelInfoVO = new TravelInfoVO();
		List<Student> stus = new ArrayList<>();

		Travels travel = travelsService.getTravel(travelid);
		List<Traveldetail> traveldetails = traveldetailService.getTraveldetails(travelid);
		for (Traveldetail traveldetail : traveldetails) {
			Student e = studentService.findStuByid(traveldetail.getStuId());
			stus.add(e);
		}
		// 查看两个的状态——是否可以看到对方的联系信息
		boolean isContect = stucontactService.getStucontactBytofromId(myId, travel.getInitId());
		travelInfoVO.setStus(stus);
		travelInfoVO.setTravels(travel);
		travelInfoVO.setIsContect(isContect);
		travelInfoVO.setTraveldetails(traveldetails);

		if (myId.equals(travel.getInitId())) {
			travelInfoVO.setIsCaptain(true);
		} else {
			travelInfoVO.setIsCaptain(false);
		}

		if (travel.getTravelid() != null) {
			return ResponseVO.success(travelInfoVO);
		} else {
			return ResponseVO.serviceFail("查询行程失败");
		}

	}
	/*
	 * 我的队伍详情
	 */
	@RequestMapping(value = "/traveldetail/queryTeamdeatil")
	public ResponseVO queryTeamdeatil(Long travelid, String myId) throws ParseException {
		TeamInfoVO teamInfoVO = new TeamInfoVO();
		List<StuTraveldetail> list = new ArrayList<>();

		Travels travel = travelsService.getTravel(travelid);
		List<Traveldetail> traveldetails = traveldetailService.getTraveldetails(travelid);
		for (Traveldetail traveldetail : traveldetails) {
			Student e = studentService.findStuByid(traveldetail.getStuId());
			StuTraveldetail stutraveldetail = new StuTraveldetail(e,traveldetail);
			list.add(stutraveldetail);
		}
		// 查看两个的状态——是否可以看到对方的联系信息
		boolean isContect = stucontactService.getStucontactBytofromId(myId, travel.getInitId());
		teamInfoVO.setTravels(travel);
		teamInfoVO.setIsContect(isContect);
		teamInfoVO.setStuTraveldetails(list);

		if (myId.equals(travel.getInitId())) {
			teamInfoVO.setIsCaptain(true);
		} else {
			teamInfoVO.setIsCaptain(false);
		}

		if (travel.getTravelid() != null) {
			return ResponseVO.success(teamInfoVO);
		} else {
			return ResponseVO.serviceFail("查询行程失败");
		}

	}
	
	/**
	 * 申请加入时要想判断
	 */
	@RequestMapping(value="/check/jointeam")
	public ResponseVO checkJoin(@RequestParam Long travelid,@RequestParam String myId) {
		boolean is_join=traveldetailService.checkJoinTeam(travelid, myId);
		if(is_join) {
			return ResponseVO.serviceFail("你已经加入了该队伍，不要重复加入");
		}
		return ResponseVO.success("可以加入该队伍");
	}
	
	
	
	
	
	/**
	 * 申请加入队伍
	 */
	@RequestMapping(value = "/student/jointeam")
	public ResponseVO joinTeam(@RequestBody Traveldetail trade) {
		trade.setCreated(new Date());
		trade.setItemid(System.currentTimeMillis());
		trade.setStatus(0);
		int num = traveldetailService.addTraveldetail(trade);
		if (num > 0) {
			Travels travel = travelService.getTravel(trade.getTravelid());
			// 更新订单的实际人数
			travel.setActualNum(travel.getActualNum() + 1);
			if (travelService.updateTravle(travel)) {
				return ResponseVO.success("申请成功，等待发起人审核！");
			}
		}
		return ResponseVO.appFail("申请失败，请检查你所填的信息！");
	}

	/**
	 * 不同意加入 或者队员主动退出
	 */
	@RequestMapping(value = "/student/notjoin")
	public ResponseVO notJoin(long travelid, String stu_id) {
		Traveldetail tra = traveldetailService.getBytravelidAndStuid(travelid, stu_id);
		if (traveldetailService.deleteTraveldetail(tra)) {
			Travels travel = travelService.getTravel(travelid);
			// 更新订单的实际人数
			travel.setActualNum(travel.getActualNum() - 1);
			if (travelService.updateTravle(travel)) {
				return ResponseVO.success("操作成功！");
			}
		}
		return ResponseVO.success("操作失败！");
	}

	/**
	 * 同意加入
	 */
	@RequestMapping(value = "/student/yesjoin")
	public ResponseVO yesJoin(long travelid, String stu_id) {
		Traveldetail tra = traveldetailService.getBytravelidAndStuid(travelid, stu_id);
		tra.setStatus(1);
		if (traveldetailService.updateTraveldetail(tra)) {
			return ResponseVO.success("操作成功！");
		}
		return ResponseVO.success("操作失败！");
	}

}
