package com.cc.utils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cc
 * @version 1.0
 * @date 2023/1/31 23:03
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    public static <T> T copyBean(Object src,Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
            copyProperties(src, t);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T> List<T> copyBeanList(List<?> srcList , Class<T> clazz) {
        return srcList.stream().map(o -> copyBean(o, clazz)).collect(Collectors.toList());
    }
}
