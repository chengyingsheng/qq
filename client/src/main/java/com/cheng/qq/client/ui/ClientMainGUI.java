package com.cheng.qq.client.ui;

import com.cheng.qq.client.ClientConnect;
import com.cheng.qq.common.Message;
import com.cheng.qq.common.SwingResourceManager;

import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class ClientMainGUI extends JFrame implements ActionListener {


	private static final long serialVersionUID = 5219230490278429067L;
	//主界面大小及显示位置
	private static int Client_Wide = 400;
	private static int Client_High = 600;
	private static int Client_Location_X ;
	private static int Client_Location_Y ;

	//文件地址显示
	private JTextField textField;
	//消息发送区域
	private JTextArea messageTextArea = new JTextArea();
	//在线人员列表
	private JComboBox userListBox = new JComboBox();
	private JButton sendMessage = new JButton("发送消息");
	private JButton fileChange = new JButton("...");
	private JButton sendFile = new JButton("发送");
	//聊天记录
	private JTextArea history = new JTextArea();
	private String username;
	private JLabel label_4;
	private JMenuItem out;
	private JMenuItem historyItem;
	private JMenuItem clientsetup;
	private JMenuItem help;
	private File sendfile;
	private String filesavepath;


	/**
	 * 加载主界面
	 */
	public void laughClient(){

		Client_Location_X = (int) ((this.getToolkit().getScreenSize().getWidth()-Client_Wide)/2);
		Client_Location_Y = (int) ((this.getToolkit().getScreenSize().getHeight()-Client_High)/2);
		this.setBounds(Client_Location_X, Client_Location_Y, Client_Wide, Client_High);
		this.setIconImage(SwingResourceManager.getImage("images\\icon.png"));
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0) {
				//关闭主界面时发送下线消息
				ClientConnect.getInstance(null, null, null, null).DisConnect();
				System.exit(0);
			}
		});
		this.setTitle("Client");
		this.setResizable(false);
		this.setVisible(true);
	}

	public ClientMainGUI(String username) {

		this.username = username;
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 396, 21);
		contentPane.add(menuBar);

		JMenu menu = new JMenu("菜单");
		menuBar.add(menu);

		clientsetup = new JMenuItem("设置");
		menu.add(clientsetup);

		historyItem = new JMenuItem("历史记录");
		menu.add(historyItem);

		help = new JMenuItem("帮助");
		menu.add(help);

		out = new JMenuItem("退出");
		menu.add(out);

		label_4 = new JLabel("欢迎 "+username);
		label_4.setBounds(10, 31, 365, 25);
		contentPane.add(label_4);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 365, 60);
		contentPane.add(scrollPane);

		scrollPane.setViewportView(messageTextArea);

		JLabel label_3 = new JLabel("发送至");
		label_3.setBounds(10, 130, 48, 25);
		contentPane.add(label_3);

		userListBox.setBounds(68, 132, 120, 21);
		userListBox.addItem("所有人");
		contentPane.add(userListBox);

		sendMessage.setBounds(268, 130, 100, 25);
		contentPane.add(sendMessage);

		JLabel label = new JLabel("发送文件");
		label.setBounds(10, 165, 53, 25);
		contentPane.add(label);

		textField = new JTextField();
		textField.setBounds(68, 165, 190, 25);
		contentPane.add(textField);
		textField.setColumns(10);

		fileChange.setBounds(268, 165, 25, 25);
		contentPane.add(fileChange);

		sendFile.setBounds(303, 165, 72, 25);
		contentPane.add(sendFile);


		JLabel label_2 = new JLabel("消息记录");
		label_2.setBounds(10, 200, 128, 25);
		contentPane.add(label_2);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 235, 365, 317);
		contentPane.add(scrollPane_1);

		history.setLineWrap(true);
		history.setEditable(false);
		scrollPane_1.setViewportView(history);

		sendMessage.addActionListener(this);
		fileChange.addActionListener(this);
		sendFile.addActionListener(this);
		out.addActionListener(this);
		historyItem.addActionListener(this);
		clientsetup.addActionListener(this);
		help.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		//发送消息
		if(e.getSource() == sendMessage){
			//得到消息内容
			String from = username;
			String to = userListBox.getSelectedItem().toString();
			String content = messageTextArea.getText();
			if(content==null||content.equals("")){
				JOptionPane.showMessageDialog(this, "消息不能为空");
			}
			else{
				//构造消息
				Message message = new Message();
				message.setMessageContent(content);
				message.setMessageFrom(from);
				message.setMessageTo(to);
				message.setMessageType("聊天");
				//发送消息
				ClientConnect.getInstance( null,null, null, null).sendMessage(message);
				//将发送的消息添加到聊天记录中
				//history.append(messageTextArea.getText()+"\n");
				messageTextArea.setText("");
			}

		} else if(e.getSource() == fileChange){
			JFileChooser cho = new JFileChooser();
			int ch=cho.showOpenDialog(this);
			if(ch==JFileChooser.APPROVE_OPTION)
			{
				//得到选择的文件
				sendfile = cho.getSelectedFile();
				textField.setText(sendfile.getPath());
			}
		} else if(e.getSource() == sendFile){
			//发送上传文件提示信息

			//上传文件
			ClientConnect.getInstance(null, null, null, null).SendFile(userListBox.getSelectedItem().toString(), sendfile);
		}else if(e.getSource() == out){
			//关闭主界面时发送下线消息
			ClientConnect.getInstance(null, null, null, null).DisConnect();
			System.exit(0);
		}
		else if(e.getSource() == historyItem){

		}
		else if(e.getSource() == clientsetup){
			ClientSetup clientsetup = new ClientSetup(filesavepath);
			clientsetup.laugh();
		}
		else if(e.getSource() == help){
			Help dialog = new Help();
			dialog.laugh();
		}

	}

	public JComboBox getUserListBox() {
		return userListBox;
	}

	public void setUserListBox(JComboBox userListBox) {
		this.userListBox = userListBox;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public JTextArea getHistory() {
		return history;
	}

	public void setHistory(JTextArea history) {
		this.history = history;
	}

	public String getFilesavepath() {
		return filesavepath;
	}

	public void setFilesavepath(String filesavepath) {
		this.filesavepath = filesavepath;
	}

}
