package com.example.demo.repository.mybatis;

import com.example.demo.model.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserMapperTest {

    @Autowired private UserMapper userMapper;

    @Test
    public void getTest() {
        List<User> userList = userMapper.selectAllUser();
        assertEquals(1, userList.size());
        assertEquals("testuserid", userList.get(0).getUserId());
    }
}
