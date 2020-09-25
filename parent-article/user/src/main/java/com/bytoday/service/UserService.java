package com.bytoday.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bytoday.common.req.RegisterREQ;
import com.bytoday.entity.Friend;
import com.bytoday.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
	User login(User user);
	void getRegInfo(RegisterREQ registerREQ);
	Friend chooseAFriend(String id);
	List<Friend> getFrindList(String id);
}
