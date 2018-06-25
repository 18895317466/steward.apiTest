package com.steward.ActiveMQ.topic;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import com.sun.istack.logging.Logger;


/**
 * @author KL-MZY
 * 订阅模式的生产者
 */
@Component
public class TopicProducer2 {	
	private static Logger logger=Logger.getLogger(TopicProducer2.class);
	
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	/**
	 * 把接收到的消息和消息模式放到了
     * 队列里或者主题里就是queue或topic里，然后只要写消费者加
     * 上@JmsListener监听队列消息就可以自动获取 
	 * @param destination
	 * @param message
	 */
	public void sendMessage(Destination destination,String message) {
		jmsMessagingTemplate.convertAndSend(destination,message);
	}
	
	/**
	 * * 2.这里的第二个方法加了监听注解就可以收到消费者反馈的信息，
     * 前提消费者要加@SendTo注解，具体看下面消费者类
	 * @param message
	 */
	@JmsListener(destination="return-topic")
	public void Message(String message) {
		logger.info("生产者收到反馈的:" +message);
	}
	
}
