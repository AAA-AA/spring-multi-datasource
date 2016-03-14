package com.bingzhilanmo.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bingzhilanmo.base.result.ApiResult;
import com.bingzhilanmo.business.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/getById")
	public ApiResult getUser(Integer id){
		return new ApiResult(userService.getById(id));
	}
	
	@RequestMapping("/addUser")
	public ApiResult addUser(){
		
		for (int i = 1; i < 20; i++) {
			userService.AddUsers(i, "test"+i, "test"+i, 34343434);
		}
		
		return new ApiResult();
	}

}
