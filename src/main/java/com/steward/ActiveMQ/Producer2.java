package com.steward.ActiveMQ;
/**
 * 第二步  定义生产者
 * @author KL-MZY
 *
 */
import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
@Component
public class Producer2 {
    private Logger logger=Logger.getLogger(Producer2.class);	
    @Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;	
    @Autowired
    private Queue queue;   
    @Autowired
    private Topic topic;    
    /**
     * 定义一个生产者，定时5S中生产发出一次数据，通过springboot提供的JmsMessagingTemplate实现send动作，并生产p2p与topic主题消息各一个：
     */
    public void sendP2P(String Msg) {
    	// 发送队列消息
    	System.out.println("生产者 : "+Msg);
    	this.jmsMessagingTemplate.convertAndSend(this.queue,Msg);  	
    }   
    /**
     * 定义一个生产者，定时5S中生产发出一次数据，通过springboot提供的JmsMessagingTemplate实现send动作，并生产p2p与topic主题消息各一个：
     */
    public void sendTopic(String Msg) {
    	// 发送队列消息
    	this.jmsMessagingTemplate.convertAndSend(this.topic,Msg);    	
    }    
}
