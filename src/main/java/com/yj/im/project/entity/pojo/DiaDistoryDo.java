package com.yj.im.project.entity.pojo;

import java.io.Serializable;

/**
 * @author liyongchang
 * @Date2020/4/9
 */
public class DiaDistoryDo implements Serializable {
    private static final long serialVersionUID = 4654687;
    String name;

    String phone;

    public DiaDistoryDo() {
    }

    public DiaDistoryDo(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
