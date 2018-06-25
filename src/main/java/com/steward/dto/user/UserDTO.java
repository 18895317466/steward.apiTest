package com.steward.dto.user;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class UserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5353503502579443490L;

	private String id;

    private String shopId;

    private String userName;

    private String empInfoId;

    private String empName;

    private String defShowShop;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date addDate;

    private String canShowShop;

    private String accesskey;
    
    private String token;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmpInfoId() {
		return empInfoId;
	}

	public void setEmpInfoId(String empInfoId) {
		this.empInfoId = empInfoId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDefShowShop() {
		return defShowShop;
	}

	public void setDefShowShop(String defShowShop) {
		this.defShowShop = defShowShop;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getCanShowShop() {
		return canShowShop;
	}

	public void setCanShowShop(String canShowShop) {
		this.canShowShop = canShowShop;
	}

	public String getAccesskey() {
		return accesskey;
	}

	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
    
}
