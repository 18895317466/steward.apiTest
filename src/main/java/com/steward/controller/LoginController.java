package com.steward.controller;

import javax.annotation.Resource;

import org.jboss.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.steward.commons.ConstData;
import com.steward.commons.Result;
import com.steward.dao.UserMapper;
import com.steward.model.User;

@Controller
public class LoginController {
	
	private Logger logger=Logger.getLogger(LoginController.class);
	@Resource
	private UserMapper userMapper;
	
    @RequestMapping("/toLogin")
    public ModelAndView login(){
    	logger.info("toLogin");
        ModelAndView modelAndView = new ModelAndView("/login");
        return modelAndView;
    }
    
    /**
     *登录操作
     **/
    @PostMapping(value = "/login/{random}")
    @ResponseBody
    public String login(@RequestParam("name")String name,@RequestParam("pwd")String pwd){
    	logger.info("login>> name "+name+" pwd "+pwd);
    	Result<User> result=new Result<User>();
        if(!name.equals("") && !"".equals(pwd)){
            User user =userMapper.selectBynameAndpwd(name, pwd); 
          
            if(user==null) {
            	result.setCode(ConstData.NOT_FOUND_CODE);
            	result.setMessage("用户名或密码不正确");
                return JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue);
            }
            logger.info("name "+user.getName());
            result.setCode(ConstData.SUCCESS_CODE);
            result.setContent(user);
            return JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue);
        }else{
        
        	result.setCode(ConstData.NOT_FOUND_CODE);
        	result.setMessage("请填写用户名或密码");
        }
        
        return JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }
    

}
