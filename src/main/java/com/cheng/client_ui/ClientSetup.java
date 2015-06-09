package com.cheng.client_ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ClientSetup extends JDialog implements ActionListener {

	private static final long serialVersionUID = -4198900740802914459L;
	private JTextField logtextField;
	private JTextField filetextField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JButton logbutton = new JButton("浏览");
	private JButton logbutton_1 = new JButton("确定");
	private JButton filebutton_1 = new JButton("浏览");
	private JButton filebutton_2 = new JButton("确认");


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			/*ClientSetup dialog = new ClientSetup();
			dialog.laugh();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void laugh() {
		this.setTitle("设置");
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	public ClientSetup(String filesavepath) {
		//this.filesavepath = filesavepath;
		setBounds(100, 100, 450, 200);
		getContentPane().setLayout(null);

		JLabel label = new JLabel("日志文件存储位置");
		label.setBounds(10, 10, 114, 25);
		getContentPane().add(label);

		textField_2 = new JTextField();
		textField_2.setBounds(124, 12, 300, 21);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);

		logtextField = new JTextField();
		logtextField.setBounds(46, 50, 239, 21);
		getContentPane().add(logtextField);
		logtextField.setColumns(10);

		logbutton.setBounds(304, 50, 45, 23);
		getContentPane().add(logbutton);

		logbutton_1.setBounds(359, 50, 66, 23);
		getContentPane().add(logbutton_1);

		JLabel label_1 = new JLabel("接收文件存储位置");
		label_1.setBounds(10, 84, 114, 25);
		getContentPane().add(label_1);

		textField_3 = new JTextField();
		textField_3.setBounds(125, 86, 300, 21);
		getContentPane().add(textField_3);
		textField_3.setColumns(10);

		filetextField_1 = new JTextField();
		filetextField_1.setBounds(46, 119, 239, 21);
		getContentPane().add(filetextField_1);
		filetextField_1.setColumns(10);

		filebutton_1.setBounds(303, 118, 45, 23);
		getContentPane().add(filebutton_1);

		filebutton_2.setBounds(364, 118, 61, 23);
		getContentPane().add(filebutton_2);

		logbutton.addActionListener(this);
		logbutton_1.addActionListener(this);
		filebutton_1.addActionListener(this);
		filebutton_2.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == logbutton) {
			JFileChooser cho = new JFileChooser();
			cho.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int ch = cho.showOpenDialog(this);
			if (ch == JFileChooser.APPROVE_OPTION) {
				String parh = cho.getSelectedFile().getPath();
				logtextField.setText(parh);
			}
		} else if (e.getSource() == logbutton_1) {
			textField_2.setText(logtextField.getText());
			logtextField.setText("");

		} else if (e.getSource() == filebutton_1) {
			JFileChooser cho = new JFileChooser();
			cho.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int ch = cho.showOpenDialog(this);
			if (ch == JFileChooser.APPROVE_OPTION) {
				String parh = cho.getSelectedFile().getPath();
				filetextField_1.setText(parh);
			}

		} else if (e.getSource() == filebutton_2) {
			textField_3.setText(filetextField_1.getText());
			//filesavepath = textField_3.getText();
			filetextField_1.setText("");
		}
	}
}
