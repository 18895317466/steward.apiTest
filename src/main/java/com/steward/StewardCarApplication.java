package com.steward;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jmx.support.RegistrationPolicy;

import com.github.tobato.fastdfs.FdfsClientConfig;

@Import(FdfsClientConfig.class)
//解决jmx重复注册bean的问题
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@SpringBootApplication
public class StewardCarApplication {


	
	public static void main(String[] args) {
		SpringApplication.run(StewardCarApplication.class, args);
	}

}
