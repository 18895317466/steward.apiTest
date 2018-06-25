package com.steward.dto.card;

import java.io.Serializable;
import java.math.BigDecimal;

public class SurplusDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5416423722952197601L;
	
	private String CardNo;
	private BigDecimal surplusBal;
	private BigDecimal consumeAmt;
	private String cardPass;
	private String cardId;
	private String custId;
	
	
	public String getCardNo() {
		return CardNo;
	}
	public void setCardNo(String cardNo) {
		CardNo = cardNo;
	}
	public BigDecimal getSurplusBal() {
		return surplusBal;
	}
	public void setSurplusBal(BigDecimal surplusBal) {
		this.surplusBal = surplusBal;
	}
	public BigDecimal getConsumeAmt() {
		return consumeAmt;
	}
	public void setConsumeAmt(BigDecimal consumeAmt) {
		this.consumeAmt = consumeAmt;
	}
	public String getCardPass() {
		return cardPass;
	}
	public void setCardPass(String cardPass) {
		this.cardPass = cardPass;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	
}
