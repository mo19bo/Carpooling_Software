package com.example.carpool;

import java.util.Date;

import android.app.Application;

public class MyStatus extends Application {
	

	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUrlString() {
		return urlString;
	}
	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getDriverlicense() {
		return driverlicense;
	}
	public void setDriverlicense(String driverlicense) {
		this.driverlicense = driverlicense;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public int getIsdriver() {
		return isdriver;
	}
	public void setIsdriver(int isdriver) {
		this.isdriver = isdriver;
	}
	public int getIscustomer() {
		return iscustomer;
	}
	public void setIscustomer(int iscustomer) {
		this.iscustomer = iscustomer;
	}
	private Boolean status = false;
	private String username = "";
	private String urlString = "http://1.miqilin.sinaapp.com/";
	private String phonenumber = "";
	private int  sex = 0;
	private String introduction = "";
	private String driverlicense = "";
	private String idcard ="";
	private int  isdriver =0;
	private int iscustomer =0;
	private Date curtime;
	private float drivercredit;
	private String job;
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public float getDrivercredit() {
		return drivercredit;
	}
	public void setDrivercredit(float drivercredit) {
		this.drivercredit = drivercredit;
	}
	public float getPassengercredit() {
		return passengercredit;
	}
	public void setPassengercredit(float passengercredit) {
		this.passengercredit = passengercredit;
	}
	private float passengercredit;
	public Date getCurtime() {
		return curtime;
	}
	public void setCurtime(Date curtime) {
		this.curtime = curtime;
	}
}
