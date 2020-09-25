package com.bytoday.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.bytoday.common.req.RegisterREQ;
import com.bytoday.common.utils.JWTUtil;
import com.bytoday.common.utils.RSAUtils;
import com.bytoday.common.verifyCode.IVerifyCodeGen;
import com.bytoday.common.verifyCode.SimpleCharVerifyCodeGenImpl;
import com.bytoday.common.verifyCode.VerifyCode;
import com.bytoday.entity.Friend;
import com.bytoday.entity.User;
import com.bytoday.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("user")
@Slf4j
@CrossOrigin
public class UserController {
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private UserService userService;

	@GetMapping("login")
	public Map<String, Object> login(@RequestParam("name")String name, @RequestParam("encrypted") String encrypted){
		Map<String, Object> map = new HashMap<>();
			User user = new User();
			try {
				String password = RSAUtils.decryptBase64(encrypted);
				user.setUsername(name);
				user.setPassword(password);
				User userDB = userService.login(user);
				HashMap<String, String> payload = new HashMap<>();
				payload.put("id",userDB.getId());
				payload.put("name",userDB.getUsername());
				payload.put("role",userDB.getRole());
				String token = JWTUtil.getToken(payload);
				redisTemplate.opsForValue().set("token",token);
				map.put("token",token);
				map.put("status",true);
				map.put("msg","登录成功");
			}catch (Exception e){
				map.put("status",false);
				map.put("msg","登录失败");

			}
		return map;
	}

	@PostMapping("verify")
	public Map<String,Object> verify(HttpServletRequest request){
		HashMap<String, Object> map = new HashMap<>();
		HashMap<String, Object> user = new HashMap<>();
		String token = request.getHeader("token");
		DecodedJWT verify = JWTUtil.verify(token);
		String id = verify.getClaim("id").asString();
		String name = verify.getClaim("name").asString();
		String role = verify.getClaim("role").asString();
		user.put("id",id);
		user.put("name",name);
		user.put("role",role);
		map.put("status",true);
		map.put("msg","请求成功");
		map.put("data",user);
		return map;
	}

	@PostMapping("register")
	public Map<String,Object> register(@RequestBody RegisterREQ req){
		HashMap<String, Object> map = new HashMap<>();
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setBirthday(req.getBirthday());
		user.setCreateTime(Calendar.getInstance().getTime());
		user.setEmail(req.getEmail());
		user.setUsername(req.getName());
		user.setPassword(req.getPassword());
		user.setDelivery(req.isDelivery());
		if(redisTemplate.opsForValue().get("code").toString().equalsIgnoreCase(req.getVerifyCode())){
			userService.save(user);
			map.put("status",true);
			map.put("msg","注册成功");
		}else{
			map.put("status",false);
			map.put("msg","注册失败");
		}
		return map;
	}

	@ApiOperation(value = "验证码")
	@GetMapping("verifyCode")
	public String verifyCode(HttpServletRequest request, HttpServletResponse response) {

		IVerifyCodeGen iVerifyCodeGen = new SimpleCharVerifyCodeGenImpl();

		try {
			//设置长宽
			VerifyCode verifyCode = iVerifyCodeGen.generate(80, 28);
			 String code = verifyCode.getCode();
			 redisTemplate.opsForValue().set("code",code);
			//将VerifyCode绑定session
			request.getSession().setAttribute("VerifyCode", code);
			//设置响应头
			response.setHeader("Pragma", "no-cache");
			//设置响应头
			response.setHeader("Cache-Control", "no-cache");
			//在代理服务器端防止缓冲
			response.setDateHeader("Expires", 0);
			//设置响应内容类型
			response.setContentType("image/jpeg");
			response.getOutputStream().write(verifyCode.getImgBytes());
			response.getOutputStream().flush();
		} catch (IOException e) {
			System.out.println(e);
		}
		throw new RuntimeException();
	}

	@GetMapping("encrypt")
	public String getKey(){
		String publicKey = RSAUtils.generateBase64PublicKey();
		return publicKey;
	}

	@GetMapping("getFriendInfo/{id}")
	public Friend getInfo(@PathVariable String id){
		Friend info = userService.chooseAFriend(id);
		return info;

	}

	@GetMapping("getFriendList")
	public List<Friend> getFriendList(){
		Object token = redisTemplate.opsForValue().get("token");
		DecodedJWT verify = JWTUtil.verify(token.toString());
		String tid = verify.getClaim("id").asString();
		List<Friend> frindList = userService.getFrindList(tid);
		return frindList;
	}

	@GetMapping("getMyInfo")
	public User getMyInfo(){
		Object token = redisTemplate.opsForValue().get("token");
		DecodedJWT verify = JWTUtil.verify(token.toString());
		String tid = verify.getClaim("id").asString();
		User myInfo = userService.getById(tid);
		return myInfo;
	}





}
