package com.steward.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.steward.commons.Result;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = Logger.getLogger(TokenInterceptor.class);

    //@Autowired
    //private RedisTemplate<String, String> redisTemplate;
    
    public TokenInterceptor() {
        super();
    }

    /**
     * 
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
        logger.info("URL:\t"+request.getRequestURI()+"\tuserId:\t"+request.getHeader("USERID")+"\ttoken:\t"+request.getHeader("TOKEN"));

        String userId = request.getHeader("USERID");
        String token = request.getHeader("TOKEN");
        
        if (StringUtils.isEmpty(userId) && StringUtils.isEmpty(token)){
            printErrorMsg(response);
            return false;
        }else{
            //String str = redisTemplate.opsForValue().get(userId.toUpperCase());
           // str = str.substring(1, str.length()-1);
           // net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(str);
           // String redisToken = json.getString("token");
           // if (!token.equals(redisToken.toUpperCase())){
                printErrorMsg(response);
                return false;
           // }else{
            //    return true;
          //  }
        }
//        return super.preHandle(request, response, handler);
//        return true;
    }

    /**
     * 用户没有登录时
     *
     * @param response
     * @throws IOException
     */
    private void printErrorMsg(HttpServletResponse response) throws IOException {
        response.setStatus(403);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, "utf-8"));
        Result<String> result = new Result<String>();
        result.setCode(403);
        result.setMessage("用户账号、密码不对");
        pw.println(JSONObject.toJSONString(result));
        pw.flush();
        pw.close();
    }

    /**
     * 将账号、密码存放早response的header
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        String userId = request.getHeader("USERID");
        String token = request.getHeader("TOKEN");
        response.setHeader("USERID",userId.toUpperCase());
        response.setHeader("TOKEN",token);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
