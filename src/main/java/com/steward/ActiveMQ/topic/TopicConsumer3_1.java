package com.steward.ActiveMQ.topic;

import org.jboss.logging.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author KL-MZY
 * 订阅模式 消费者
 */

@Component
public class TopicConsumer3_1{
	
	private static  Logger logger=Logger.getLogger(TopicConsumer3_1.class);
	
	/**
	 * 使用 jmsListener 配置消费者监听队列，其中text是接收到的消息
	 */
	@JmsListener(destination="topic-oy")
	public void receiveTopic(String text) {
		logger.info("消费者收到的:  "+text);
		// 将收到的消息处理
	}
}
