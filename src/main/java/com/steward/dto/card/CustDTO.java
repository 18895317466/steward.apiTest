package com.steward.dto.card;

import java.io.Serializable;

public class CustDTO implements Serializable {

	/**
	 * 修复数据
	 */
	private static final long serialVersionUID = -7084185316136092025L;

	private String  custId;
	private String  customerID;
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	
	
}
