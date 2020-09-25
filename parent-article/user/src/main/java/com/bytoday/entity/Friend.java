package com.bytoday.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Friend implements Serializable {
	private String id;
	private String username;
	private String avatar;

}
