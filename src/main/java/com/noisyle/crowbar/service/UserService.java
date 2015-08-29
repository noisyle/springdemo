package com.noisyle.crowbar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noisyle.crowbar.model.User;
import com.noisyle.crowbar.repository.UserDao;

@Service("userService")
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public List<User> list() {
		return userDao.list();
	}
}
