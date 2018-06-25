package org.carManage.services.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.carManage.services.BaseRestService;
import org.carManage.util.AliyunSmsServices;


public class GetSmsVerifiCode extends BaseRestService {
	/**
	 * 获取短信验证码,若用户不存在先执行注册
	 * 
	 * @author Li Jie
	 * @param mobilePhone
	 * @return Response
	 */
	public Response getSmsVerifiCode(String mobilePhone) {

		int retCode = 200;
		int verifiCodeLength = 4;
		String errorMsg = "";// 出错信息
		String verifiCode = "";// 短信验证码
		String SignName = "凯利车管家";
		String TemplateCode = "SMS_41835019";

		Map<String, Object> resultObj = new HashMap<String, Object>();

		if (StringUtils.isBlank(mobilePhone)) {
			retCode = 400;
			errorMsg = UtilProperties.getMessage(resourceError,
					"mobilePhoneIsEmpty", locale);
		} else {
			// 生成验证码
			for (int i = 0; i < verifiCodeLength; i++) {
				verifiCode += new Random().nextInt(10);
			}
			// 调用短信接口
			boolean sendSms = new AliyunSmsServices().singleSendSms(verifiCode,
					mobilePhone, SignName, TemplateCode);
			// sendSms = true;
			if (sendSms) {// 短信发送成功
				// 把验证码存储到登录表

				GenericDelegator delegator = (GenericDelegator) DelegatorFactory
						.getDelegator("default");
				try {
					boolean beganTrans = TransactionUtil.begin();
					GenericValue login = delegator.findByPrimaryKey(
							"UserLogin",
							UtilMisc.toMap("userLoginId", mobilePhone));
					if (login == null) {
						String successfulLogin = "Y";
						/* 更新party表，储存个人权限信息 */
						String partyId = delegator.getNextSeqId("Party");
						GenericValue party = delegator.makeValue("Party",
								UtilMisc.toMap("partyId", partyId));
						party.put("statusId", "PARTY_ENABLED");
						party.put("partyTypeId", "PERSON");
						party.put("createdDate", UtilDate.nowTimestamp());
						party.put("successfulLogin", successfulLogin);
						party.create();

						/* 更新person表，储存个人信息 */
						GenericValue person = delegator.makeValue("Person");
						person.put("partyId", partyId);
						if (StringUtils.isNotBlank(mobilePhone)) {
							person.put("mobilePhone", mobilePhone);
						}
						person.create();
						/* 更新userLogin表，储存登录信息 */
						GenericValue userLogin = delegator
								.makeValue("UserLogin");
						userLogin.put("partyId", partyId);
						userLogin.put("userLoginId", mobilePhone);
						userLogin.put("enabled", "Y");// 手机号注册时默认可用
						userLogin.put("verifiCode", verifiCode);
						userLogin.create();
						/* 更新partyRole表，储存个人信息 */
						GenericValue PartyRole = delegator
								.makeValue("PartyRole");
						PartyRole.put("partyId", partyId);
						PartyRole.put("roleTypeId", "CUSTOMER");
						PartyRole.create();
						// 储存短信接口日志
						String smsLogId = delegator.getNextSeqId("SmsLog");
						GenericValue smsLog = delegator.makeValue("SmsLog");
						smsLog.put("id", smsLogId);
						smsLog.put("mobilePhone", mobilePhone);
						smsLog.put("createdTime", UtilDate.nowTimestamp());
						smsLog.create();
					} else {
						// 用户存在时更新验证码字段
						login.put("verifiCode", verifiCode);
						login.store();
					}
					TransactionUtil.commit(beganTrans);
				} catch (Exception e) {
					e.printStackTrace();
					Debug.logError(e, module);
					try {
						TransactionUtil.rollback();
					} catch (Exception ee) {
						Debug.logError(ee, module);
					}
					retCode = 500;
					errorMsg = UtilProperties.getMessage(resourceError,
							"ExecuteServerException", locale);
				}
			} else {
				retCode = 400;
				errorMsg = UtilProperties.getMessage(resourceError,
						"VerifiCodeRequestFaild", locale);
			}
		}
		resultObj.put("code", retCode);
		if (retCode == 200) {
			return Response.ok(jsonStr(resultObj)).build();
		} else {
			Map<String, Object> errorObj = new HashMap<String, Object>();
			errorObj.put("code", new Integer(retCode));
			errorObj.put("message", errorMsg);
			return Response.status(retCode).entity(jsonStr(errorObj)).build();
		}
	}
}
