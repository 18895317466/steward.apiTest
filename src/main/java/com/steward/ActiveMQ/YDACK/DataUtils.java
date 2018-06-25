package com.steward.ActiveMQ.YDACK;



public class DataUtils{

	
	/**
	 * 自动确认
	 */
	public static final int AUTO_ACKNOWLEDGE  = 1;
	/**
	 * 客户端手动确认确认
	 */
	public static final int CLIENT_ACKNOWLEDGE  = 2;
	/**
	 * 自动批量确认
	 */
	public static final int DUPS_OK_ACKNOWLEDGE   = 3;
	/**
	 * 事务提交并确认
	 */
	public static final int SESSION_TRANSACTED  = 0;
	/**
	 * 单条消息确认 activemq 独有
	 */
	public static final int INDIVIDUAL_ACKNOWLEDGE = 4 ;   
	
	
}
