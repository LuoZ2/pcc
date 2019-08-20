package com.cndatacom.pcc.VO;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 
 * @author luo
 *广场展现行程信息的视图实体类
 */

@Data
public class TravelInfo implements Serializable {

	// 行程id
	private Long travelid;
	/**
	 * 登陆的账号
	 */
	private String stuId;

	/**
	 * 昵称
	 */
	private String stuNick;

	/**
	 * 学校
	 */
	private String school;

	/**
	 * 性别
	 */
	private String stuSex;

	/**
	 * 起始地
	 */
	private String originAddr;

	/**
	 * 目的地
	 */
	private String destination;

	/**
	 * 出发时间
	 */
	private Date starttime;
	
	/**
	 * 头像
	 */
	private String stuImage;
	

	
}
