package com.steward.dao;

import org.apache.ibatis.annotations.Param;

import com.steward.model.User;

public interface UserMapper {
      User	selectBynameAndpwd(@Param("name")String name,@Param("pwd")String pwd);
}
