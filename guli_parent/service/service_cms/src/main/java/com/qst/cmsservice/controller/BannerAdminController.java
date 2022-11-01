package com.qst.cmsservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qst.cmsservice.entity.CrmBanner;
import com.qst.cmsservice.service.CrmBannerService;
import com.qst.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

/**
 * 后台banner管理接口
 */
@Api("后台banner管理接口")
@RestController
@RequestMapping("/eduCms/bannerAdmin")
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     *    分页查询banner
     */
    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<CrmBanner> pageInfo = new Page<>(page,limit);
        bannerService.page(pageInfo,null);
        return R.ok().data("pageInfo",pageInfo);
    }


    /**
     *    添加banner
     */
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return R.ok();
    }

    @ApiOperation(value = "根据id获取Banner")
    @GetMapping("/get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("banner", banner);
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("/update")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "根据id删除Banner")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }
}

