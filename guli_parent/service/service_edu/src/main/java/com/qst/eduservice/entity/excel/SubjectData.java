package com.qst.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/8
 * @description     excel文件的对应实体类  我们约定excel文件第一个表头为一级分类，第二个表头为二级分类
 */
@Data
public class SubjectData {
    @ExcelProperty(index = 0)    //将要读取的excel文件的第一个表头用此实体类的oneSubjectName属性接收
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;

}
