package com.steward.ActiveMQ.topic;

import javax.jms.Queue;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制层接收要发布的消息
 * @author KL-MZY
 *
 */
@RestController
@RequestMapping("activeMq")
public class TopicController {
	
	@Autowired
	private Queue queue;
	
	@Autowired
	private Topic topic;
	
	@Autowired
	private TopicProducer2 topicProducer;
	
	@PostMapping("/topic")
    public void sendTopic(@RequestParam("msg")String msg) {
		topicProducer.sendMessage(this.topic, msg);
	}
}
