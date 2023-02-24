package com.cc.exception;

/**
 * @author cc
 * @version 1.0
 * @date 2023/2/12 15:24
 */

public class WxLoginException extends RuntimeException{
    private String msg;

    public WxLoginException(String msg) {
        super(msg);
    }
}
