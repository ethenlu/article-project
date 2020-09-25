package com.bytoday.common.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.bytoday.common.utils.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class JWTInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("token");
		HashMap<String, Object> map = new HashMap<>();
		try {
			JWTUtil.verify(token);
			return true;
		} catch (SignatureVerificationException e) {
			e.printStackTrace();
			map.put("msg","无效签名");
		}catch (TokenExpiredException e){
			e.printStackTrace();
			map.put("msg","token过期");
		}catch (AlgorithmMismatchException e){
			e.printStackTrace();
			map.put("msg","算法不匹配");
		}catch (Exception e){
			e.printStackTrace();
			map.put("msg","token无效");
		}

		map.put("status",false);
		String json = new ObjectMapper().writeValueAsString(map);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(json);
		return false;
	}

}
