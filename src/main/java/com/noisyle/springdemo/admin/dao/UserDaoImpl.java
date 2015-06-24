package com.noisyle.springdemo.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.noisyle.springdemo.admin.entity.User;
import com.noisyle.springdemo.common.exception.DAOException;
import com.noisyle.springdemo.common.util.SpringContextHolder;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public User getUser() {
		User user = (User) sqlSessionTemplate.selectOne("com.noisyle.springdemo.admin.entity.User.get", 1);
		System.out.println(user);
		return user;
	}
	
	public void init() {
		DataSource ds = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ds = SpringContextHolder.getBean("dataSource");
			conn = DataSourceUtils.getConnection(ds);
			
			try {
				ps = conn.prepareStatement("drop table demo_user");
				ps.execute();
			} catch (Exception e) {
			}
			ps = conn.prepareStatement("create table demo_user(id varchar, name varchar, loginname varchar, password varchar)");
			ps.execute();
			ps = conn.prepareStatement("insert into demo_user values ('1','管理员1','admin1','123456')");
			ps.execute();
			ps = conn.prepareStatement("insert into demo_user values ('2','管理员2','admin2','123456')");
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			throw new DAOException("保存失败", e);
		} finally {
		}
	}
	
}
