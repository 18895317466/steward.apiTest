package com.steward.controller;
import org.jboss.logging.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
@RestController
public class IndexController {
	
	private Logger logger=Logger.getLogger(IndexController.class);
	
    @RequestMapping("/index")
    public ModelAndView login(){
    	logger.info("toLogin");
        ModelAndView modelAndView = new ModelAndView("/index");
        return modelAndView;
    }
    
	
    @RequestMapping("/index2")
    public ModelAndView login2(){
    	logger.info("toLogin");
        ModelAndView modelAndView = new ModelAndView("/index2");
        return modelAndView;
    }
    
    
}
