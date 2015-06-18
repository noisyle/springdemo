package com.noisyle.springdemo.admin.dao;

import com.noisyle.springdemo.admin.entity.User;
import com.noisyle.springdemo.common.exception.DAOException;

public interface UserDao {
	public User getUser() throws DAOException;
	
	public void init() throws DAOException;
}
