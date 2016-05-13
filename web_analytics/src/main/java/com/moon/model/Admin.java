package com.moon.model;

public class Admin {
	private int userId;
	private String userName;
	private String passWord;
	private String again_pwd;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getAgain_pwd() {
		return again_pwd;
	}
	public void setAgain_pwd(String again_pwd) {
		this.again_pwd = again_pwd;
	}
	
}
