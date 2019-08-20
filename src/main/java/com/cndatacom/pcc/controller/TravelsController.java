package com.cndatacom.pcc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cndatacom.pcc.VO.PagedResult;
import com.cndatacom.pcc.VO.ResponseVO;
import com.cndatacom.pcc.VO.TravelInfo;
import com.cndatacom.pcc.domain.Traveldetail;
import com.cndatacom.pcc.domain.Travels;
import com.cndatacom.pcc.service.OrderService;
import com.cndatacom.pcc.service.TravelsService;

@RestController
public class TravelsController {

	@Autowired
	private TravelsService travelsService;

	@Autowired
	private OrderService orderService;

	/*
	 * 发布行程
	 */
	@RequestMapping(value = "/travel/publish")
	public ResponseVO publishTravel(@RequestBody Travels travel) throws ParseException {
		// status：订单支付状态
		boolean status = orderService.findOrderStatus(travel.getInitId(), 5);
		if (status == true) {
			return ResponseVO.serviceFail("您还有未支付的订单，请完成支付后再发布行程");
		}
		travel.setTravelid(System.currentTimeMillis());
		travel.setCreated(new Date());
		int isSuccess = travelsService.addTravel(travel);

		if (isSuccess == 1) {
			return ResponseVO.success("行程发布成功");
		} else {
			return ResponseVO.serviceFail("行程发布失败");
		}

	}

	/*
	 * 用户在广场上查询所有行程 分页： nums：每页记录数, nowPage：当前页 筛选条件: originAddr：为""或者null时不生效,
	 * destination：为""或者null时不生效, stuSex为null时不生效, actualNum为null时不生效
	 */
	@RequestMapping(value = "/travel/queryTravelInfo")
	public PagedResult queryTravelInfo(int page, String originAddr, String destination, String stuSex,
			Integer actualNum) {
		int nums = 6;
		PagedResult pagedResult = travelsService.getTravels(nums, page, originAddr, destination, stuSex, actualNum);
		return pagedResult;
	}

	/**
	 * 我的队伍
	 */
	@RequestMapping(value = "/travel/getMyTravelList")
	public PagedResult getMyTravelList(int page, String originAddr, String destination, String stuSex,
			Integer actualNum, String myId) {
		int nums = 6;
		PagedResult pagedResult = travelsService.getMyTravels(nums, page, originAddr, destination, stuSex, actualNum,
				myId);
		return pagedResult;
	}
	
	/**
	 * 取消组队
	 */
	@RequestMapping(value = "/travel/cancel")
	public ResponseVO cancelTravel(Long travelid) {
		int flag = travelsService.updateTravleStatus(travelid, 0);
		if(flag>0) {
			return ResponseVO.success("取消成功");
		}
		return ResponseVO.serviceFail("取消失败！");
	}

}
