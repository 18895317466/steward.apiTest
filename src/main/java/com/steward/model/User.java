package com.steward.model;

import java.io.Serializable;

/**
 * @author OYSF
 *
 * 2018年3月16日-下午4:41:47
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5813577395167408289L;
	
	private int id;
	
	private String name;
	
	private String pwd;
	
	private String telphone;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	
	
}
