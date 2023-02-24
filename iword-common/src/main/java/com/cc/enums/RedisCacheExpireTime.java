package com.cc.enums;

/**
 * @author cc
 * @version 1.0
 * @date 2023/2/12 17:10
 */
public enum RedisCacheExpireTime {
    //一级
    ONE(1),
    //二级
    TWO(15),
    //三级
    Three(30);

    final Integer time;

    RedisCacheExpireTime(Integer time) {
        this.time = time;
    }

    public Integer getTime() {
        return this.time;
    }
}
