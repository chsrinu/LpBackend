package com.lp.spring.model;

import javax.persistence.*;

@Entity(name = "user_role")
public class UserRole {
    private Long roleId;
    private User user;
    private String roleName;

    public UserRole() {
    }

    public UserRole(String roleName, User user) {
        this.roleName=roleName;
        this.user=user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
