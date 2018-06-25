package com.steward.controller.book;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.steward.commons.Page;
import com.steward.commons.Result;
import com.steward.model.book.Course;
import com.steward.service.book.CourseService;

@RestController
public class CoruseController {

	private  Logger logger=Logger.getLogger(CoruseController.class);
	@Resource
	private CourseService courseService;
    /**
     *添加课程
     **/
    @PostMapping(value = "/main/book/addcourse/{random}")
    public String addcourse(@RequestParam("title")String title,@RequestParam("author")String author,
    		@RequestParam("url")String url){
		Result<String> result=new Result<String>();
		Course course=new Course();
		course.setId(UUID.randomUUID().toString().toUpperCase());
		course.setAuthor(author);
		course.setTitle(title);
		course.setUrl(url);
		course.setAdddate(new Date());
		result=courseService.addCourse(course);
		
		return JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue);
    	
    }
	@PostMapping("/main/book/courseList/{random}")
	public String courseList(@RequestParam("title")String title,@RequestParam("author")String author,
			@RequestParam("pageNumber")String pageNumber,@RequestParam("pageSize")String pageSize) {
		if("NULL".equals(title)) title=null;
		if("NULL".equals(author)) author=null;
		int num=Integer.parseInt(pageNumber);
		int size=Integer.parseInt(pageSize);
		Result<Page<Course>> result=courseService.courseList(title, author, num, size);
 		
		return JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue);
		
	}
	
}
