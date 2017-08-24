package cn.edu.nju.software.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by xmc1993 on 2017/8/24.
 */
@Component
public class CalculateStatisticTask {

    @Scheduled(cron = "0/5 * * * * ? ")
    public void testTask(){
        System.out.println("----done----");
    }


    public void calculateCorrelation() {

    }


}
