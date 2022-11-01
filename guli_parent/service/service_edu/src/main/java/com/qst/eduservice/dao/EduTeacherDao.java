package com.qst.eduservice.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qst.eduservice.entity.EduTeacher;

/**
 * 讲师(EduTeacher)表数据库访问层
 *
 * @author 记住吾名梦寒
 * @since 2022-09-03 11:01:03
 */
//@Mapper  在配置类上加了@MapperScan注解就不用在每个接口上写@Mapper接口了
public interface EduTeacherDao extends BaseMapper<EduTeacher> {

}

