package com.qst.staservice.schedule;

import com.qst.staservice.service.StatisticsDailyService;
import com.qst.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/27
 * @description   定时任务类  使用cron表达式设置执行规则(定时)
 *          cron表达式又称之为"七子表达式"、"七域表达式"(哪七? 年 月 周 日 时 分 秒)
 *          这个表达式不需要记，网上有工具在线生成  https://www.pppet.net/
 */
@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
     * 每天凌晨1点执行一次(把前一天的数据进行数据查询添加)
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task(){
        //通过工具类获取当前日期的前一天
        Date date = DateUtil.addDays(new Date(), -1);
        statisticsDailyService.getRegisterCount(DateUtil.formatDate(date));
    }

    //以下是两个示例:
    //每隔5秒执行一次
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void task01(){
//        System.out.println("5秒的定时器执行了");
//    }

    //每天凌晨一点执行
//    @Scheduled(cron = "0 0 1 * * ?")
//    public void task02(){
//        System.out.println("每天凌晨一点的定时器执行了");
//    }
}
