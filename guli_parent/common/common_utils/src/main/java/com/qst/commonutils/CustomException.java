package com.qst.commonutils;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/5
 * @description     自定义业务异常类
 */
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
