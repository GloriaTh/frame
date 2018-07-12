package com.sboot.springboot.dao;

import com.sboot.springboot.domin.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUsernameAPassword(@Param("username") String username, @Param("password") String password);

    User selectByUsername(String username);

    int checkUsername(String username);

    int checkEmail(String email);
}