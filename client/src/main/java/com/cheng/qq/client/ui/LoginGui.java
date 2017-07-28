package com.cheng.qq.client.ui;

import com.cheng.qq.client.ClientConnect;
import com.cheng.qq.common.SwingResourceManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginGui extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	//登入界面显示的位置
	private static int Client_Location_X;
	private static int Client_Location_Y;
	//

	//登入界面的大小
	private static int Client_Wide = 350;
	private static int Client_High = 250;
	//
	private static LoginGui loginGUI;
	private static ClientMainGUI c;
	//用户账号，用户密码
	public JTextField UsernametextField;
	private JPasswordField passwordField;
	//按钮
	private JButton button_1 = new JButton("登入");
	private JButton button = new JButton("注册");
	private ClientConnect client;

	public LoginGui() {

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("账号");
		label.setBounds(35, 90, 39, 25);
		contentPane.add(label);

		UsernametextField = new JTextField("cheng");
		UsernametextField.setBounds(84, 90, 191, 25);
		contentPane.add(UsernametextField);
		UsernametextField.setColumns(10);

		JLabel label_1 = new JLabel("密码");
		label_1.setBounds(35, 132, 39, 18);
		contentPane.add(label_1);

		passwordField = new JPasswordField("124817");
		passwordField.setBounds(84, 131, 191, 25);
		contentPane.add(passwordField);


		button_1.setBounds(249, 179, 75, 25);
		contentPane.add(button_1);
		UsernametextField.setColumns(10);


		button.setBounds(8, 179, 66, 25);
		contentPane.add(button);

		JLabel label_2 = new JLabel("图片");
		label_2.setBounds(0, 0, 335, 80);

		contentPane.add(label_2);
		button.addActionListener(this);
		button_1.addActionListener(this);
	}

	public static void main(String[] args) {
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			loginGUI = new LoginGui();
			loginGUI.laughLogin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载登入界面
	 */
	public void laughLogin() {
		Client_Location_X = (int) ((this.getToolkit().getScreenSize()
				.getWidth() - Client_Wide) / 2);
		Client_Location_Y = (int) ((this.getToolkit().getScreenSize()
				.getHeight() - Client_High) / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("登入界面");
		this.setResizable(false);
		this.setBounds(Client_Location_X, Client_Location_Y, Client_Wide,
				Client_High);
		this.setIconImage(SwingResourceManager.getImage(this.getClass().getResource("/").getPath() + "images/icon.png"));
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == button_1) {
			//得到用户名和密码
			String username = UsernametextField.getText();
			String password = passwordField.getPassword().toString();
			//创建主窗口
			c = new ClientMainGUI(username);
			//连接服务器
			client = ClientConnect.getInstance(loginGUI, c, username, password);
			client.Connect();

		} else if (e.getSource() == button) {

		}
	}


}
