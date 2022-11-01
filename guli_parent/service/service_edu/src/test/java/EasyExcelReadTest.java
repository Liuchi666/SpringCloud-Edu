import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.ReadListener;
import excel.DemoData_read;
import excel.DemoData_write;
import excel.ExcelListener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/8
 * @description
 */
public class EasyExcelReadTest {
    /**
     *  实现EasyExcel写操作
     */
    @Test
    public void  testEasyExcel(){
        //1.设置想要读取的excel文件位置和excel文件名称
        String filename = "E:\\a\\a.xlsx";

        //2.调用EasyExcel里面的方法实现读操作
        //read()参数： 第一个参数是文件路径名称，第二个参数是excel文件对应的实体类class,
        // 第三个参数是实现读操作的监听器对象
        EasyExcel.read(filename, DemoData_read.class,new ExcelListener()).sheet().doRead();

    }
}
