package com.qst.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qst.commonutils.CustomException;
import com.qst.eduservice.entity.EduSubject;
import com.qst.eduservice.entity.excel.SubjectData;
import com.qst.eduservice.service.EduSubjectService;

import java.util.Map;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/8
 * @description
 */

/**
 *  特别注意，官网文档明确指明：不能将SubjectExcelListener交给Spring管理，使用时需要自己手动new,
 *  这里就有一个问题，没有交给spring管理的类中的属性是不能通过@Resource、@Autowired等方式注入的，也就意味着不能通过传统方法操作数据库了，
 *  解决方案：
 *      在EduSubjectServiceImpl中 手动new一个SubjectExcelListener时通过构造器传入在EduSubjectServiceImpl中被注入过的属性eduSubjectService，
 *      SubjectExcelListener中声明一个构造器，接收传入的被注入过的eduSubjectService，并赋给本类中的eduSubjectService
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //声明一个普通属性
    private EduSubjectService eduSubjectService;

    //声明一个有参构造，接收在别处被注入过的eduSubjectService
    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }
    public SubjectExcelListener() {
    }

    /**   EasyExcel读取excel文件是一行一行进行读取的，读一行就将读到的内容存进DemoData_read中去 */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        //如果没有读取到数据
        if(subjectData == null){
            throw new CustomException("文件数据为空");
        }
        //判断一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(subjectData.getOneSubjectName());
        if(existOneSubject == null){
            //说明数据库中没有该分类
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(existOneSubject);
        }else {
            System.out.println("一级分类中" + subjectData.getOneSubjectName() + "已存在.................");
        }

        //获取一级分类的id值
        String pid = existOneSubject.getId();  //这里仔细琢磨为什么可以这样  假如一级分类已经存在，则可以直接取到一级分类的id，
        // 假如不存在，就会new一个，id由mp雪花算法完成，且id在save,insert等操作后会将id回设给实体类，所以也能取到

        //判断二级分类是否重复
        EduSubject existTwoSubject = this.existTwoSubject(subjectData.getTwoSubjectName(), pid);
        if(existTwoSubject == null){
            //说明数据库中二级分类中不存在该课程，就插入这条语句
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(existTwoSubject);
        }else {
            System.out.println("二级分类中" + subjectData.getTwoSubjectName() + "已存在.................");
        }

    }

    /**   用于判断一级分类不能重复添加 一级分类的特点: 父ID为0
     *      sql语句： select * from edu_subject where title = ?  and parent_id = 0        */
    private EduSubject existOneSubject(String name){
        LambdaQueryWrapper<EduSubject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduSubject::getTitle,name);
        wrapper.eq(EduSubject::getParentId,"0");
        EduSubject eduSubject = eduSubjectService.getOne(wrapper);
        return eduSubject;
    }

    /**   用于判断二级分类不能重复添加 二级分类的特点: 父ID为不为0,为一级分类课程的id
     *      sql语句： select * from edu_subject where title = ?  and parent_id = ?     */
    private EduSubject existTwoSubject(String name,String pid){
        LambdaQueryWrapper<EduSubject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduSubject::getTitle,name);
        wrapper.eq(EduSubject::getParentId,pid);
        EduSubject eduSubject = eduSubjectService.getOne(wrapper);
        return eduSubject;
    }


    /**   判断一级分类不能重复添加  */

    /**   用于读取表头内容  */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {

    }

    /**   读取完成之后执行此方法  */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {


    }

}
