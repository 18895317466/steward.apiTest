//package com.steward;
//
//import java.util.Date;
//import java.util.UUID;
//
//import javax.annotation.Resource;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.steward.ActiveMQ.YDACK.ProducerACK2;
//
//
//
//
//
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@Configuration
//@EnableConfigurationProperties
//public class MQ {
//	
//	@Autowired
//	private ProducerACK2 producerACK;
//
//	@Test
//	public void MQACK(){
//		producerACK.sendMessage("应答模式 demo {'userName':'大阳','age':'25'}");
//		while(true) {
//			
//		}
//	}
//
//}
//
