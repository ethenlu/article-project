package com.bytoday.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;


public class JWTUtil {

	private static final String signature = "!@#asds$%&Aaa";

	//生成token header.payload.signature
	public static String getToken(Map<String,String> map){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,7);
		JWTCreator.Builder builder = JWT.create();
		map.forEach((k,v)->{
			builder.withClaim(k,v);
		});
		String token = builder.withExpiresAt(calendar.getTime()).sign(Algorithm.HMAC256(signature));
		return token;
	}

//	//验证token
//	public static void verify(String token){
//		JWT.require(Algorithm.HMAC256(signature)).build().verify(token);
//	}

	//验证token
	public static DecodedJWT verify(String token){
		DecodedJWT verify = JWT.require(Algorithm.HMAC256(signature)).build().verify(token);
		return verify;
	}
}
