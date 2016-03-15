package com.bingzhilanmo.business.service;

import com.bingzhilanmo.base.datasource.DataSource;
import com.bingzhilanmo.base.mapper.BaseMapper;
import com.bingzhilanmo.base.service.BaseServiceSupport;
import com.bingzhilanmo.business.mapper.userMapper;
import com.bingzhilanmo.business.model.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
@DataSource(fixed=false,value="user")
public class UserService extends BaseServiceSupport<user, Long>{

    @Resource
    private userMapper userMapper;

    @Override
    public BaseMapper<user, Long> getMapper() {
        return userMapper;
    }
    
    public void AddUsers(Long id,String name,String pwd,Integer insertTime){
    	user user=new user();
    	user.setId(id);
    	user.setName(name);
    	user.setPwd(pwd);
    	user.setInsertTime(insertTime);
    	userMapper.save(user);
    }
    
    
}