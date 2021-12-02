package com.bjfu.fcro.timedtask.staticscheduletask;

import com.bjfu.fcro.service.SysSpendBetweenInPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
/**
 * 静态定时任务 异步+自定义线程池
 *
 * 四个时段 ：
 *          8 - 10
 *          10 - 17
 *          17 - 20  每30分钟触发一次
 *          20 - 8   每一个小时触发一次
 *
 * 开多个线程   利用hash选择部分库里数据
 * */
@Configuration      //1.主要用于标记配置类，兼备Component的效果.
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
    //3.添加定时任务
//    @Scheduled(cron = "0 30 8 ? * MON-FRI")//每周一到周五的八点三十触发
//    @Scheduled(fixedRate=2000) //每两秒一次
    //或直接指定时间间隔，例如：5秒
    @Autowired
    private SysSpendBetweenInPointsService sysSpendBetweenInPointsService;

    @Async("scheduleTaskAsyncPool")
    @Scheduled(cron = "0 0/30 8-20 * * MON-FRI")//周一到周五，8-20点每半小时触发一次
    public void operatingHoursConfigureTasks0() {//为了应对库里数据较多的时候，只对id % 3 == 0 找新数据
        int hash = 0;
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now()+"线程名字："+Thread.currentThread().getName()+"hash:"+hash);

        sysSpendBetweenInPointsService.scheduleTask(hash,"10","17");
        sysSpendBetweenInPointsService.scheduleTask(hash,"8","10");
        sysSpendBetweenInPointsService.scheduleTask(hash,"17","20");


    }
    @Async("scheduleTaskAsyncPool")
    @Scheduled(cron = "0 0/30 8-20 * * MON-FRI")//周一到周五，8-20点每半小时触发一次
    public void operatingHoursConfigureTasks1() {//为了应对库里数据较多的时候，只对id % 3 == 1 找新数据
        int hash = 1;
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now()+"线程名字："+Thread.currentThread().getName()+"hash:"+hash);

        sysSpendBetweenInPointsService.scheduleTask(hash,"10","17");
        sysSpendBetweenInPointsService.scheduleTask(hash,"8","10");
        sysSpendBetweenInPointsService.scheduleTask(hash,"17","20");
    }
    @Async("scheduleTaskAsyncPool")
    @Scheduled(cron = "0 0/30 8-20 * * MON-FRI")//周一到周五，8-20点每半小时触发一次
    public void operatingHoursConfigureTasks2() {//为了应对库里数据较多的时候，只对id % 3 == 2 找新数据
        int hash = 2;
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now()+"线程名字："+Thread.currentThread().getName()+"hash:"+hash);

        sysSpendBetweenInPointsService.scheduleTask(hash,"10","17");
        sysSpendBetweenInPointsService.scheduleTask(hash,"8","10");
        sysSpendBetweenInPointsService.scheduleTask(hash,"17","20");
    }
    @Async("scheduleTaskAsyncPool")
    @Scheduled(cron = "0 0/59 20-23 * * MON-FRI")//周一到周五，20-23点每一小时触发一次
    public void otherTImeConfigureTasks0() {//为了应对库里数据较多的时候，只对id % 3 == 0 找新数据
        int hash = 0;
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now()+"线程名字："+Thread.currentThread().getName()+"hash:"+hash);

        sysSpendBetweenInPointsService.scheduleTask(hash,"20","24");

    }
    @Async("scheduleTaskAsyncPool")
    @Scheduled(cron = "0 0/59 20-23 * * MON-FRI")//周一到周五，20-23点每一小时触发一次
    public void otherTImeConfigureTasks1() {//为了应对库里数据较多的时候，只对id % 3 == 1 找新数据
        int hash = 1;
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now()+"线程名字："+Thread.currentThread().getName()+"hash:"+hash);

        sysSpendBetweenInPointsService.scheduleTask(hash,"20","24");
    }
    @Async("scheduleTaskAsyncPool")
    @Scheduled(cron = "0 0/59 20-23 * * MON-FRI")//周一到周五，20-23点每一小时触发一次
    public void otherTImeConfigureTasks2() {//为了应对库里数据较多的时候，只对id % 3 == 2 找新数据
        int hash = 2;
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now()+"线程名字："+Thread.currentThread().getName()+"hash:"+hash);

        sysSpendBetweenInPointsService.scheduleTask(hash,"20","24");
    }
    @Async("scheduleTaskAsyncPool")
    @Scheduled(cron = "0 0/59 0-8 * * MON-FRI")//周一到周五，0-8点每一小时触发一次
    public void otherTImeConfigureTasks10() {//为了应对库里数据较多的时候，只对id % 3 == 0 找新数据
        int hash = 0;
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now()+"线程名字："+Thread.currentThread().getName()+"hash:"+hash);

        sysSpendBetweenInPointsService.scheduleTask(hash,"0","8");
    }
    @Async("scheduleTaskAsyncPool")
    @Scheduled(cron = "0 0/59 0-8 * * MON-FRI")//周一到周五，0-8点每一小时触发一次
    public void otherTImeConfigureTasks11() {//为了应对库里数据较多的时候，只对id % 3 == 1 找新数据
        int hash = 1;
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now()+"线程名字："+Thread.currentThread().getName()+"hash:"+hash);

        sysSpendBetweenInPointsService.scheduleTask(hash,"0","8");
    }
    @Async("scheduleTaskAsyncPool")
    @Scheduled(cron = "0 0/59 0-8 * * MON-FRI")//周一到周五，0-8点每一小时触发一次
    public void otherTImeConfigureTasks12() {//为了应对库里数据较多的时候，只对id % 3 == 2 找新数据
        int hash = 2;
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now()+"线程名字："+Thread.currentThread().getName()+"hash:"+hash);

        sysSpendBetweenInPointsService.scheduleTask(hash,"0","8");
    }

}