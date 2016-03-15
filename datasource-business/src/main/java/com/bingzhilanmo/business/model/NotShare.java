package com.bingzhilanmo.business.model;

import java.io.Serializable;

/**
 * 
 * @author bowen_wang
 * 部分库的 只在一个库里面
 */
public class NotShare implements Serializable {

    private static final long serialVersionUID = -1L;

    /** id : **/
    private Long id;
    /** info : **/
    private String info;

    public NotShare() {
        
    }

    public NotShare(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public NotShare setId(Long id) {
        this.id = id;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public NotShare setInfo(String info) {
        this.info = info;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", info=").append(info);
        sb.append("]");
        return sb.toString();
    }
}