package com.steward.commons;
/**
 * 
 * @author mastercheng
 *
 */
public class ConstData {

	/**
	 * 请求成功
	 */
	public static final int SUCCESS_CODE = 200;

	/**
	 * 没有对应的数据
	 */
	public static final int NOT_FOUND_CODE = 400;

	/**
	 * 后台抛异常
	 */
	public static final int SYS_ERROR = 500;
	
	/**
	 * 找不到数据
	 */
	public static final int NOT_FOND = 600;

	/**
	 * 年卡被激活的状态
	 */
	public static final int CARD_ACTIVE_STATUS = 1;

	/**
	 * 年卡未激活的状态
	 */
	public static final int CARD_NOT_ACTIVE_STATUS = 0;

	/**
	 * 激活方式 未知
	 */
	public static final int CARD_ACTIVE_TYPE_UNKNOWN = 0;

	/**
	 * 激活方式 app
	 */
	public static final int CARD_ACTIVE_TYPE_APP = 1;

	/**
	 * 激活方式 网站
	 */
	public static final int CARD_ACTIVE_TYPE_WEBSITE = 2;

	/**
	 * 年卡类型未知
	 */
	public static final int CARD_TYPE_UNKOWN = 0;
	
	/**
	 * 年卡类型 会员卡
	 */
	public static final int CARD_TYPE_MEMBERSHIP = 1;

	/**
	 * 年卡类型油卡
	 */
	public static final int CARD_TYPE_OIL = 2;
	
	/**
	 * 油卡类型 未知
	 */
	public static final int OIL_CARD_TYPE_UNKNOWN = 0;
	
	/**
	 * 油卡类型中石化
	 */
	public static final int OIL_CARD_TYPE_SINOPEC = 1;
	
	/**
	 * 油卡类型 中石油
	 */
	public static final int OIL_CARD_TYPE_PETROCHINA = 2;
	
	/**
	 * 加密key
	 */
	public static final String KEY = "MICROTEAM";

}
