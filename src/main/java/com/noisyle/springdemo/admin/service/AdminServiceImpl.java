package com.noisyle.springdemo.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noisyle.springdemo.admin.dao.UserDao;
import com.noisyle.springdemo.admin.entity.User;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private UserDao userDao;
	
	public User getUser() {
		return userDao.getUser();
	}
}
