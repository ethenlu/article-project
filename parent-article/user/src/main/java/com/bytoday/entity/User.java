package com.bytoday.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
	private String id;
	private String username;
	private String password;
	private String role;
	private Integer isDeleted;
	private Integer isBanned;
	private Date createTime;
	private Integer isVip;
	private String birthday;
	private String email;
	private boolean delivery;
	private String avatar;

}
