package com.steward.dto.card;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class ConsumeListDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8630749983732999816L;
	
	private String cardNo;
	private BigDecimal account;
	private String acctNo;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date addDate;
	
	private int toalNum;
	private BigDecimal sumAccount;
	
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public BigDecimal getAccount() {
		return account;
	}
	public void setAccount(BigDecimal account) {
		this.account = account;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public int getToalNum() {
		return toalNum;
	}
	public void setToalNum(int toalNum) {
		this.toalNum = toalNum;
	}
	public BigDecimal getSumAccount() {
		return sumAccount;
	}
	public void setSumAccount(BigDecimal sumAccount) {
		this.sumAccount = sumAccount;
	}
	
	
}
