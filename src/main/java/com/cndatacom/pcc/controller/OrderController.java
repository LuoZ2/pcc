package com.cndatacom.pcc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cndatacom.pcc.VO.AliPayInfoVO;
import com.cndatacom.pcc.VO.AliPayResultVO;
import com.cndatacom.pcc.VO.OrderInfo;
import com.cndatacom.pcc.VO.PagedResult;
import com.cndatacom.pcc.VO.ResponseVO;
import com.cndatacom.pcc.domain.Orders;
import com.cndatacom.pcc.service.AliPayService;
import com.cndatacom.pcc.service.DrcontactService;
import com.cndatacom.pcc.service.OrderDetailService;
import com.cndatacom.pcc.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderDetailService OoderDetailService;
	@Autowired
	private DrcontactService drcontactService;

	@Autowired
	private AliPayService aliPayService;

	/**
	 * 司机广场上分页查找所有订单
	 */
	@RequestMapping(value = "/driver/getOrdersLimit")
	public PagedResult getlist(int page, String originAddr, String destination, Integer peoplenum, Integer money) {
		int nums = 6;
		PagedResult pgedResult = orderService.getOrdersLimit(nums, page, originAddr, destination, peoplenum, money);
		return pgedResult;
	}

	/**
	 * 8/2 xw 司机查询待出行订单
	 */
	@RequestMapping(value = "/driver/getMyWaitOrder")
	public ResponseVO getMyWaitOrder(String myId) {
		List<OrderInfo> oiList = new ArrayList<>();
		// 0是司机1是学生
		List<Long> oidList = orderService.getWaitOrderid(myId, 0);
		if (oidList.size() != 0) {
			for (Long oid : oidList) {
				OrderInfo oi = OoderDetailService.getOrderDetailInfo(oid);
				oiList.add(oi);
			}
			return ResponseVO.success(oiList);
		}
		return ResponseVO.appFail("找不到相关信息");
	}

	/**
	 * 8/2 xw 司机查询正在出行订单
	 */
	@RequestMapping(value = "/driver/getOngoingOrder")
	public ResponseVO getOngoingOrder(String myId) {
		Long oid = orderService.getOngoingOrder(myId, 0);
		if (oid != null) {
			OrderInfo oi = OoderDetailService.getOrderDetailInfo(oid);
			return ResponseVO.success(oi);
		}
		return ResponseVO.appFail("找不到相关信息");
	}

	/**
	 * 8/2 xw 司机接单
	 */
	@RequestMapping(value = "/driver/takeOrder")
	public ResponseVO takeOrder(String myId, long oid) {
		// 判断司机是否有待出行订单，如果有就不允许接单
		boolean flag = orderService.getOrdersByDrid(myId);
		if (flag) {
			Orders order = orderService.findOrderByid(oid);
			order.setOstatus(3);
			order.setDrId(myId);
			System.out.println(order);
			boolean is_Success = orderService.updateOrder(order);
			if (is_Success) {
				// 司机接单后可以看到联系方式
//				List<String> list = OoderDetailService.getOrderStuid(oid);
//				drcontactService.addContactDr(list, myId);
				return ResponseVO.success("接单成功！");
			} else {
				return ResponseVO.success("接单失败，请稍后在试！");
			}
		} else {
			return ResponseVO.success("有订单还没有完成，请完成后再接单！");
		}
	}

	/**
	 * 8/2 xw 学生查询待接单订单
	 */
	@RequestMapping(value = "/student/waitTakeOrder")
	public ResponseVO waitTakeOrder(String myId) {
		List<Long> oidList = orderService.waitTakeOrder(myId);
		if (oidList.size() != 0) {
			List<OrderInfo> diList = OoderDetailService.waitTakeOrder(oidList);
			return ResponseVO.success(diList);
		}
		return ResponseVO.appFail("找不到相关数据");
	}

	/**
	 * 8/2 xw 学生用户查看我的待出行订单 0是司机1是学生
	 */
	@RequestMapping(value = "/student/getMyWaitOrder")
	public ResponseVO getStuWaitOrder(String myId) {
		List<OrderInfo> oiList = new ArrayList<>();
		// 0是司机1是学生
		List<Long> oidList = orderService.getWaitOrderid(myId, 1);
		if (oidList.size() != 0) {
			for (Long oid : oidList) {
				OrderInfo oi = OoderDetailService.getOrderDetailInfo(oid);
				oiList.add(oi);
			}
			return ResponseVO.success(oiList);
		}
		return ResponseVO.appFail("找不到相关信息");
	}

	/**
	 * 8/2 xw 学生用户在待出行订单界面上点击出行按钮
	 * 
	 */
	@RequestMapping(value = "/student/onging")
	public ResponseVO onging(Long oid, String myId) {
		int status = getStuOngoingOrder(myId).getStatus();
		if (status != 200) {
			Orders order = orderService.findOrderByid(oid);
			order.setOstatus(4);
			boolean flag = orderService.updateOrder(order);
			if (flag) {
				return ResponseVO.success("操作成功");
			} else {
				return ResponseVO.serviceFail("操作失败");
			}
		} else {
			return ResponseVO.serviceFail("正在出行");
		}

	}

	/**
	 * 8/2 xw 学生查询正在出行订单
	 */
	@RequestMapping(value = "/student/getOngoingOrder")
	public ResponseVO getStuOngoingOrder(String myId) {
		Long oid = orderService.getOngoingOrder(myId, 1);
		OrderInfo odi = OoderDetailService.getOrderDetailInfo(oid);
		if (odi != null) {
			return ResponseVO.success(odi);
		}
		return ResponseVO.appFail("找不到相关信息");
	}

	/**
	 * 获取订单信息
	 * 
	 * @param nowPage
	 *            当前页码
	 * @param pageSize
	 *            每页页数
	 * @param ostatus
	 *            订单状态码 0:取消订单 1:完成订单 2:等待接单 3:待出行 
	 *            4:正在出行 5:待付款
	 * 
	 * @param roleid
	 *            0：司机 1：学生
	 * @return
	 */

	@RequestMapping(value = "/order/getOrderInfo", method = RequestMethod.POST)
	public PagedResult getOrderInfo(String userId, Integer ostatus, Integer roleid,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "num", required = false, defaultValue = "5") Integer num) {

		PagedResult pagedResult = orderService.getOrderByUserId(userId, ostatus, roleid, num, page);

		return pagedResult;

	}

	/**
	 * 获取支付信息
	 * 
	 * @param orderId
	 *            订单id
	 * @return
	 */
	@RequestMapping(value = "/order/getPayInfo", method = RequestMethod.POST)
	public ResponseVO getPayInfo(@RequestParam("orderId") Long orderId, String userId) {

		if (userId == null || userId.trim().length() == 0) {
			return ResponseVO.serviceFail("抱歉，用户未登陆");
		}
		// 订单二维码返回结果
		AliPayInfoVO aliPayInfoVO = aliPayService.getQRCode(orderId);
		return ResponseVO.success(aliPayInfoVO);
	}

	/**
	 * 获取支付结果
	 * 
	 * @param orderId
	 *            订单id
	 * @param tryNums
	 *            请求重试次数
	 * @return
	 */
	@RequestMapping(value = "/order/getPayResult", method = RequestMethod.POST)
	public ResponseVO getPayResult(@RequestParam("orderId") Long orderId,
			@RequestParam(name = "tryNums", required = false, defaultValue = "1") Integer tryNums) {

		// 判断是否支付超时
		if (tryNums >= 4) {
			return ResponseVO.serviceFail("订单支付失败，请稍后重试");
		} else {
			AliPayResultVO aliPayResultVO = aliPayService.getOrderStatus(orderId);
			if (aliPayResultVO == null || aliPayResultVO.getOrderId() == null) {
				AliPayResultVO serviceFailVO = new AliPayResultVO();
				serviceFailVO.setOrderId(orderId);
				serviceFailVO.setOrderStatus(0);
				serviceFailVO.setOrderMsg("支付不成功");
				return ResponseVO.success(serviceFailVO);
			}
			return ResponseVO.success(aliPayResultVO);
		}
	}

	/**
	 * 学生取消订单
	 * 
	 * @param order---oid
	 *            status
	 * @return
	 */
	@RequestMapping(value = "/order/cancelOrder", method = RequestMethod.POST)
	public ResponseVO cancelOrder(Long oid, String myId) {

		boolean isSuccess = false;
		Orders order = orderService.findOrderByid(oid);
		if (order.getInitId().equals(myId)) {

			Integer status = order.getOstatus();
			// 等待出行或者待出行才可以取消
			if (status == 2 || status == 3) {
				order.setOstatus(0);
				isSuccess = orderService.updateOrder(order);
			}
			if (isSuccess) {
				return ResponseVO.success("取消订单成功");
			} else {
				return ResponseVO.serviceFail("业务繁忙！");
			}
		} else {
			return ResponseVO.serviceFail("您没有权限");
		}
	}

	/**
	 * 学生开始出行
	 * 
	 * @param status修改为4
	 * @return
	 */
	@RequestMapping(value = "/order/startTravel", method = RequestMethod.POST)
	public ResponseVO updateOrderStatus(Long oid, String myId) {
		boolean isSuccess = false;
		Orders order = orderService.findOrderByid(oid);
		if (order.getInitId().equals(myId)) {
			order.setOstatus(4);
			isSuccess = orderService.updateOrder(order);
		} else {
			return ResponseVO.serviceFail("您没有权限！");
		}

		if (isSuccess) {
			return ResponseVO.success("开始出行！");
		} else {
			return ResponseVO.serviceFail("业务繁忙！");
		}
	}
	/**
	 * 司机点击完成，status改为5
	 * 
	 * @param status修改为4
	 * @return
	 */
	@RequestMapping(value = "/order/endTravel", method = RequestMethod.POST)
	public ResponseVO endTravel(Long oid, String myId) {
		boolean isSuccess = false;
		Orders order = orderService.findOrderByid(oid);
		if (order.getDrId().equals(myId)) {
			order.setOstatus(5);
			isSuccess = orderService.updateOrder(order);
		} else {
			return ResponseVO.serviceFail("您没有权限！");
		}

		if (isSuccess) {
			return ResponseVO.success("提醒客户付款！");
		} else {
			return ResponseVO.serviceFail("业务繁忙！");
		}
	}
	/**
	 * 学生修改订单金额
	 * @param oid
	 * @param myId
	 * @param money
	 * @return
	 */
	@RequestMapping(value = "/order/updateMoney", method = RequestMethod.POST)
	public ResponseVO endTravel(Long oid, String myId,Integer money) {
		boolean isSuccess = false;
		Orders order = orderService.findOrderByid(oid);
		if (order.getInitId().equals(myId)) {
			order.setMoney(money);
			isSuccess = orderService.updateOrder(order);
		} else {
			return ResponseVO.serviceFail("您没有权限！");
		}

		if (isSuccess) {
			return ResponseVO.success("修改金额成功！");
		} else {
			return ResponseVO.serviceFail("业务繁忙！");
		}
	}
}
