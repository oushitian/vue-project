package com.netty.rpc.api.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author xyl
 * @Create 2018-11-28 15:35
 * @Desc
 **/
public class Person implements Serializable {
    private int id;

    private String name;

    private Date birth;

    private boolean man;

    private List<String> list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public boolean isMan() {
        return man;
    }

    public void setMan(boolean man) {
        this.man = man;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birth=" + birth +
                ", man=" + man +
                ", list=" + list +
                '}';
    }
}
