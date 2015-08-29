package com.noisyle.crowbar.model;

import com.noisyle.crowbar.core.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Role extends BaseModel {
    private String name;
    private String permission;
    private Integer status;
    @ManyToMany
    @JoinTable(name = "r_user_role", joinColumns = {@JoinColumn(name = "roleid")}, inverseJoinColumns = {@JoinColumn(name = "userid")})
    private List<User> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
