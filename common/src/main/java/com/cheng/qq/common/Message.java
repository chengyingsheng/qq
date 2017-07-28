package com.cheng.qq.common;

import java.io.Serializable;
 
public class Message implements Serializable
{
	
	private static final long serialVersionUID = 2438298835863086532L;
	private String messageType;
	private String messageFrom;
	private String messageTo;
	private String messageContent;
	
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getMessageFrom() {
		return messageFrom;
	}
	public void setMessageFrom(String messageFrom) {
		this.messageFrom = messageFrom;
	}
	public String getMessageTo() {
		return messageTo;
	}
	public void setMessageTo(String messageTo) {
		this.messageTo = messageTo;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	@Override
	public String toString() {
		return "Message [messageContent=" + messageContent + ", messageFrom="
				+ messageFrom + ", messageTo=" + messageTo + ", messageType="
				+ messageType + "]";
	}
}
