package com.bytoday.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "demo_collection")
public class SocketMsg implements Serializable {
	private int type; //聊天类型0：群聊，1：单聊.
	private String fromUser;//发送者.
	private String toUser;//接受者.
	private String msg;//消息
	private String tid;
}
