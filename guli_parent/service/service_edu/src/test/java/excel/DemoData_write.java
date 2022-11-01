package excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/8
 * @description   excel文件对应的实体类
 */
@Data
public class DemoData_write {
    /**
     *      @ExcelProperty注解： 用来设置excel文件和本实体类的对应关系
     *      注解中的value属性： 写操作时使用，用于指明生成的excel文件的表头名称
     *      注解中的index属性： 读操作时使用，用于指明excel文件中的表头跟实体类属性的对应关系
     */
    @ExcelProperty("学生编号")
    private  Integer sno;

    @ExcelProperty("学生姓名")
    private  String sname;


}
