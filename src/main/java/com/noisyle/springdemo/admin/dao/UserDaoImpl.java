package com.noisyle.springdemo.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.noisyle.springdemo.admin.entity.User;
import com.noisyle.springdemo.common.exception.DAOException;
import com.noisyle.springdemo.common.util.SpringContextHolder;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
	public User getUser() {
		User user = new User();
		DataSource ds = null;
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;
		try {
			ds = SpringContextHolder.getBean("dataSource");
			conn = DataSourceUtils.getConnection(ds);
			
			ps = conn.prepareStatement("select * from demo_user where id = ?");
			ps.setString(1, "1");
			rs = ps.executeQuery();
			user.setId(rs.getString("ID"));
			user.setName(rs.getString("NAME"));
			user.setLoginname(rs.getString("LOGINNAME"));
			user.setPassword(rs.getString("PASSWORD"));
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new DAOException("查询失败", e);
		} finally {
		}
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
