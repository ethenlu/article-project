//package com.bytoday.controller;
//
//import com.bytoday.common.verifyCode.IVerifyCodeGen;
//import com.bytoday.common.verifyCode.SimpleCharVerifyCodeGenImpl;
//import com.bytoday.common.verifyCode.VerifyCode;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@RestController
//@Slf4j
//@CrossOrigin
//public class VerifyController {
//	private static final Logger logger = LoggerFactory.getLogger(SimpleCharVerifyCodeGenImpl.class);
//
//	@ApiOperation(value = "验证码")
//	@GetMapping("/verifyCode")
//	public String verifyCode(HttpServletRequest request, HttpServletResponse response) {
//		IVerifyCodeGen iVerifyCodeGen = new SimpleCharVerifyCodeGenImpl();
//		try {
//			//设置长宽
//			VerifyCode verifyCode = iVerifyCodeGen.generate(80, 28);
//			String code = verifyCode.getCode();
//			//将VerifyCode绑定session
//			request.getSession().setAttribute("VerifyCode", code);
//			//设置响应头
//			response.setHeader("Pragma", "no-cache");
//			//设置响应头
//			response.setHeader("Cache-Control", "no-cache");
//			//在代理服务器端防止缓冲
//			response.setDateHeader("Expires", 0);
//			//设置响应内容类型
//			response.setContentType("image/jpeg");
//			response.getOutputStream().write(verifyCode.getImgBytes());
//			response.getOutputStream().flush();
//		} catch (IOException e) {
//			logger.info("", e);
//		}
//		throw new RuntimeException();
//	}
//}
