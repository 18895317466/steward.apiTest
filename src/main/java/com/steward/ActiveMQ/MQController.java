//package com.steward.ActiveMQ;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.steward.commons.ConstData;
//import com.steward.commons.Result;
//import com.steward.model.book.Course;
//
//@RestController
//public class MQController {
//
//	 @Autowired
//	 private Producer2 producer;
//	
//	 @PostMapping(value = "/mq/{random}")
//	 public String demoMQ(@RequestParam("title")String title,@RequestParam("author")String author,
//	    		@RequestParam("url")String url) {
//		 Result<String> result=new Result<String>();
//			Course course=new Course();
//			course.setId(UUID.randomUUID().toString().toUpperCase());
//			course.setAuthor(author);
//			course.setTitle(title);
//			course.setUrl(url);
//			course.setAdddate(new Date());
//		 
//		 
//		 producer.sendP2P(JSONObject.toJSONString(course, SerializerFeature.WriteMapNullValue)); 
//		 result.setCode(ConstData.SUCCESS_CODE);
//		 result.setMessage("添加成功");
//		return JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue);
//		 
//	 }
//}
