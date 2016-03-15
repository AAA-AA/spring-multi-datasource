package com.bingzhilanmo.business.model;

import java.io.Serializable;

public class user implements Serializable {

    private static final long serialVersionUID = -1L;

    /** id : **/
    private Long id;
    /** name : **/
    private String name;
    /** pwd : **/
    private String pwd;
    /** insert_time : **/
    private Integer insertTime;
    /** update_time : **/
    private Integer updateTime;

    public user() {
        
    }

    public user(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public user setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public user setName(String name) {
        this.name = name;
        return this;
    }

    public String getPwd() {
        return pwd;
    }

    public user setPwd(String pwd) {
        this.pwd = pwd;
        return this;
    }

    public Integer getInsertTime() {
        return insertTime;
    }

    public user setInsertTime(Integer insertTime) {
        this.insertTime = insertTime;
        return this;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public user setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", pwd=").append(pwd);
        sb.append(", insertTime=").append(insertTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}