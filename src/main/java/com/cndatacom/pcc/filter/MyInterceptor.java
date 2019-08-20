package com.cndatacom.pcc.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.druid.support.json.JSONUtils;
import com.cndatacom.pcc.VO.ResponseVO;

public class MyInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub

		String userId = request.getHeader("userId");
		String userToken = request.getHeader("userToken");

		if (StringUtils.isBlank(userId) && StringUtils.isBlank(userId)) {
			returnErrorResponse(response, ResponseVO.serviceFail("尚未登录，请登录..."));
			return false;
		}

		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userToken)) {
			String uniqueToken = new StringRedisTemplate().opsForValue().get("stu-redis-session:" + userId);
			if (StringUtils.isEmpty(uniqueToken) && StringUtils.isBlank(uniqueToken)) {
				returnErrorResponse(response, ResponseVO.serviceFail("账号已超时，请登录..."));
				return false;
			} else {
				if (!uniqueToken.equals(userToken)) {
					returnErrorResponse(response, ResponseVO.serviceFail("账号被挤出，请登录..."));
					return false;
				}
			}

		} else {
			returnErrorResponse(response, ResponseVO.serviceFail("尚未登录，请登录..."));
			return false;
		}

		return true;

	}

	public void returnErrorResponse(HttpServletResponse response, ResponseVO result)
			throws IOException, UnsupportedEncodingException {
		OutputStream out = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			out = response.getOutputStream();
			out.write(JSONUtils.toJSONString(result).getBytes("utf-8"));
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
