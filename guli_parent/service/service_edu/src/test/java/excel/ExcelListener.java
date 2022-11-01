package excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/8
 * @description     EasyExcel读操作要写一个监听器，即定义一个类继承AnalysisEventListener<excel文件对应的实体类>
 */
public class ExcelListener extends AnalysisEventListener<DemoData_read> {
    /**   EasyExcel读取excel文件是一行一行进行读取的，读一行就将读到的内容存进DemoData_read中去 */
    @Override
    public void invoke(DemoData_read demoData_read, AnalysisContext analysisContext) {
        System.out.println("*****" + demoData_read);
    }

    /**   用于读取表头内容  */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头:" + headMap);
    }

    /**   读取完成之后执行此方法  */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("读取完成");
    }
}
