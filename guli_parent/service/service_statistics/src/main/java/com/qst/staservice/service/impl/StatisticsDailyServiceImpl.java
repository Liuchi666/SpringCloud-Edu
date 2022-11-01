package com.qst.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qst.commonutils.R;
import com.qst.staservice.dao.StatisticsDailyDao;
import com.qst.staservice.entity.StatisticsDaily;
import com.qst.staservice.feign.StaUcenterFeign;
import com.qst.staservice.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网站统计日数据(StatisticsDaily)表服务实现类
 *
 * @author 记住吾名梦寒
 * @since 2022-09-27 18:51:47
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyDao, StatisticsDaily> implements StatisticsDailyService {
    //注入feign接口
    @Autowired
    private StaUcenterFeign staUcenterFeign;

    /**
     * 统计某一天的注册人数
     */
    @Override
    public void getRegisterCount(String day) {
        //先判断数据库中当天是否已经有记录(有记录则先删除旧纪录，再插入新记录，没有记录则直接插入)
        LambdaQueryWrapper<StatisticsDaily> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StatisticsDaily::getDateCalculated, day);
        this.remove(queryWrapper);

        //远程调用service-uCenter模块中的方法，根据日期获取当天的注册人数
        R r = staUcenterFeign.getRegisterCount(day);
        Integer count = (Integer) r.getData().get("registerCount");

        //将获取的数据添加到数据库中统计分析表里面去
        StatisticsDaily statisticsDaily = StatisticsDaily.builder()
                .registerNum(count)
                .dateCalculated(day)
                .videoViewNum(RandomUtils.nextInt(100, 200))
                .loginNum(RandomUtils.nextInt(100, 200))
                .courseNum(RandomUtils.nextInt(100, 200))
                .build();
        this.save(statisticsDaily);
    }

    /**
     * 图表显示，返回两部分数据： 日期json数组，数量json数组
     */
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        //根据查询条件组装条件构造器，并查询对应数据
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .ge("date_calculated", begin) //大于等于
                .le("date_calculated", end);  //小于等于
        queryWrapper.select("date_calculated",type).orderByAsc("date_calculated");  //只查这两个字段
        //查询
        List<StatisticsDaily> staList = this.list(queryWrapper);

        //因为要返回日期和日期对应的数量两部分，且是数组格式的json
        //java中的list转成json是[x,x,x]格式，对象和map转成json是{x:x,x:x,x:x}格式
        //所以我们定义两个List集合，一个日期list，一个数量list
        List<String> dateList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();

        //遍历封装了日期和对应数量的staList
        for (StatisticsDaily statisticsDaily : staList) {
            //添加到日期list
            dateList.add(statisticsDaily.getDateCalculated());

            //添加到数量list
            //因为type的取值只有四种固定情况，所以使用switch进行判断
            switch(type){
                case "login_num":
                    numList.add(statisticsDaily.getLoginNum());
                    break;
                case "register_num":
                    numList.add(statisticsDaily.getRegisterNum());
                    break;
                case "video_view_num":
                    numList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(statisticsDaily.getCourseNum());
                    break;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("dateList",dateList);
        map.put("numList",numList);
        //返回数据
        return map;
    }
}

