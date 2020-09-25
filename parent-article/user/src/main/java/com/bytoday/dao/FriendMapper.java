package com.bytoday.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bytoday.entity.Friend;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FriendMapper extends BaseMapper<Friend> {
	@Select("SELECT `user`.`id`,`user`.`username`,`user`.`avatar` FROM `user` WHERE id = #{id}")
	Friend chooseAFriend(String id);

	@Select("SELECT `user`.`id`,`user`.`username`,`user`.`avatar` FROM `user` ,`friendship` WHERE `friendship`.`uid` = #{id} AND `friendship`.`fid` = `user`.`id`")
	List<Friend> getFrindList(String id);
}
