package com.steward.ActiveMQ;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.MessageCreator;
/**
 * 第一步 定义 点对点消息 和一个主题
 * 第二步 定义生产者
 * 第三步 定义消费者
 * @author KL-MZY
 *
 */
@Configuration
@EnableJms
public class Msg1  {

   /**
    * 定义点对点队列
    */
	@Bean
	public Queue queue() {
		
		return new ActiveMQQueue("demoQueue"); 
		
	}

    /**
     * 定义一个主题
     */
	@Bean
	public Topic topic() {
		return new ActiveMQTopic("demoTopic");
		
	}
	
	
	
	
}
