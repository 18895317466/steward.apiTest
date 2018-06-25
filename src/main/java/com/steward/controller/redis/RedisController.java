package com.steward.controller.redis;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {
	private static Logger logger=Logger.getLogger(RedisController.class); 
	@Autowired
    private RedisTemplate<String, String> redisTemplate;   	
	@PostMapping("/users/redisTst/{random}")
	public @ResponseBody String redisTst() {
		 redisTemplate=initRedis(12,redisTemplate);
		String redis12=redisTemplate.opsForValue().get("00001");		
		logger.info("redis12 : "+redis12);		
		 redisTemplate=initRedis(5,redisTemplate);
		String redis5= redisTemplate.opsForValue().get("39E40B10-5EE9-431B-A015-5ECDA7AFE173");
		logger.info("redis5 : "+redis5); 			
		redisTemplate=initRedis(12,redisTemplate);
		String redis12_2=redisTemplate.opsForValue().get("00001");
		logger.info("redis12_2 : "+redis12_2);
		return "success";
		
	}	
	//自己改进后的封装
	/**
	 * redis 数据库切换
	 * 
	 */	    
	    public static RedisTemplate<String, String> initRedis(Integer indexDb, RedisTemplate<String, String> redisTemplate){
	        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(); 
	        redisConnectionFactory.setHostName("192.168.0.71");  
	        redisConnectionFactory.setPort(6379); 
	        redisConnectionFactory.setDatabase(indexDb);
	        redisConnectionFactory.setPassword("klcgj");
	        redisConnectionFactory.afterPropertiesSet(); 
	       
	        redisTemplate.setConnectionFactory(redisConnectionFactory); 
	        return redisTemplate;
	    }
		
	
}
