package com.cndatacom.pcc.VO;

import lombok.Data;

@Data
public class ResponseVO<M> {
	// 返回状态[200-成功 ，500-业务失败，404-表示系统异常]
	private int status;
	// 返回信息
	private String msg;
	// 返回数据实体
	private M data;

	

	private ResponseVO() {
	}

	// 业务成功
	public static <M> ResponseVO success(int nowPage, int totalPage, M m) {
		ResponseVO responseVO = new ResponseVO();
		responseVO.setStatus(200);
		responseVO.setData(m);
		
		return responseVO;
	}

	public static <M> ResponseVO success(M m) {
		ResponseVO responseVO = new ResponseVO();
		responseVO.setStatus(200);
		responseVO.setData(m);
		return responseVO;
	}

	public static <M> ResponseVO success(String msg) {
		ResponseVO responseVO = new ResponseVO();
		responseVO.setStatus(200);
		responseVO.setMsg(msg);
		return responseVO;
	}

	// 业务失败
	public static <M> ResponseVO serviceFail(String msg) {
		ResponseVO responseVO = new ResponseVO();
		responseVO.setStatus(500);
		responseVO.setMsg(msg);
		return responseVO;
	}

	// 系统异常
	public static <M> ResponseVO appFail(String msg) {
		ResponseVO responseVO = new ResponseVO();
		responseVO.setStatus(404);
		responseVO.setMsg(msg);
		return responseVO;
	}
	
	// 主要用于查看学生信息时用到
		public static <M> ResponseVO successUrl(String msg,M m) {
			ResponseVO responseVO = new ResponseVO();
			responseVO.setStatus(200);
			responseVO.setMsg(msg);
			responseVO.setData(m);
			return responseVO;
		}

}
