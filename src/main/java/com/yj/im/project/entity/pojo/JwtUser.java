package com.yj.im.project.entity.pojo;

import com.yj.im.project.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;


public class JwtUser {

    private Long id;
    private String phone;
    private String username;
    private String password;
    private String userImg;
    private String userAlias;
    private String email;
    private Date createTime;
    //    private List<SysMenuVo> links;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser() {
    }

    // 写一个能直接使用user创建jwtUser的构造器
    public JwtUser(SysUser user) {
        id = user.getId();
        phone = user.getPhone();
        username = user.getAccount();
        password = user.getPassword();
        createTime = user.getCreateTime();
        userImg = user.getImage();
        userAlias = user.getName();
        email = user.getEmail();
//        authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
