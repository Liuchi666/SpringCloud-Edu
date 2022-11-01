package excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/8
 * @description
 */
@Data
public class DemoData_read {
    /**
     *      @ExcelProperty注解： 用来设置excel文件和本实体类的对应关系
     *      注解中的value属性： 写操作时使用，用于指明生成的excel文件的表头名称
     *      注解中的index属性： 读操作时使用，用于指明excel文件中的表头跟实体类属性的对应关系
     */
    @ExcelProperty(index = 0)  //意为将excel文件中的第1个表头读取到实体类的sno属性中
    private  Integer sno;

    @ExcelProperty(index = 1)//意为将excel文件中的第2个表头读取到实体类的sno属性中
    private  String sname;

}
