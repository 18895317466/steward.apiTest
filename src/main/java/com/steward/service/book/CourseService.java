package com.steward.service.book;

import com.steward.commons.Page;
import com.steward.commons.Result;
import com.steward.model.book.Course;

public interface CourseService  {
	
	Result<Page<Course>> courseList(String title,String author,Integer pageNumber,Integer pageSize);
	Result<String> addCourse(Course course);
}
