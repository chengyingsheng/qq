package com.cheng.qq.client;


import com.cheng.qq.client.ui.ClientMainGUI;
import com.cheng.qq.client.ui.LoginGui;
import com.cheng.qq.common.Message;

import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

public class ClientConnect {

	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ClientReceive recvThread;
	private String username;
	private String password;
	private LoginGui loginGUI;
	private ClientMainGUI c;
	private static ClientConnect ds = null;

	public ClientConnect(LoginGui loginGUI, ClientMainGUI c, String username,
						 String password) {
		this.loginGUI = loginGUI;
		this.c = c;
		this.username = username;
		this.password = password;

	}

	public static ClientConnect getInstance(LoginGui loginGUI, ClientMainGUI c,
											String username, String password) {
		if (ds == null) {
			ds = new ClientConnect(loginGUI, c, username, password);
		}
		return ds;
	}
	/**
	 * 连接服务器
	 */
	public void Connect() {
		try {
			//构建输入输出流
			socket = new Socket("127.0.0.1", 8000);
			output = new ObjectOutputStream(socket.getOutputStream());
			output.flush();
			input = new ObjectInputStream(socket.getInputStream());
			//构造登入信息
			Message sendMessage = new Message();
			sendMessage.setMessageType("登入");
			sendMessage.setMessageFrom(username);
			sendMessage.setMessageContent(password);
			//发送登入信息
			output.writeObject(sendMessage);
			output.flush();

			//得到返回信息
			Message receiveMessage = new Message();
			receiveMessage = (Message) input.readObject();
			//判断是否登入成功
			if (receiveMessage.getMessageType().equals("登入")
					&& receiveMessage.getMessageContent().equals("登入成功")) {
				//登入成功显示主界面
				c.laughClient();
				loginGUI.setVisible(false);
				c.setUsername(username);
				//启动客户端信息接收线程
				recvThread = new ClientReceive(c, socket, output, input);
				recvThread.setName(username);
				recvThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//断开和服务器的连接
	public void DisConnect() {

		if (socket.isClosed()) {
			return;
		}
		try {
			Message message = new Message();
			message.setMessageType("用户下线");
			message.setMessageFrom(username);
			message.setMessageTo("所有人");
			sendMessage(message);
			input.close();
			output.close();
			socket.close();
		} catch (Exception e) {
		}
	}
	/**
	 * 发送一个消息
	 * @param message 要发送的消息对象
	 */
	public void sendMessage(Message message) {

		try {
			output.writeObject(message);
			output.flush();
		} catch (Exception e) {
		}

	}

	public void SendFile(String to , File file) {

		try {
			Message m = new Message();
			m.setMessageType("传送文件");
			m.setMessageFrom(username);
			m.setMessageTo(to);
			m.setMessageContent(file.getName());
			output.writeObject(m);
			output.flush();
			System.out.println(m.toString());
			FileInputStream fileStream;
			try {
				fileStream = new FileInputStream(file);
				byte[] bytes = new byte[1024];
				int cont = 0;
				while((cont = fileStream.read(bytes))!=-1)
				{
					output.write(bytes,0,cont);
					System.out.println(bytes);
				}
				fileStream.close();
				JOptionPane.showMessageDialog(null,"上传完毕");
			} catch (Exception e) {
				e.printStackTrace();
			}


		} catch (Exception e) {
		}

	}
}
