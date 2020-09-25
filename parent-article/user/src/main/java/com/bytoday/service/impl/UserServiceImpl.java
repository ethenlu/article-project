package com.bytoday.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bytoday.common.req.RegisterREQ;
import com.bytoday.dao.FriendMapper;
import com.bytoday.dao.RegisterREQMapper;
import com.bytoday.dao.UserMapper;
import com.bytoday.entity.Friend;
import com.bytoday.entity.User;
import com.bytoday.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RegisterREQMapper registerREQMapper;
	@Autowired
	private FriendMapper friendMapper;


	@Override
	@Transactional
	public User login(User user) {
		User userDB = userMapper.login(user);
		if(userDB!=null){
			return userDB;
		}
		throw new RuntimeException("登录失败");
	}

	@Override
	public void getRegInfo(RegisterREQ registerREQ) {
		 registerREQMapper.insert(registerREQ);
	}

	@Override
	public Friend chooseAFriend(String id) {
		return friendMapper.chooseAFriend(id);
	}

	@Override
	public List<Friend> getFrindList(String id) {
		return friendMapper.getFrindList(id);
	}


}
