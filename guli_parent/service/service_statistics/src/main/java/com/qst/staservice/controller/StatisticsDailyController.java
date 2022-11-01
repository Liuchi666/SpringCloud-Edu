package com.qst.staservice.controller;


import com.qst.commonutils.R;
import com.qst.staservice.entity.StatisticsDaily;
import com.qst.staservice.feign.StaUcenterFeign;
import com.qst.staservice.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;


/**
 * 网站统计日数据(StatisticsDaily)表控制层
 *
 * @author 记住吾名梦寒
 * @since 2022-09-27 18:51:46
 */
@RestController
@RequestMapping("/staService/sta")
@CrossOrigin(origins = "*")
public class StatisticsDailyController{

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
     *  统计某一天的注册人数
     */
    @GetMapping("/getRegisterCount/{day}")
    public R getRegisterCount(@PathVariable String day){
        statisticsDailyService.getRegisterCount(day);
        return R.ok();
    }

    /**
     *  图表显示，返回两部分数据： 日期json数组，数量json数组
     */
    @GetMapping("/showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String,Object> map = statisticsDailyService.getShowData(type,begin,end);
        return R.ok().data(map);
    }


}

