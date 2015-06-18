package com.noisyle.springdemo.admin.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noisyle.springdemo.admin.dao.UserDao;
import com.noisyle.springdemo.admin.entity.User;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
	@Autowired
	private UserDao userDao;
	
	public User getUser() {
		try {
			return userDao.getUser();
		} catch (Exception e) {
			return null;
		}
	}
	
	public void init() {
		try {
			LoggerFactory.getLogger(this.getClass()).warn("userDao:"+userDao.toString());
			userDao.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
