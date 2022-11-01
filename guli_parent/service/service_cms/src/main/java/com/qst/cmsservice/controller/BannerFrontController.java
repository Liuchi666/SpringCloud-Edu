package com.qst.cmsservice.controller;


import com.qst.cmsservice.entity.CrmBanner;
import com.qst.cmsservice.service.CrmBannerService;
import com.qst.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台bannber显示
 */
@Api("前台bannber显示")
@RestController
@RequestMapping("/eduCms/bannerFront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService crmBannerService;

    /**
     *   查询前两条banner
     */
    //缓存注解用法详见service_base模块中的RedisConfig配置类的注释
    @Cacheable(value = "banner",key = "'selectIndexList'")  //这种写法底层会将存进缓存的数据的key拼接为 banner::selectIndexList
    @GetMapping("/getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = crmBannerService.selectAllBanner();
        return R.ok().data("list",list);
    }

}

