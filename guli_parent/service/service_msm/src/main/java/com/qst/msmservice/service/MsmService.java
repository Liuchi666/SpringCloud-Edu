package com.qst.msmservice.service;

import java.util.Map;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/22
 * @description
 */
public interface MsmService {


    boolean send(String code, String phone);
}
