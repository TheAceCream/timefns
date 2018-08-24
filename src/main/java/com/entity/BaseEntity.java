package com.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/8/23 18:28
 * Time: 14:15
 *
 * 优化结构
 * 这个结构 权当提供思路
 *
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 5908489787919929004L;

    private String name;

    private Integer value;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
