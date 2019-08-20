package com.cndatacom.pcc.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cndatacom.pcc.VO.PagedResult;
import com.cndatacom.pcc.domain.Orders;

public interface OrderService {
	//分页查找订单
	PagedResult getOrdersLimit(int nums, int nowPage, String originAddr, String destination, Integer peoplenum,
			Integer money);
	
	//按id查询订单
	Orders findOrderByid(Long oid);
	
	//更新订单信息
	boolean updateOrder(Orders order);
	
	//查询是否可以接单true可以接单
	boolean getOrdersByDrid(String drId);
	
	//根据stuId查询订单的状态
	boolean findOrderStatus(String myid, Integer ostatus);
	
	// 当订单支付成功状态时，修改订单状态为1
    Boolean paySuccess(Long orderId);
    // 获取订单信息
    Orders getOrderInfoById(Long orderId);
    
    /**
     * 8/2添加以下方法
     */
    //司机或者学生查询自己的待出行订单号
    List<Long> getWaitOrderid(String myId,Integer roleid);
  	
  	//司机或者学生查询自己的正在出行订单号
	Long getOngoingOrder(String myId,Integer roleid);
	
	//根据学生id查询该学生的所有订单号
	List<Long> waitTakeOrder(String myId);

	//根据学生id查询某个状态的订单
	PagedResult getOrderByUserId(String myId,Integer ostatus, Integer roleid ,Integer num, Integer page);


  	
}
