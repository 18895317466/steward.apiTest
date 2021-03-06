package com.steward.commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;


public class RulesUtils {

	public static String send(String str,String cookies,String url){
		
		HttpURLConnection conn = null;
		StringBuffer resultBuffer = null;
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		
		try {
			
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("charset", "UTF-8");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
			conn.setRequestProperty("Referer", "http://hl.122.gov.cn/views/inquiry.html?q=j");
			conn.setRequestProperty("Cookie", cookies);  //浏览器cookies  
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);// post不能使用缓存

			conn.setRequestMethod("POST");
			// 获取URLConnection对象对应的输出流
			osw = new OutputStreamWriter(conn.getOutputStream());
			
			osw.write(str);
			osw.flush();
			
			resultBuffer = new StringBuffer();
			
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String temp;
			while ((temp = br.readLine()) != null) {
				resultBuffer.append(temp);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					osw = null;
					throw new RuntimeException(e);
				} finally {
					if (conn != null) {
						conn.disconnect();
						conn = null;
					}
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				} finally {
					if (conn != null) {
						conn.disconnect();
						conn = null;
					}
				}
			}
		}
		
		
		return resultBuffer.toString();
	}
	
	
	
//取图片 验证码	
public static String sendToken(String picUrl,String cookies){
	
		HttpURLConnection conn = null;
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		FileOutputStream fos = null;
		String token = "";
		Long keyTime = new Date().getTime();
		String url = picUrl(picUrl);
		try {
			
			URL realUrl = new URL("http://hl.122.gov.cn/captcha?nocache="+keyTime);    //验证码接口
			// 打开和URL之间的连接
			conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("charset", "UTF-8");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
			conn.setRequestProperty("Referer", "http://hl.122.gov.cn/views/inquiry.html?q=j");
//			conn.setRequestProperty("Cookie", "JSESSIONID-L=490e0a38-7a8e-469d-8747-d2e0df4b67ec; aliyungf_tc=AQAAAL4+fTSmSgMAYUERcANsshXIwzHE; tmri_csfr_token=464DB47C03DCC84CE6DFDA49DDC6FB5E; qt="+mathNumber);
			conn.setRequestProperty("Cookie", cookies);
			
			conn.connect();
			
			
			InputStream iStream = conn.getInputStream();
			
			
			File f = new File(url);
			fos = new FileOutputStream(f);
			byte[] b = new byte[1];
			while (iStream.read(b) != -1) {
				fos.write(b);
			}
			
		   token = ChaoJiYing.PostPic("2228370822", "a123456", "893865", "1902", "4", url);
		   
		   
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					osw = null;
					throw new RuntimeException(e);
				} finally {
					if (conn != null) {
						conn.disconnect();
						conn = null;
					}
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				} finally {
					if (conn != null) {
						conn.disconnect();
						conn = null;
					}
				}
			}
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(token.length() != 0){
			JSONObject jsonObject = JSONObject.fromObject(token);
			if("0".equals(jsonObject.getString("err_no"))){
				File f1 = new File(url);
				String url1 = picUrl(picUrl+jsonObject.getString("pic_str")+"_");
				File ftoken = new File(url1);
				f1.renameTo(ftoken);
			}
		}
		
		
		return token;
	}
	
	//  浏览器信息 
	public static List<String> getCookies(){
		//http://hl.122.gov.cn/
		
		HttpURLConnection conn = null;
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		List<String> list = null;
		
		try {
			
			URL realUrl = new URL("http://hl.122.gov.cn/views/inquiry.html");
			// 打开和URL之间的连接
			conn = (HttpURLConnection) realUrl.openConnection();
			
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("charset", "UTF-8");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
			
			conn.connect();
			
			
			Map<String, List<String>> map = conn.getHeaderFields();
			
			list = map.get("Set-Cookie");
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					osw = null;
					throw new RuntimeException(e);
				} finally {
					if (conn != null) {
						conn.disconnect();
						conn = null;
					}
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				} finally {
					if (conn != null) {
						conn.disconnect();
						conn = null;
					}
				}
			}
		}
		
		return list;
	}
	
	public static String picUrl(String str){
		
		String uuid = UUID.randomUUID().toString().toUpperCase();
		String picurl = str + uuid + ".jpg";
//		System.out.println(picurl);
		return picurl;
	}
	
	
	public static void main(String[] args) {

		String cookies = getCookies().toString();
		System.out.println(cookies);
		String token = sendToken("C:\\Users\\Administrator\\Desktop\\",cookies);
		System.out.println(token);
//		JSONObject jsonObject = JSONObject.fromObject(token);
//		String str = "hpzl=02&hphm1b=A701TB&hphm=黑A701TB&fdjh=087940&captcha="+jsonObject.getString("pic_str")+"&qm=wf&page=1";
//		JSONObject jObject = JSONObject.fromObject(send(str,cookies));
//		System.out.println(send(str,cookies));
//		System.out.println(jObject.getJSONObject("data").getJSONObject("content").getInt("zs"));
		
		
		
//		File file1 = new File("C:\\Users\\Administrator\\Desktop\\0D16DD20-A6EB-4420-8EC2-D9EB5AC0D06E.jpg");
//		file1.renameTo(new File("C:\\Users\\Administrator\\Desktop\\111.jpg"));
	}
}
