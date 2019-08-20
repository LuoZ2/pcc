package com.cndatacom.pcc.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cndatacom.pcc.VO.ResponseVO;
import com.cndatacom.pcc.domain.Orderdetail;
import com.cndatacom.pcc.domain.Orders;
import com.cndatacom.pcc.domain.Traveldetail;
import com.cndatacom.pcc.domain.Travels;
import com.cndatacom.pcc.service.AssistOrderService;
import com.cndatacom.pcc.service.OrderService;
import com.cndatacom.pcc.service.TraveldetailService;
import com.cndatacom.pcc.service.TravelsService;

@RestController
public class AssistOrderController {

	@Autowired
	private TravelsService travelService;
	@Autowired
	private TraveldetailService traveldetailService;
	@Autowired
	private AssistOrderService assistOrderService;

	/**
	 * 完成组队后在平台打车
	 * 
	 * @return
	 */
	@RequestMapping("/travel/addOrder")
	public ResponseVO addOrder(Long travelid, Integer money) {
		Travels travel = travelService.getTravel(travelid);
		travel.setStatus(1);
		travelService.updateTravle(travel);
		Long oid = System.currentTimeMillis();
		Orders order = swapOrder(travel, money, oid);
		System.out.println(order);
		boolean is_success = assistOrderService.addOrder(order);
		int num = 0;
		List<Traveldetail> odList = traveldetailService.getTraveldetails(travelid);
		for (Traveldetail traveldetail : odList) {
			if (traveldetail.getStatus() == 1) {
				Orderdetail orderDetail = swapOrderDetail(traveldetail, oid);
				num = num + assistOrderService.addOrderDetail(orderDetail);
			}
		}
		if (is_success && odList.size() == num) {
			return ResponseVO.success("添加订单成功，等待司机接单");
		}
		return ResponseVO.serviceFail("未知错误！");
	}

	/**
	 * 把TravelDetail的数据存进OrderDetail表
	 * 
	 * @param traveldetail
	 * @param oid
	 * @return
	 */
	public Orderdetail swapOrderDetail(Traveldetail traveldetail, long oid) {
		Orderdetail od = new Orderdetail();
		od.setCreated(new Date());
		od.setDestination(traveldetail.getDestination());
		od.setFollowId(traveldetail.getStuId());
		od.setOid(oid);
		od.setOriginAddr(traveldetail.getOriginAddr());
		od.setStarttime(traveldetail.getStarttime());
		od.setTelephone(traveldetail.getTelephone());
		return od;
	}

	/**
	 * 把Travel的数据存进Ordersl表
	 * 
	 * @param travel
	 * @param money
	 * @param oid
	 * @return
	 */
	public Orders swapOrder(Travels travel, Integer money, long oid) {
		/**
		 * 司机的id没有设置
		 */
		Orders order = new Orders();
		order.setOid(oid);
		order.setCreated(new Date());
		order.setDestination(travel.getDestination());
		order.setInitId(travel.getInitId());
		order.setMoney(money);
		order.setOriginAddr(travel.getOriginAddr());
		order.setOstatus(2);
		order.setPeoplenum(travel.getActualNum());
		order.setStarttime(travel.getStarttime());
		order.setTelephone(travel.getTelephone());
		order.setTravelid(travel.getTravelid());
		return order;
	}

	/**
	 * 完成组队后在私下叫车status设为3
	 * 
	 * @return
	 */
	@RequestMapping("/travel/byMyself")
	public ResponseVO travelByMyself(Long travelid) {
		int isSuccess = travelService.updateTravleStatus(travelid, 3);
		if (isSuccess > 0) {
			return ResponseVO.success("组队完成");
		} else {
			return ResponseVO.serviceFail("业务繁忙，请稍后再试");
		}
	}
}
