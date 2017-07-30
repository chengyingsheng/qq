package com.cheng.qq.client.ui;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Help extends JDialog implements ActionListener {

    private static final long serialVersionUID = 4953186894067408669L;
    private JButton button;

    /**
     * Create the dialog.
     */
    public Help() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(null);

        JLabel label = new JLabel("1");
        label.setBounds(10, 10, 414, 25);
        getContentPane().add(label);

        JLabel label_1 = new JLabel("2");
        label_1.setBounds(10, 45, 414, 25);
        getContentPane().add(label_1);

        JLabel label_2 = new JLabel("3");
        label_2.setBounds(10, 80, 414, 25);
        getContentPane().add(label_2);

        JLabel label_3 = new JLabel("4");
        label_3.setBounds(10, 115, 414, 25);
        getContentPane().add(label_3);

        JLabel label_4 = new JLabel("5");
        label_4.setBounds(10, 150, 414, 25);
        getContentPane().add(label_4);

        JLabel label_5 = new JLabel("6");
        label_5.setBounds(10, 185, 414, 25);
        getContentPane().add(label_5);

        button = new JButton("我知道了");
        button.setBounds(180, 229, 93, 23);
        getContentPane().add(button);

        button.addActionListener(this);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            Help dialog = new Help();
            dialog.laugh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void laugh() {
        this.setResizable(false);
        this.setTitle("帮助");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            System.exit(0);
        }
    }
}
