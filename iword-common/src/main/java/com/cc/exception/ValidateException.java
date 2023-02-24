package com.cc.exception;

/**
 * @author cc
 * @version 1.0
 * @date 2023/2/12 16:51
 * 校验异常
 */

public class ValidateException extends RuntimeException{
    String msg;

    public ValidateException(String msg) {
        super(msg);
    }
}
