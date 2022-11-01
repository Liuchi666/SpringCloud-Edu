package com.qst.cmsservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qst.cmsservice.entity.CrmBanner;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-03-07
 */
public interface CrmBannerService extends IService<CrmBanner> {

    //查询前两条bannerbanner
    List<CrmBanner> selectAllBanner();
}
