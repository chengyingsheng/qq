package com.cheng.qq.server;

import com.cheng.qq.common.Message;
import com.cheng.qq.common.UserValue;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ServerReceive extends Thread {
    public boolean isStop;
    private JTextArea messageHistory;
    private JComboBox combobox;
    private UserValue user;
    private Map<String, UserValue> users;

    public ServerReceive(JTextArea textarea, JComboBox combobox,
                         UserValue user, Map<String, UserValue> users) {

        this.messageHistory = textarea;
        this.user = user;
        this.users = users;
        this.combobox = combobox;
        this.isStop = false;
    }

    public void run() {
        while (!isStop && !user.getSocket().isClosed()) {//
            try {
                Message message = (Message) user.getInput().readObject();
                // 转发聊天信息
                if (message.getMessageType().equals("聊天")) {
                    if (message.getMessageTo().equals("所有人")) {
                        messageHistory.append(message.getMessageFrom()
                                + "对所有人说" + message.getMessageContent() + "\n");
                        sendMessageToAll(message);
                    } else {
                        String to = message.getMessageTo();
                        UserValue user = users.get(to);
                        user.getOutput().writeObject(message);
                    }
                } else
                    // 转发用户下线信息
                    if (message.getMessageType().equals("用户下线")) {
                        String from = message.getMessageFrom();
                        users.remove(from);
                        sendMessageToAll(message);
                        messageHistory.append("[系统消息] 用户：" + from + "下线" + "\n");
                        combobox.removeItem(message.getMessageFrom());
                    } else
                        // 转发用户上线信息
                        if (message.getMessageType().equals("我上线")) {
                            String to = message.getMessageTo();
                            UserValue user = users.get(to);
                            user.getOutput().writeObject(message);
                        } else
                            // 处理传送文件
                            if (message.getMessageType().equals("传送文件")) {
                                // String from = message.getMessageFrom();
                                String to = message.getMessageTo();
                                if (to.equals("所有人")) {
                                    String path = "C:";
                                    // 接受文件
                                    receiveFile(path, message);
                                    //将文件转发给所有在线的人
                                    forwardingFileToAll(message);

                                } else {
                                    // 转发文件
                                    UserValue user = users.get(to);
                                    forwardingFile(user, message);
                                }

                            }
            } catch (Exception e) {
            }
        }
    }


    /**
     * 给所有在线用户发送消息
     *
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

    /**
     * 接受文件
     *
     * @param path    接受文件的保存路径
     * @param message 发送者给接受的者得提示信息
     */
    public void receiveFile(String path, Message message) {

        try {

            ObjectInputStream input = user.getInput();
            FileOutputStream file = new FileOutputStream(path + "\\"
                    + message.getMessageContent());
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

    /**
     * 转发文件
     *
     * @param userA   转发文件的接受者
     * @param message 发送者发给接受者得提示信息
     */
    public void forwardingFile(UserValue userA, Message message) {

        try {

            ObjectInputStream input = user.getInput();
            ObjectOutputStream output = userA.getOutput();
            userA.getOutput().writeObject(message);

            byte[] bytes = new byte[1024];
            int cum = input.read(bytes);
            while (cum != -1) {
                output.write(bytes, 0, cum);
                cum = input.read(bytes);
                System.out.println(bytes);
            }
            output.flush();

        } catch (Exception e) {
        }
    }

    private void forwardingFileToAll(Message message) {

        try {
            Set<String> key = users.keySet();
            Iterator<String> it = key.iterator();
            while (it.hasNext()) {
                String a = it.next();
                forwardingFile(users.get(a), message);
            }
        } catch (Exception e) {
        }
    }

}
