package com.cheng.qq.client;


import com.cheng.qq.client.ui.ClientMainGUI;
import com.cheng.qq.common.Message;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientReceive extends Thread {

	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ClientMainGUI clientMainGUI;

	public ClientReceive(ClientMainGUI clientMainGUI, Socket socket,
						 ObjectOutputStream output, ObjectInputStream input) {
		this.clientMainGUI = clientMainGUI;
		this.socket = socket;
		this.output = output;
		this.input = input;
	}

	public void run() {
		while (!socket.isClosed()) {
			try {
				Message message = (Message) input.readObject();
				// 处理用户下线信息
				if (message.getMessageType().equalsIgnoreCase("用户下线")) {

					clientMainGUI.getUserListBox().removeItem(
							message.getMessageFrom());
					clientMainGUI.getHistory().append(
							"[系统消息] 用户：" + message.getMessageFrom() + "下线"
									+ "\n");

				} else
					// 处理用户上线信息
					if (message.getMessageType().equalsIgnoreCase("用户上线")) {

						clientMainGUI.getUserListBox().addItem(
								message.getMessageFrom());
						clientMainGUI.getHistory().append(
								"[系统消息] 用户：" + message.getMessageFrom() + "上线"
										+ "\n");
						Message message1 = new Message();
						message1.setMessageType("我上线");
						message1.setMessageFrom(this.getName());
						message1.setMessageTo(message.getMessageFrom());
						ClientConnect.getInstance(null, null, null, null)
								.sendMessage(message1);
					} else
						// 处理用户在线信息
						if (message.getMessageType().equalsIgnoreCase("我上线")) {
							clientMainGUI.getUserListBox().addItem(
									message.getMessageFrom());
						} else
							// 处理用服务器关闭信息------------没有做
							if (message.getMessageType().equalsIgnoreCase("服务关闭")) {
								output.close();
								input.close();
								socket.close();
							} else
								// 处理系统信息
								if (message.getMessageType().equalsIgnoreCase("系统消息")) {
									clientMainGUI.getHistory().append(
											"" + message.getMessageFrom() + "说"
													+ message.getMessageContent() + "\n");

								} else
									// 处理聊天信息
									if (message.getMessageType().equalsIgnoreCase("聊天")) {
										clientMainGUI.getHistory().append(
												" 用户：" + message.getMessageFrom() + "说"
														+ message.getMessageContent() + "\n");
									} else
										// 传送文件
										if (message.getMessageType().equalsIgnoreCase("传送文件")) {
											String path = "D:";
											clientMainGUI.getHistory().append(
													" 用户：" + message.getMessageFrom() + "给你发送了文件："
															+ message.getMessageContent() + "    文件保存在：" + path + "\n");
											//接收文件信息

											receiveFile(path, message);
										}
			} catch (Exception e) {
			}
		}
	}


	/**
	 * 接受文件
	 *
	 * @param path
	 * @param message
	 */
	public void receiveFile(String path, Message message) {

		try {

			FileOutputStream file = new FileOutputStream(path + "\\" + message.getMessageContent());
			byte[] bytes = new byte[1024];
			int cum = input.read(bytes);
			while (cum != -1) {
				file.write(bytes, 0, cum);
				cum = input.read(bytes);
			}
			file.flush();
			file.close();

		} catch (Exception e) {
		}
	}
}
