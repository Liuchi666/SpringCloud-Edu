package com.qst.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qst.commonutils.R;
import com.qst.eduservice.entity.EduComment;
import com.qst.eduservice.service.EduCommentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 评论(EduComment)表控制层
 *
 * @author 记住吾名梦寒
 * @since 2022-09-26 15:22:08
 */
@Api(tags = "课程评论")
@RestController
@RequestMapping("/eduService/eduComment")
public class EduCommentController extends ApiController {

    @Autowired
    private EduCommentService eduCommentService;

    /**
     *  根据课程id获取评论列表 + 分页
     */
    @GetMapping("/getPageList/{current}/{pageSize}/{courseId}")
    public R getPageList(@PathVariable long current,
                         @PathVariable long pageSize,
                         @PathVariable long courseId){
        //创建分页对象
        Page<EduComment> pageInfo = new Page<>(current,pageSize);
        //组装条件
        LambdaQueryWrapper<EduComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduComment::getCourseId,courseId);   //当前课程下的所有评论
        queryWrapper.orderByDesc(EduComment::getGmtCreate);  //按照评论时间降序排列
        eduCommentService.page(pageInfo,queryWrapper);

        boolean hasPrevious = pageInfo.hasPrevious();
        boolean hasNext = pageInfo.hasNext();
        //创建一个map来封装完整的分页信息(因为MP返回的分页信息里面没有是否有上一页，是否有下一页)
        Map<String, Object> coursePageMap = new HashMap<>();
        coursePageMap.put("pageInfo",pageInfo);
        coursePageMap.put("hasPrevious",hasPrevious);
        coursePageMap.put("hasNext",hasNext);

        //返回分页信息
        return R.ok().data("coursePageMap",coursePageMap);
    }

    /**
     *  发表评论(添加)
     */
    @PostMapping("/publish")
    public R publish(@RequestBody EduComment eduComment){
        return R.ok();
    }


}

