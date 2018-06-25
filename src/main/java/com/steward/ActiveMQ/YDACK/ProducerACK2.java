//package com.steward.ActiveMQ.YDACK;
//
//import javax.jms.Destination;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.stereotype.Component;
//
///**
// * 应答模式 生产者
// * @author KL-MZY
// *
// */
//@Component
//public class ProducerACK2 {
//   
//	private Logger logger=Logger.getLogger(ProducerACK2.class);
//	
//	@Autowired
//	private JmsTemplate jmsTemplate;
//	/**
//	 * 发送消息， estination 是发送的队列，message是待发送的消息
//	 */
//	public void sendMessage(Destination destination,final String message) {
//		logger.info(jmsTemplate.getDeliveryMode());
//		jmsTemplate.convertAndSend(destination,message);
//		
//	}
//	
//	/**
//	 * 指定queueACK 队列 ，message是待发送消息
//	 */
//	public void sendMessage(final String message) {
//		logger.info("queueACK : "+jmsTemplate.getDeliveryMode());
//		jmsTemplate.convertAndSend("queueACK",message);
//	}
//}
