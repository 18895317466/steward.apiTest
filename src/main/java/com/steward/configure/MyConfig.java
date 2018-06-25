package com.steward.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * @author KL-MZY
 *
 */
@Configuration
public class MyConfig extends WebMvcConfigurerAdapter{
	/**
	 * 配置类：
	 * 在static 文件夹下放静态文件，强迫症下进行文件夹分类 后 js、css加载不出
	 * 原因是因为默认路径虽然是在static下，但并没有包含static 下的各个文件夹，
	 * 因此当我们把静态文件移入这些文件夹后，spring boot就不认识了 
	 * 
	 * 方法： 加下面配置类就ok了
	 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/public/**").addResourceLocations("classpath:/public/");
    }
}