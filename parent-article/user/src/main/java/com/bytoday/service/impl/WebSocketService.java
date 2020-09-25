package com.bytoday.service.impl;

import com.bytoday.dao.UserMapper;
import com.bytoday.entity.SocketMsg;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@ServerEndpoint("/websocket/{myName}")
public class WebSocketService {
	@Autowired
	private UserMapper mapper;
	@Autowired
	private MongoTemplate mongoTemplate;

	private String myName;
	//用来存放每个客户端对应的MyWebSocket对象。
	private static CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<WebSocketService>();
	//与某个客户端的连接会话，需要通过它来给客户端发送数据
	//用来记录sessionId和该session进行绑定
	private static Map<String, Session> map = new HashMap<String, Session>();

	@OnOpen
	public void onOpen(Session session, @PathParam("myName") String myName) {
		webSocketSet.add(this);//加入set中
		map.put(myName, session);
		System.out.println("连接成功");
		System.out.println(map);
	}

	@OnClose
	public void onClose() {
		webSocketSet.remove(this); //从set中删除
		System.out.println("连接断开");
	}

	@OnMessage
	public void onMessage(String message, Session session, @PathParam("myName") String myName) {
		System.out.println("来自客户端的消息-->" + myName + ": " + message);

		//从客户端传过来的数据是json数据，所以这里使用jackson进行转换为SocketMsg对象，
		// 然后通过socketMsg的type进行判断是单聊还是群聊，进行相应的处理:
		ObjectMapper objectMapper = new ObjectMapper();
		SocketMsg socketMsg;

		try {

			socketMsg = objectMapper.readValue(message, SocketMsg.class);
			if (socketMsg.getType() == 0) {
				//单聊.需要找到发送者和接受者.
				socketMsg.setFromUser(session.getId());//发送者.
				Session fromSession = map.get(myName);
				System.out.println("from: "+fromSession);
				Session toSession = map.get(socketMsg.getToUser());
				System.out.println("to: "+toSession);
				//发送给接受者.
				if (toSession != null) {
					//发送给发送者.
					Map<String,Object> m=new HashMap<String, Object>();
					m.put("type",1);
					m.put("from",myName);
					m.put("msg",socketMsg.getMsg());
					m.put("to",socketMsg.getToUser());
					System.out.println(m);
					fromSession.getAsyncRemote().sendText(new Gson().toJson(m));
					toSession.getAsyncRemote().sendText(new Gson().toJson(m));
				} else {
					//发送给发送者.
					Map<String,Object> m=new HashMap<String, Object>();
					m.put("msg","系统消息：对方不在线或者您输入的频道号不对");
					fromSession.getAsyncRemote().sendText(new Gson().toJson(m));
				}
			}

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}
}
