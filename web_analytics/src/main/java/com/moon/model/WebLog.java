package com.moon.model;

public class WebLog {

	private  String  logdate;
	private  int  pv;
	private  int reguser;
	private  int ip;
	private  int jumper;
	
	public String getLogdate() {
		return logdate;
	}
	public void setLogdate(String logdate) {
		this.logdate = logdate;
	}
	public int getPv() {
		return pv;
	}
	public void setPv(int pv) {
		this.pv = pv;
	}
	public int getReguser() {
		return reguser;
	}
	public void setReguser(int reguser) {
		this.reguser = reguser;
	}
	public int getIp() {
		return ip;
	}
	public void setIp(int ip) {
		this.ip = ip;
	}
	public int getJumper() {
		return jumper;
	}
	public void setJumper(int jumper) {
		this.jumper = jumper;
	}
	
}
