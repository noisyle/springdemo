package com.noisyle.crowbar.repository;

import com.noisyle.crowbar.core.base.AbstractDao;
import com.noisyle.crowbar.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDao extends AbstractDao<User, Long> {

    public List<User> list() {
        List<User> list = getAll();
        return list;
    }

    public User getUserByLoginName(String loginName) {
        User user = findUniqueBy("loginName", loginName);
        return user;
    }

    public void insertUser(User user) {
        save(user);
    }

}
