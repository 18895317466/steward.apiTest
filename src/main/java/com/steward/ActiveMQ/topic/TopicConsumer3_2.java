package com.steward.ActiveMQ.topic;

import org.jboss.logging.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @author KL-MZY
 * 订阅模式 消费者
 */

@Component
public class TopicConsumer3_2 {

	private static  Logger logger=Logger.getLogger(TopicConsumer3_2.class);
	
	/**
	 * 使用 jmsListener 配置消费者监听队列，其中text是接收到的消息
	 */
	@JmsListener(destination="topic-oy")
	@SendTo("return-topic")
	public String receiveTopic(String text) {
		logger.info("消费者2收到的:  "+text);
		// 将收到的消息处理
		return "回复：消费者2收到消息!";
	}
}
