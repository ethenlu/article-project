package com.bytoday.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bytoday.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {
	@Select("SELECT * FROM USER WHERE #{username} = username AND #{password} = password")
	User login(User user);

	@Select("select `user`.`id` from `user` where `user`.`username` = #{username}")
	String getTid(String username);
}
