package com.example.demo.tasklet;

import com.example.demo.model.entities.User;
import com.example.demo.repository.mybatis.UserMapper;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class UserTasklet implements Tasklet {

    @Autowired
    private UserMapper userMapper;


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws InterruptedException {

        try {
            List<User> userList = userMapper.selectAllUser();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return RepeatStatus.FINISHED;
    }
}
