package com.qst.staservice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qst.staservice.entity.StatisticsDaily;

import java.util.Map;

/**
 * 网站统计日数据(StatisticsDaily)表服务接口
 *
 * @author 记住吾名梦寒
 * @since 2022-09-27 18:51:47
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void getRegisterCount(String day);

    Map<String, Object> getShowData(String type, String begin, String end);
}

