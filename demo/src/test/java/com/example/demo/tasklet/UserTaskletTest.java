package com.example.demo.tasklet;

import com.example.demo.job.UserJobConfig;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBatchTest
//@SpringJUnitConfig({CreatePanDanMappingJobConfig.class})
@SpringBootTest
public class UserTaskletTest {



    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils; // SpringBatchTestでインジェクションされるが、IntelliJだとエラー扱いになる。（テスト動作は問題ない）

    @Autowired private UserJobConfig job;

    // Jobテスト
    @Test
    public void testJob() throws Exception {
        // launchJobでJob全体を実行する
        JobExecution jobExecution = this.jobLauncherTestUtils.launchJob();
        System.out.println("success");
        //実行結果と予想値と比較
        assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());

    }
}
