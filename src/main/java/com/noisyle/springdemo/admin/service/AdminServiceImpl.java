package com.noisyle.springdemo.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noisyle.springdemo.admin.dao.UserDao;
import com.noisyle.springdemo.admin.entity.User;
import com.noisyle.springdemo.common.exception.ServiceException;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
	@Autowired
	private UserDao userDao;
	
	public User getUser() {
		return userDao.getUser();
	}
	
	public void init() {
		try {
			userDao.init();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("操作失败", e);
		}
	}
}
