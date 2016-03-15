package com.bingzhilanmo.business.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bingzhilanmo.base.datasource.DataSource;
import com.bingzhilanmo.base.mapper.BaseMapper;
import com.bingzhilanmo.base.service.BaseServiceSupport;
import com.bingzhilanmo.business.mapper.NotShareMapper;
import com.bingzhilanmo.business.model.NotShare;

@Service
@DataSource("datasource1")
public class NotShareService extends BaseServiceSupport<NotShare, Long>  {

    @Resource
    private NotShareMapper notShareMapper;

    @Override
    public BaseMapper<NotShare, Long> getMapper() {
        return notShareMapper;
    }
}