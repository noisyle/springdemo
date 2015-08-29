package com.noisyle.crowbar.core.auth;

import com.noisyle.crowbar.model.Role;
import com.noisyle.crowbar.model.User;
import com.noisyle.crowbar.repository.UserDao;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class HibernateUserRealm extends AuthorizingRealm {
    final protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;

    @Override
    public String getName() {
        return "userRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持 UsernamePasswordToken 类型的 Token
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 身份验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        User user = userDao.getUserByLoginName(token.getUsername());
        if(user != null) {
            return new SimpleAuthenticationInfo(user.getId(), user.getPassword(), getName());
        } else {
            return null;
        }
    }

    /**
     * 授权操作
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Long id = (Long) principals.fromRealm(getName()).iterator().next();
        User user = userDao.get(id);
        if(user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            for(Role role: user.getRoles()){
                info.addRole(role.getName());
                logger.debug(role.getName());
            }
            return info;
        } else {
            return null;
        }
    }

}
