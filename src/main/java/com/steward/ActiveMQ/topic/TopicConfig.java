package com.steward.ActiveMQ.topic;

import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

/**
 * @author KL-MZY
 *		定义 一个 主题 
 */

@Configuration
@EnableJms
public class TopicConfig {	
	/**
	 * 发布订阅模式
	 */
	@Bean 
	public Topic topic() {
		return new ActiveMQTopic("topic-oy");
		
	}
	
}
