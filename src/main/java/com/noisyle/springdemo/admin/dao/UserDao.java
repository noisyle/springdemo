package com.noisyle.springdemo.admin.dao;

import com.noisyle.springdemo.admin.entity.User;

public interface UserDao {
	public User getUser();
	
	public void init();
}
