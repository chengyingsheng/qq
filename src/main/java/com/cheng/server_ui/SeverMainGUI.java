package com.cheng.server_ui;

import java.awt.event.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.cheng.client_entity.Message;
import com.cheng.client_tool.SwingResourceManager;
import com.cheng.server_entity.UserValue;
import com.cheng.server_tcp.ServerListen;

public class SeverMainGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	//
	private ServerSocket serverSocket;
	private ServerListen listenThread;
	// 在线用户
	private JComboBox userBox;
	// 发送消息框
	private JTextArea messageHistory;
	private JTextArea messageArea;
	// 在线用户的输入输出流
	private Map<String, UserValue> users;
	// 按钮组
	private JButton serverSet = new JButton("服务器设置");
	private JButton startServer = new JButton("启动服务器");
	private JButton stopServer = new JButton("停止服务器");
	private JButton exitServer = new JButton("退出系统");
	private JButton button = new JButton("浏览");
	private JButton button_2 = new JButton("发送文件");
	private JButton button_1 = new JButton("发送");
	private JMenuItem serverSetup;
	private JMenu help;

	public static void main(String[] args) {

		JFrame.setDefaultLookAndFeelDecorated(true);
		SeverMainGUI SeverMainGUI = new SeverMainGUI();
		SeverMainGUI.laughServer();
	}

	/**
	 * 加载主界面
	 */
	public void laughServer() {
		this.setIconImage(SwingResourceManager.getImage("images\\icon.png"));
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent arg0) {
				// 发送服务器关闭消息
				stopService();
				System.exit(0);

			}

		});
		this.setTitle("Server");
		this.setBounds(100, 100, 400, 600);
		this.setResizable(false);
		this.setVisible(true);
		this.setIconImage(SwingResourceManager.getImage(this.getClass().getResource("/").getPath()+"images/icon.png"));
	}

	public SeverMainGUI() {

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 396, 21);
		contentPane.add(menuBar);

		JMenu menu = new JMenu("菜单");
		menuBar.add(menu);

		serverSetup = new JMenuItem("服务器设置");
		menu.add(serverSetup);

		help = new JMenu("帮助");
		menuBar.add(help);

		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 25, 396, 25);
		contentPane.add(toolBar);

		toolBar.add(serverSet);
		toolBar.addSeparator();

		toolBar.add(startServer);
		toolBar.addSeparator();

		stopServer.setEnabled(false);
		toolBar.add(stopServer);
		toolBar.addSeparator();

		toolBar.add(exitServer);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 365, 60);
		contentPane.add(scrollPane);

		messageArea = new JTextArea();
		scrollPane.setViewportView(messageArea);

		JLabel label_3 = new JLabel("发送至");
		label_3.setBounds(10, 130, 48, 25);
		contentPane.add(label_3);

		userBox = new JComboBox();
		userBox.setBounds(68, 132, 120, 21);
		userBox.addItem("所有人");
		contentPane.add(userBox);

		button_1.setBounds(303, 130, 72, 25);
		contentPane.add(button_1);


		JLabel label_2 = new JLabel("消息记录");
		label_2.setBounds(10, 170, 128, 25);
		contentPane.add(label_2);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 205, 365, 347);
		contentPane.add(scrollPane_1);

		messageHistory = new JTextArea();
		messageHistory.setLineWrap(true);
		messageHistory.setEditable(false);
		scrollPane_1.setViewportView(messageHistory);

		serverSet.addActionListener(this);
		startServer.addActionListener(this);
		stopServer.addActionListener(this);
		exitServer.addActionListener(this);

		button.addActionListener(this);
		button_1.addActionListener(this);
		button_2.addActionListener(this);
		serverSetup.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e) {
		// 启动服务器
		if (e.getSource() == startServer) {
			startService();
			startServer.setEnabled(false);
			serverSet.setEnabled(false);
			stopServer.setEnabled(true);
			messageArea.setEnabled(true);
		} else
			// 停止服务器
			if (e.getSource() == stopServer) {
				int j = JOptionPane.showConfirmDialog(this, "真的停止服务吗?", "停止服务",
						JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (j == JOptionPane.YES_OPTION) {
					stopService();
				}
			} else if (e.getSource() == serverSet || e.getSource() == serverSetup) {
				ServerSetup serversetup = new ServerSetup();
				serversetup.laugh();
			} else
				// 关闭服务器
				if (e.getSource() == exitServer) {
					int j = JOptionPane.showConfirmDialog(this, "确认退出吗?", "退出",
							JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (j == JOptionPane.YES_OPTION) {
						stopService();
						System.exit(0);
					}
				} else
					// 发送系统消息
					if (e.getSource() == button_1) {
						Message m = new Message();
						String to = userBox.getSelectedItem().toString();
						String content = messageArea.getText();
						if (content == null || content.equals("")) {
							JOptionPane.showMessageDialog(this, "消息不能为空");
						} else {
							m.setMessageType("系统消息");
							m.setMessageFrom("系统");
							m.setMessageContent(content);
							if (to.equals("所有人")) {
								sendMessageToAll(m);
							} else {
								try {
									users.get(to).getOutput().writeObject(m);
								} catch (IOException e1) {
								}
							}
							// 将消息加到消息记录中
							messageHistory.append(messageArea.getText());
							messageArea.setText("");
						}
					} else
						// 发送文件
						if (e.getSource() == button_2) {
							if(userBox.getSelectedItem().equals("所有人")){
								//发送给所有人
							}else{
								//发送给指定人
							}

						}
	}

	/**
	 * 启动服务端
	 */
	public void startService() {
		try {
			serverSocket = new ServerSocket(8000, 10);
			messageHistory.append("[系统消息]服务端已经启动，在8000端口侦听...\n");

		} catch (Exception e) {
		}
		users = new HashMap<String, UserValue>();
		// 启动服务器监听线程
		listenThread = new ServerListen(serverSocket, userBox, messageHistory,
				users);
		listenThread.start();
	}

	/**
	 * 停止服务器
	 */
	public void stopService() {
		// 构造服务器关闭消息
		Message m = new Message();
		m.setMessageType("系统消息");
		m.setMessageContent("服务器将要关闭");
		m.setMessageFrom("系统");
		sendMessageToAll(m);

		closeAllUserSocket();

		stopServer.setEnabled(false);
		startServer.setEnabled(true);
		serverSet.setEnabled(true);
	}

	/**
	 * 给所有在线用户发送消息
	 *
	 * @param message
	 *            要发送的消息对象
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

	public void closeAllUserSocket() {

		try {
			Set<String> key = users.keySet();
			Iterator<String> it = key.iterator();
			while (it.hasNext()) {
				String a = it.next();
				users.remove(a);
			}
			listenThread.isStop = true;
			serverSocket.close();
			System.out.println(serverSocket.isClosed());

		} catch (Exception e) {
		}
	}
}
