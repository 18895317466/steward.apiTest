package com.steward.dao.book;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.steward.model.book.Course;

public interface CourseMapper {
	
	int insert(Course record);
	int courseCount(@Param("title")String title,@Param("author")String author);
	List<Course> courseList(@Param("title")String title,@Param("author")String author,@Param("pageNumber")Integer pageNumber,@Param("pageSize")Integer pageSize);
}
