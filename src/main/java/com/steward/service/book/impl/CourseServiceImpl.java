package com.steward.service.book.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.steward.commons.ConstData;
import com.steward.commons.Page;
import com.steward.commons.Result;
import com.steward.dao.book.CourseMapper;
import com.steward.model.book.Course;
import com.steward.service.book.CourseService;
@Service
public class CourseServiceImpl implements CourseService {
    @Resource
	private CourseMapper courseMapper;

	@Override
	public Result<String> addCourse(Course course) {
		Result<String> result=new Result<String>();
		courseMapper.insert(course);
		result.setCode(ConstData.SUCCESS_CODE);
		result.setMessage("添加成功");
		return result;
	}
    
	@Override
	public Result<Page<Course>> courseList(String title, String author, Integer pageNumber, Integer pageSize){

		Result<Page<Course>> result = new Result<Page<Course>>();
		Integer  totalRecords = courseMapper.courseCount(title, author);
		
		List<Course> userInfos = null;
		
		if(totalRecords > 0){
			Integer start = (pageNumber-1)*pageSize;
			//Integer end = pageNumber*pageSize;
			userInfos = courseMapper.courseList(title, author, start, pageSize);
		}else {
			result.setCode(ConstData.NOT_FOUND_CODE);
			result.setMessage("记录不存在");
			return result;
		}
		Page<Course> page = new Page<Course>(totalRecords, pageSize, pageNumber, userInfos);		
		result.setCode(ConstData.SUCCESS_CODE);
		result.setContent(page);
		return result;
	}
	
}
