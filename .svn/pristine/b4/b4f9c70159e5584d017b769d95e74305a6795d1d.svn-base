package org.carManage.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 阿里云短信服务
 * 
 * @author Li Jie
 *
 */
public class AliyunSmsServices {
	private static final String APPCODE = "2bc3a0a90dfa432da28ed00303b0c911";
	private static final String APPKEY="23546164";//X-Ca-Key
	
	/**
	 * 
	 * @param verifiCode 验证码
	 * @param mobilePhone 手机号
	 * @param SignName 签名
	 * @param TemplateCode 模板代码
	 * 
	 * @return 执行成功返回 true 否则 false
	 * */
	public boolean singleSendSms(String verifiCode, String mobilePhone,
			String SignName,String TemplateCode) {
		String host = "http://sms.market.alicloudapi.com";
		String path = "/singleSendSms";
		String method = "GET";
		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE
		// 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + APPCODE);
		headers.put("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");
		headers.put("Accept", "application/json");
		headers.put("X-Ca-Key", APPKEY);
		Map<String, String> querys = new HashMap<String, String>();
		//模板字段值拼装
		String ParamString = "{'verifiCode':'" + verifiCode + "','timeout':'15'}";
		System.out.println(ParamString);
		querys.put("ParamString", ParamString);// {“no”:”123456”}
		// 目标手机号
		querys.put("RecNum", mobilePhone);
		// 签名名称
		querys.put("SignName", SignName);
		//短信模板代码
		querys.put("TemplateCode", TemplateCode);
		try {
			HttpResponse response = HttpUtils.doGet(host, path, method,
					headers, querys);
			// 获取response的body
			String resJson=EntityUtils.toString(response.getEntity());
			JSONObject backobj = JSON.parseObject(resJson);
			return backobj.getBooleanValue("success");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		// Host: e710888d3ccb4638a723ff8d03837095-cn-qingdao.aliapi.com
		// Date: Mon, 22 Aug 2016 11:21:04 GMT
		// User-Agent: Apache-HttpClient/4.1.2 (java 1.6)
		// Content-Type: application/x-www-form-urlencoded; charset=UTF-8
		// //请求体类型，请根据实际请求体内容设置
		// Accept: application/json
		// //请求响应体类型，部分API可以根据指定的响应类型来返回对应数据格式，建议手动指定此请求头，如果不设置，部分HTTP客户端会设置默认值*/*，导致签名错误
		// X-Ca-Request-Mode: debug
		// //是否开启Debug模式，大小写不敏感，不设置默认关闭，一般API调试阶段可以打开此设置
		// X-Ca-Version: 1
		// //API版本号，目前所有API仅支持版本号『1』，可以不设置此请求头，默认版本号为『1』
		// X-Ca-Signature-Headers:
		// X-Ca-Request-Mode,X-Ca-Version,X-Ca-Stage,X-Ca-Key,X-Ca-Timestamp
		// //参与签名的自定义请求头，服务端将根据此配置读取请求头进行签名，此处设置不包含Content-Type、Accept、Content-MD5、Date请求头，这些请求头已经包含在了基础的签名结构中，详情参照请求签名说明文档
		// X-Ca-Stage: RELEASE
		// //请求API的Stage，目前支持TEST、RELEASE两个Stage，大小写不敏感，API提供者可以选择发布到哪个Stage，只有发布到指定Stage后API才可以调用，否则会提示API找不到或Invalid
		// Url
		// X-Ca-Key: 60022326
		// //请求的AppKey，请到API网关控制台生成，只有获得API授权后才可以调用，通过云市场等渠道购买的API默认已经给APP授过权，阿里云所有云产品共用一套AppKey体系，删除ApppKey请谨慎，避免影响到其他已经开通服务的云产品
		// X-Ca-Timestamp: 1471864864235
		// //请求的时间戳，值为当前时间的毫秒数，也就是从1970年1月1日起至今的时间转换为毫秒，时间戳有效时间为15分钟
		// X-Ca-Nonce:b931bc77-645a-4299-b24b-f3669be577ac
		// //请求唯一标识，15分钟内AppKey+API+Nonce不能重复，与时间戳结合使用才能起到防重放作用
		// X-Ca-Signature: FJleSrCYPGCU7dMlLTG+UD3Bc5Elh3TV3CWHtSKh1Ys=
		// //请求签名
	}

	public void PostRequestHeaders() {

	}

	public static void main(String[] args) {
		new AliyunSmsServices().singleSendSms("test", "15336566009", "吴刚毅","SMS_29730008");
	}
}
