package com.qst.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qst.eduservice.dao.EduSubjectDao;
import com.qst.eduservice.entity.EduSubject;
import com.qst.eduservice.entity.excel.SubjectData;
import com.qst.eduservice.entity.subject.OneSubject;
import com.qst.eduservice.entity.subject.TwoSubject;
import com.qst.eduservice.listener.SubjectExcelListener;
import com.qst.eduservice.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程科目(EduSubject)表服务实现类
 *
 * @author 记住吾名梦寒
 * @since 2022-09-08 12:00:47
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectDao, EduSubject> implements EduSubjectService {

    /** 添加课程    */
    @Override
    public void saveSubject(MultipartFile file) {
        try {
            //获取字节输入流
            InputStream in = file.getInputStream();
            //读取excel文件
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(this)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  课程分类列表(树形)
     */
    @Override
    public List<OneSubject> getOneTwoSubject() {
        //查询所有的一级分类(一级分类的ParentId为0)
        LambdaQueryWrapper<EduSubject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduSubject::getParentId,"0");
        List<EduSubject> oneSubjectList = this.list(wrapper);  //此时list集合中的数据还不是最终返回的数据

        //查询所有的二级分类
        LambdaQueryWrapper<EduSubject> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.ne(EduSubject::getParentId,"0");   // ne() 不等于
        List<EduSubject> twoSubjectList = this.list(wrapper2);

        //创建最终返回的集合
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //遍历一级分类课程集合,
        for (EduSubject eduSubject : oneSubjectList) {
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);  //spring提供的BeanUtils中的对象拷贝，这种是浅拷贝

            //创建List集合用来封装二级课程分类对象
            ArrayList<TwoSubject> twoList = new ArrayList<>();
            //遍历二级分类课程集合
            for (EduSubject eduSubject2 : twoSubjectList) {
                //将所有二级分类中的数据按照一级分类的id进行抽离
                if(eduSubject2.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduSubject2,twoSubject);
                    twoList.add(twoSubject);
                }
            }
            //将本次一级分类下面的所有二级分类封装进一级分类中
            oneSubject.setChildren(twoList);
            //将封装了一级分类对象信息和其下所有二级分类对象信息的对象存进list集合
            finalSubjectList.add(oneSubject);
        }

        return finalSubjectList;
    }
}

