package com.steward.dto.card;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class SuccessDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2003076282726860568L;
	
	private BigDecimal account;
	private BigDecimal surplusBal;
	private int payType;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date addDate;
	private String acctNo;
	public BigDecimal getAccount() {
		return account;
	}
	public void setAccount(BigDecimal account) {
		this.account = account;
	}
	public BigDecimal getSurplusBal() {
		return surplusBal;
	}
	public void setSurplusBal(BigDecimal surplusBal) {
		this.surplusBal = surplusBal;
	}

	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	
	

}
