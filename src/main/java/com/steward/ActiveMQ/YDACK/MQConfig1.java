//package com.steward.ActiveMQ.YDACK;
//
//import javax.jms.Queue;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.apache.activemq.RedeliveryPolicy;
//import org.apache.activemq.command.ActiveMQQueue;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jms.annotation.EnableJms;
//import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
//import org.springframework.jms.core.JmsTemplate;
//
///**
// * @author KL-MZY
// * 消息机制之应答模式 -- ACK 机制
// */
//
//@Configuration
//@EnableJms
//public class MQConfig1 {
//    /**
//     * 定义应答模式队列  
//     * @return
//     */
//	@Bean
//	public Queue queue() {
//		return new ActiveMQQueue("queueACK");
//	}
//	
//
//	
//	/**
//	 * 消息重发机制RedeliveryPolicy 有几个属性如下：
//	 */
//	@Bean
//	public RedeliveryPolicy redeliveryPolicy() {
//		RedeliveryPolicy redeliveryPolicy=new RedeliveryPolicy();
//		// 是否每次重新发送失败后，增长这个等待时间
//		redeliveryPolicy.setUseExponentialBackOff(true);
//		// 重发次数，默认6次，这里设置 9 次
//		redeliveryPolicy.setMaximumRedeliveryDelay(9);
//		// 设置重发间隔时间 默认1 s
//		redeliveryPolicy.setInitialRedeliveryDelay(1);
//		// 第一次失败后重新发送之前等待 500毫秒，第二次失败在等待500*2；
//		redeliveryPolicy.setBackOffMultiplier(2);
//		// 是否避免消息碰撞
//		redeliveryPolicy.setMaximumRedeliveryDelay(-1);
//			
//		return redeliveryPolicy;
//		
//	}
//	 @Bean  
//	    public ActiveMQConnectionFactory activeMQConnectionFactory (@Value("${activemq.url}")String url,RedeliveryPolicy redeliveryPolicy){    
//	        ActiveMQConnectionFactory activeMQConnectionFactory =    
//	                new ActiveMQConnectionFactory(  
//	                       "admin",  
//	                        "admin",  
//	                        url);  
//	       activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
//	        return activeMQConnectionFactory;  
//	    }  
//	@Bean
//	public JmsTemplate jmsTemplate(ActiveMQConnectionFactory activeMQConnectionFactory,Queue queue) {
//		JmsTemplate jmsTemplate=new JmsTemplate();
//		jmsTemplate.setDeliveryMode(2);//进行持久化配置 1表示非持久化，2表示持久化
//        jmsTemplate.setConnectionFactory(activeMQConnectionFactory);  
//        jmsTemplate.setDefaultDestination(queue); //此处可不设置默认，在发送消息时也可设置队列  
//        jmsTemplate.setSessionAcknowledgeMode(4);//客户端签收模式</span>  
//        return jmsTemplate;
//		
//	}
//	
//	/**
//	 * 定义一个消息监听器连接工厂，这里定义的是点对点模式的监听器连接工厂  
//	 */
//	@Bean(name="jmsQueueListener")
//	public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
//		DefaultJmsListenerContainerFactory factory=new DefaultJmsListenerContainerFactory();
//		//设置连接时间
//		factory.setConcurrency("1-10");
//		// 设置间隔时间
//		factory.setRecoveryInterval(1000L);
//		factory.setSessionAcknowledgeMode(4);
//		return factory;
//		
//	}
//	
//}
