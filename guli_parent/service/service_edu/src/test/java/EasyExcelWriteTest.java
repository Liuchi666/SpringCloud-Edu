import com.alibaba.excel.EasyExcel;
import excel.DemoData_write;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/8
 * @description
 */

public class EasyExcelWriteTest {
    /**
     *  实现EasyExcel写操作
     */
    @Test
    public void  testEasyExcel(){
        //1.设置写入的文件位置和excel文件名称
        String filename = "E:\\a\\a.xlsx";

        //通过循环生成写入文件的内容
        List<DemoData_write> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData_write data = new DemoData_write();
            data.setSno(i);
            data.setSname("zhangsan" + i);
            list.add(data);
        }

        //2.调用easyexcel里面的方法实现写操作
        //write()参数： 第一个参数是文件路径名称，第二个参数是excel文件对应的实体类class
        EasyExcel.write(filename, DemoData_write.class).sheet("学生列表").doWrite(list);

    }
}
