package com.steward.ActiveMQ;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.steward.dao.book.CourseMapper;
import com.steward.model.book.Course;

import net.sf.json.JSONObject;
/**
 * 定义消费者
 * @author KL-MZY
 *
 */
@Component
public class Consumer3 {

	@Resource
	private CourseMapper courseMapper;
	// @JmsListener 注解监听所要的 队列
	@JmsListener(destination ="demoQueue")
	public void receiveQueue(String Msg) throws ParseException {
		// Msg 生产的消息
		JSONObject MsgJson = JSONObject.fromObject(Msg);
		
		Course course=new Course();
		course.setId(MsgJson.getString("id"));
		course.setAuthor(MsgJson.getString("author"));
		course.setTitle(MsgJson.getString("title"));
		course.setUrl(MsgJson.getString("url"));
		course.setAdddate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(MsgJson.getString("adddate")));
		courseMapper.insert(course);
		System.out.println("消费Msg : "+Msg);
		
	}
	
}
