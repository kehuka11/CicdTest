package com.example.demo.repository.mybatis;

import com.example.demo.model.entities.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> selectAllUser();

}
