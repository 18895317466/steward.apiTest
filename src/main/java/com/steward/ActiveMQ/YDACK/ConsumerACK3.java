//package com.steward.ActiveMQ.YDACK;
//
//import javax.jms.JMSException;
//import javax.jms.Session;
//import javax.jms.TextMessage;
//
//import org.jboss.logging.Logger;
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.stereotype.Component;
//
///**
// * 应答模式  消费者 ： 使用异步监听（使用监听形式）
// * @author KL-MZY
// *
// */
//
//@Component
//public class ConsumerACK3 {
//
//	 private static  Logger logger=Logger.getLogger(ConsumerACK3.class);
//	 
//	 @JmsListener(destination="queueACK", containerFactory="jmsQueueListener")
//	 private void receiveQueue(final TextMessage text,Session session) throws JMSException {
//		 
//		 
//		 try {
//			 logger.info("消费者收到的报文 ： "+text.getText());
//			// 使用手动签收模式，需要手动的调用，如果不在catch中调用session.recover()消息只会在重启服务后重发  
//			 text.acknowledge();
//		} catch (JMSException e) {
//			session.recover();// 此不可省略 重发信息使用  
//			e.printStackTrace();
//		}
//		 
//	 }
//	
//}
