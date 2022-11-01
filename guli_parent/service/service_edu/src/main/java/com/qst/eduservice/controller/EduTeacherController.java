package com.qst.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qst.commonutils.R;
import com.qst.eduservice.entity.EduTeacher;
import com.qst.eduservice.entity.vo.TeacherQuery;
import com.qst.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 讲师(EduTeacher)表控制层
 *
 * @author 记住吾名梦寒
 * @since 2022-09-03 11:01:03
 *
 *      swagger的使用在service_base模块中的com.qst.baseservice.config.SwaggerConfig有介绍
 */
@Api(tags = "讲师管理")   //swagger注解之一，对该Controller类的描述
@RestController
@RequestMapping("/eduService/Teacher")
//@CrossOrigin  //解决跨域问题
public class EduTeacherController extends ApiController {
    //service层的对象
    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询所有讲师数据
     */
    @ApiOperation(value = "查询所有讲师列表")    //swagger注解之一，对该方法的描述
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    /**
     *  多条件查询+分页
     *  可以用@GetMapping  直接从请求头里面取数据，
     *  也可以用@PostMapping 从请求体里面取数据，但要求前端传过来的数据是json格式的且是在请求体中
     *  用required = false是因为可以不带条件查询,不带条件时就是查询所有
     */
    @ApiOperation("多条件查询+分页")
    @PostMapping("/conditions/{current}/{size}")
    public R conditions(@PathVariable Long current,
                        @PathVariable Long size,
                        @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建分页对象
        Page<EduTeacher> pageInfo = new Page<>(current, size);
        //创建lambdaQueryWrapper对象
        LambdaQueryWrapper<EduTeacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(teacherQuery != null){
            //取出teacherQuery中封装的条件
            String name = teacherQuery.getName();
            Integer level = teacherQuery.getLevel();
            String begin = teacherQuery.getBegin();
            String end = teacherQuery.getEnd();

            //组装查询条件
            if(StringUtils.hasText(name)){
                lambdaQueryWrapper.like(EduTeacher::getName,name);
            }
            if(level != null){
                lambdaQueryWrapper.eq(EduTeacher::getLevel,level);
            }
            if(StringUtils.hasText(begin)){
                lambdaQueryWrapper.ge(EduTeacher::getGmtCreate,begin);   //ge() 大于等于
            }
            if(StringUtils.hasText(end)){
                lambdaQueryWrapper.le(EduTeacher::getGmtCreate,end);     //le() 小于等于
            }
        }

        //组装排序条件(根据创建时间降序排列，这样新添加的就在前面)
        lambdaQueryWrapper.orderByDesc(EduTeacher::getGmtCreate);

        //查询
        eduTeacherService.page(pageInfo, lambdaQueryWrapper);
        //返回结果
        return R.ok().data("pageInfo",pageInfo);
    }

    /**
     * 新增讲师
     *
     * @param eduTeacher 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("/insert")
    public R insert(@RequestBody EduTeacher eduTeacher) {
        boolean flog = eduTeacherService.save(eduTeacher);
        if(flog){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     *    根据id查询讲师数据
     */
    @ApiOperation("根据id查询讲师数据")
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("eduTeacher",eduTeacher);
    }

    /**
     * 修改讲师数据
     *
     * @param eduTeacher 实体对象
     * @return 修改结果
     */
    @ApiOperation(value = "修改数据")
    @PutMapping("/update")
    public R update(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 删除讲师
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @ApiOperation(value = "删除数据")
    @DeleteMapping("/delete")
    public R delete(
            @ApiParam(name = "ids",value = "讲师ID",required = true)
            @RequestParam("ids") List<Long> ids) {  //这里用List是因为可以删除多个
        boolean flag = eduTeacherService.removeByIds(ids);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

