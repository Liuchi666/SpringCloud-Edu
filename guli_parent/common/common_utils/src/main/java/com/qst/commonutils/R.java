package com.qst.commonutils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/3
 * @description      统一返回结果的类
 */
@Data
public class R {

    private Boolean success;

    private Integer code;

    private String message;

    private Map<String,Object> data = new HashMap<>();

    /**  将构造方法私有化   */
    private R(){}

    //成功静态方法
    public static R ok(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    //失败静态方法
    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    /**   以下这些方法返回this是有说法的，直接返回当前对象的话，就又可以通过当前对象调其他方法
     *      这就是链式调用  例如 对象.sucess(XXX).message(XXX).code(XXX)...
     */
    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }
    public R message(String message){
        this.setMessage(message);
        return this;
    }
    public R code(Integer code){
        this.setCode(code);
        return this;
    }
    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }
    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
