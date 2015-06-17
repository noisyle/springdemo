package com.noisyle.springdemo.admin.dao;

import org.springframework.stereotype.Repository;

import com.noisyle.springdemo.admin.entity.User;

@Repository("userDao")
public class UserDao {
	public User getUser() {
		User user = new User();
		user.setId("1");
		user.setName("Admin1");
		return user;
	}
}
