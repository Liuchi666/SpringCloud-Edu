package com.qst.cmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qst.cmsservice.dao.CrmBannerDao;
import com.qst.cmsservice.entity.CrmBanner;
import com.qst.cmsservice.service.CrmBannerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-03-07
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerDao, CrmBanner> implements CrmBannerService {
    /**  查询前两条banner  */
    @Override
    public List<CrmBanner> selectAllBanner() {
        //根据id进行降序排列，显示排序之后前两条记录
        LambdaQueryWrapper<CrmBanner> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(CrmBanner::getId);
        //last方法，拼接sql语句，有sql注入的风险，请谨慎使用
        queryWrapper.last("limit 2");
        List<CrmBanner> list = this.list(queryWrapper);
        return list;
    }



}
