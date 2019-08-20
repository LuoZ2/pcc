package com.cndatacom.pcc.serviceImpl;

import static org.hamcrest.CoreMatchers.allOf;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cndatacom.pcc.VO.OrderDetailInfo;
import com.cndatacom.pcc.VO.OrderInfo;
import com.cndatacom.pcc.VO.PagedResult;
import com.cndatacom.pcc.dao.DriversMapper;
import com.cndatacom.pcc.dao.OrderdetailMapper;
import com.cndatacom.pcc.dao.OrdersMapper;
import com.cndatacom.pcc.dao.StudentMapper;
import com.cndatacom.pcc.domain.Drivers;
import com.cndatacom.pcc.domain.Orderdetail;
import com.cndatacom.pcc.domain.Orders;
import com.cndatacom.pcc.domain.Student;
import com.cndatacom.pcc.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrdersMapper orderMapper;
	@Autowired
	private OrderdetailMapper orderDetailMapper;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private DriversMapper dirverMapper;

	@Override
	public PagedResult getOrdersLimit(int nums, int nowPage, String originAddr, String destination, Integer peoplenum,
			Integer money) {
		PagedResult pageResult = new PagedResult();

		QueryWrapper<Orders> orderWrapper = new QueryWrapper<Orders>();
		orderWrapper.eq("ostatus", 2);
		orderWrapper.orderByDesc("created");

		// 条件筛选 起点、目的地、人数、费用
		if (originAddr != "" && originAddr != null) {
			String originAddrStr = "%" + originAddr + "%";
			orderWrapper.like("origin_addr", originAddrStr);
		}
		if (destination != "" && destination != null) {
			String destinationStr = "%" + destination + "%";
			orderWrapper.like("destination", destinationStr);
		}
		if (peoplenum != null) {
			orderWrapper.eq("peoplenum", peoplenum);
		}
		if (money != null) {
			orderWrapper.eq("money", money);
		}

		// 分页查找订单
		Page<Orders> page = new Page<>(nowPage, nums);
		List<Orders> orderList = orderMapper.selectPage(page, orderWrapper).getRecords();
		pageResult.setNum(orderList.size());

		// 组装List<orderInfo>
		/**
		 * orderInfo有两个属性 order List:orderDetailInfo
		 */
		List<OrderInfo> oiList = new ArrayList<OrderInfo>();
		for (Orders orders : orderList) {
			OrderInfo oi = new OrderInfo();
			oi.setOrder(orders);
			oi.setStu(studentMapper.selectById(orders.getInitId()));
			oiList.add(oi);
		}
		// 获取总记录数
		int totalCounts = orderMapper.selectCount(orderWrapper);
		// 计算总页码
		int totalPages = (totalCounts / nums) + 1;

		pageResult.setRows(oiList);
		pageResult.setNowPage(nowPage);
		pageResult.setTotalPage(totalPages);

		return pageResult;
	}

	// 封装List<OrderDetailInfo>
	public List<OrderDetailInfo> getOrderDetailInfo(long oid) {
		QueryWrapper<Orderdetail> odMapper = new QueryWrapper<Orderdetail>();
		odMapper.eq("oid", oid);
		List<Orderdetail> odList = orderDetailMapper.selectList(odMapper);
		List<OrderDetailInfo> odiList = new ArrayList<OrderDetailInfo>();
		for (Orderdetail orderdetail : odList) {
			OrderDetailInfo odi = new OrderDetailInfo();
			odi.setOrderDetail(orderdetail);
			Student stu = studentMapper.selectById(orderdetail.getFollowId());
			odi.setStudent(stu);
			odiList.add(odi);
		}
		return odiList;
	}

	@Override
	public Orders findOrderByid(Long oid) {
		// TODO Auto-generated method stub
		return orderMapper.selectById(oid);
	}

	@Override
	public boolean updateOrder(Orders order) {
		// TODO Auto-generated method stub
		if (orderMapper.updateById(order) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean getOrdersByDrid(String drId) {
		// TODO Auto-generated method stub
		QueryWrapper<Orders> orderWrapper = new QueryWrapper<Orders>();
		orderWrapper.eq("ostatus", 3);
		orderWrapper.eq("dr_id", drId);
		List<Orders> list = orderMapper.selectList(orderWrapper);
		if (list.size() > 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean findOrderStatus(String myid, Integer ostatus) {
		QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("init_id", myid);
		queryWrapper.eq("ostatus", ostatus);
		Orders order = orderMapper.selectOne(queryWrapper);
		if (order == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Boolean paySuccess(Long orderId) {
		Orders order = orderMapper.selectById(orderId);
		order.setOstatus(1);
		int isSucces = orderMapper.updateById(order);
		if (isSucces > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Orders getOrderInfoById(Long orderId) {

		return orderMapper.selectById(orderId);
	}

	@Override
	public List<Long> getWaitOrderid(String myId, Integer roleid) {
		List<Long> oidList = new ArrayList<>();
		QueryWrapper<Orders> orderWrapper = new QueryWrapper<Orders>();
		orderWrapper.eq("ostatus", 3);
		if (roleid == 0) {
			orderWrapper.eq("dr_id", myId);
			Orders order = orderMapper.selectOne(orderWrapper);
			if (order != null) {
				Long oid = orderMapper.selectOne(orderWrapper).getOid();
				oidList.add(oid);
			}
		} else {
			orderWrapper.eq("stu_id", myId);
			List<Orders> orderList = orderMapper.selectList(orderWrapper);
			if (orderList.size() != 0) {
				for (Orders order : orderList) {
					oidList.add(order.getOid());
				}
			}
		}
		return oidList;

	}

	@Override
	public Long getOngoingOrder(String myId, Integer roleid) {
		Long oid = null;
		QueryWrapper<Orders> orderWrapper = new QueryWrapper<Orders>();
		orderWrapper.eq("ostatus", 4);
		if (roleid == 0) {
			orderWrapper.eq("dr_id", myId);
			Orders order = orderMapper.selectOne(orderWrapper);
			if (order != null) {
				oid = orderMapper.selectOne(orderWrapper).getOid();
			}
		} else {
			orderWrapper.eq("stu_id", myId);
			Orders order = orderMapper.selectOne(orderWrapper);
			if (order != null) {
				oid = orderMapper.selectOne(orderWrapper).getOid();
			}
		}
		return oid;

	}

	@Override
	public List<Long> waitTakeOrder(String myId) {
		List<Long> oidList = new ArrayList<>();
		QueryWrapper<Orderdetail> wrapper = new QueryWrapper<Orderdetail>();
		wrapper.eq("follow_id", myId);
		List<Orderdetail> list = orderDetailMapper.selectList(wrapper);
		for (Orderdetail orderdetail : list) {
			oidList.add(orderdetail.getOid());
		}
		return oidList;

	}

	@Override
	public PagedResult getOrderByUserId(String myId, Integer ostatus, Integer roleid, Integer num, Integer nowPage) {
		PagedResult pageResult = new PagedResult();
		if(roleid == 1) {
			QueryWrapper<Orderdetail> wapper =new QueryWrapper<Orderdetail>();
			wapper.eq("follow_id", myId);
			List<Long> oid_list=new ArrayList<>();
			List<Orderdetail> od_list=orderDetailMapper.selectList(wapper);
			for (Orderdetail orderdetail : od_list) {
				oid_list.add(orderdetail.getOid());
			}
			QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("ostatus", ostatus);
			//queryWrapper.orderByDesc("created");
			if(oid_list.size()!=0) {
				queryWrapper.in("oid", oid_list);
				Page<Orders> page = new Page<>(nowPage, num);
				List<Orders> order_list = orderMapper.selectPage(page, queryWrapper).getRecords();
				pageResult.setNum(order_list.size());
				// 组装List<orderInfo>
				/**
				 * orderInfo有两个属性 order student List:orderDetailInfo
				 */
				List<OrderInfo> oiList = new ArrayList<OrderInfo>();
				for (Orders order : order_list) {
					OrderInfo oi = new OrderInfo();
					oi.setOrder(order);
					System.out.println(order);
					String drId = order.getDrId();
					if(drId!=null) {
						Drivers driver = dirverMapper.selectById(drId);
						oi.setDriver(driver);
					}
					Student student=studentMapper.selectById(order.getInitId());
					oi.setStu(student);
					oiList.add(oi);
				}
				System.out.println("oiList:"+oiList.size());
				int totalCounts = orderMapper.selectCount(queryWrapper);
				// 计算总页码
				int totalPages = (totalCounts / num) + 1;
				pageResult.setRows(oiList);
				pageResult.setNowPage(nowPage);
				pageResult.setTotalPage(totalPages);
				return pageResult;
			}else {
				return null;
			}
			
		}else {
			QueryWrapper<Orders> orderWrapper = new QueryWrapper<>();
			orderWrapper.eq("dr_id", myId);
			orderWrapper.eq("ostatus", ostatus);
			Page<Orders> page = new Page<>(nowPage, num);
			List<Orders> order_list = orderMapper.selectPage(page, orderWrapper).getRecords();
			// 组装List<orderInfo>
			/**
			 * orderInfo有两个属性 order student List:orderDetailInfo
			 */
			List<OrderInfo> oiList = new ArrayList<OrderInfo>();
			for (Orders order : order_list) {
				OrderInfo oi = new OrderInfo();
				oi.setOrder(order);
				Drivers driver = dirverMapper.selectById(order.getDrId());
				oi.setDriver(driver);
				Student student=studentMapper.selectById(order.getInitId());
				oi.setStu(student);
				oiList.add(oi);
			}
			int totalCounts = orderMapper.selectCount(orderWrapper);
			// 计算总页码
			int totalPages = (totalCounts / num) + 1;
			pageResult.setRows(oiList);
			pageResult.setNowPage(nowPage);
			pageResult.setTotalPage(totalPages);
			return pageResult;
		}
//		OrderInfo orderInfo = new OrderInfo();
//		if (roleid == 1) {
//			QueryWrapper<Orderdetail> qw = new QueryWrapper<>();
//			qw.eq("follow_id", myId);
//			orderInfo.setStu(studentMapper.selectById(myId));
//			List<Orderdetail> list = orderDetailMapper.selectList(qw);
//			List<Long> oidList = new ArrayList<>();
//			for (Orderdetail orderdetail : list) {
//				oidList.add(orderdetail.getOid());
//			}
//			if (oidList.size() != 0) {
//				queryWrapper.in("oid", oidList);
//			} else {
//				return null;
//			}
//		} else {
//			queryWrapper.eq("dr_id", myId);
//		}
//		queryWrapper.eq("ostatus", ostatus);
//		Page<Orders> page = new Page<>(nowPage, num);
//		List<Orders> list = orderMapper.selectPage(page, queryWrapper).getRecords();
//		orderInfo.set
//		
//		pagedResult.setNum(list.size());
//
//		// 需要总页数 totalCounts/nums -> 0 + 1 = 1
//		// 每页10条，我现在有6条 -> 1
//		int totalCounts = orderMapper.selectCount(queryWrapper);
//		int totalPages = (totalCounts / num) + 1;
//		pagedResult.setRows(list);
//		pagedResult.setNowPage(nowPage);
//		pagedResult.setNum(num);
//		pagedResult.setTotalPage(totalPages);
//		return pageResult;
	}

}
