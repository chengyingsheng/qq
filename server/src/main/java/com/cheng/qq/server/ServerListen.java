package com.cheng.qq.server;

import com.cheng.qq.common.Message;
import com.cheng.qq.common.UserValue;

import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.*;


public class ServerListen extends Thread {
	private ServerSocket server;
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	//在线用户的输入输出流
	private Map<String,UserValue> users;
	private ServerReceive recvThread;
	
	private JComboBox usersBox;
	private JTextArea historyArea;
	
	public boolean isStop;
	
	public ServerListen(ServerSocket server,JComboBox combobox,
		JTextArea textarea,Map<String,UserValue> users){

		this.server = server;
		this.usersBox = combobox;
		this.historyArea = textarea;
		this.users = users;
		this.isStop = false;
	}



	public void run(){
		while(!isStop && !server.isClosed()){
			try{
				//等待连接
				socket = server.accept();
				output = new ObjectOutputStream(socket.getOutputStream());
				output.flush();
				input  = new ObjectInputStream(socket.getInputStream());
				
				//获得登入请求
				Message meaasge =  (Message) input.readObject();
				if(meaasge.getMessageType().equals("登入")){
					//得到登入信息
					String username = meaasge.getMessageFrom();
					String password = meaasge.getMessageContent();
					//验证登入信息
					//---------没有做---------------
					
					//发送登入的验证信息
					Message meaasge1 = new Message();
					meaasge1.setMessageType("登入");
					meaasge1.setMessageContent("登入成功");
					output.writeObject(meaasge1);
					
					//显示新用户登入
					historyArea.append("[系统消息] 用户："+username+" 登入"+"\n");
					
					//通知所有在线用户，有新用户登入
					Message message = new Message();
					message.setMessageType("用户上线");
					message.setMessageFrom(username);
					sendMessageToAll(message);
					//保存新登入用户的输入输出流
					UserValue user = new UserValue();
					user.setInput(input);
					user.setOutput(output);
					user.setSocket(socket);
					user.setPassword(password);
					users.put(username, user);

					//将新登入的用户添加的在线用户列表
					usersBox.addItem(username);
					//启动该用户在服务器端线程
					recvThread = new ServerReceive(historyArea,usersBox,user,users);
					recvThread.setName(username);
					recvThread.start();
				}
			}
			catch(Exception e){
			}
		}
	}
	
	/**
	 * 给所有在线用户发送消息
	 * @param message 要发送的消息对象
	 */
	public void sendMessageToAll(Message message) {

		try {
			Set<String> key = users.keySet();
			Iterator<String> it = key.iterator();
			while (it.hasNext()) {
				String a = it.next();
				users.get(a).getOutput().writeObject(message);
			}
		} catch (Exception e) {
		}
	}
}
