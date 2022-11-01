package com.qst.baseservice.config.handler;


import com.qst.commonutils.CustomException;
import com.qst.commonutils.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/5
 * @description 全局异常处理
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
//@ControllerAdvice注解： 控制器增强， 放在类的上面， 表示此类提供了方法可以对凡是类上有以下annotations属性中列出的注解的增强其功能。
@ResponseBody  //以上两个注解可以用@RestControllerAdvice复合注解代替
public class GlobalExceptionHandler {
    /**
     * 异常处理方法
     *  这样报错信息就不会直接返回客户端了，而是返回我们定义的比较友好的报错提示
     * @ExceptionHandler注解：处理异常的，放在方法的上面,注解属性是想要处理的异常类型
     */
    @ExceptionHandler(Exception.class)   //当出现此注解参数中的异常类型时，就执行下面的方法
    public R exceptionHandler(Exception exception) {
        exception.printStackTrace();  //将报错信息输出到控制台，可以方便我们排错
        return R.error().message("执行了全局异常处理...");
    }

    /**
     *  自定义业务异常处理方法
     */
    @ExceptionHandler(CustomException.class)
    public R exceptionHandler(CustomException e){
        return R.error().message(e.getMessage());
    }


}
