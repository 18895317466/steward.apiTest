package com.steward.dto.card;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class ConsumeDetDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5406100712750569839L;
	
	private String cardNo;
	private String shopName;
	private String remarks;
	private BigDecimal salesAmt;
	private BigDecimal account;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date addDate;
	
	private String acctNo;
	
	private String updateOpr;
	
	private BigDecimal surplusBal;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getSalesAmt() {
		return salesAmt;
	}

	public void setSalesAmt(BigDecimal salesAmt) {
		this.salesAmt = salesAmt;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
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

	public String getUpdateOpr() {
		return updateOpr;
	}

	public void setUpdateOpr(String updateOpr) {
		this.updateOpr = updateOpr;
	}

	public BigDecimal getSurplusBal() {
		return surplusBal;
	}

	public void setSurplusBal(BigDecimal surplusBal) {
		this.surplusBal = surplusBal;
	}
	
	
}
