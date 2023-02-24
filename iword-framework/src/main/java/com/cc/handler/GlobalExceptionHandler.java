package com.cc.handler;

import com.cc.constants.HttpStatus;
import com.cc.domain.R;
import com.cc.exception.ValidateException;
import com.cc.exception.WxLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * @author CC
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(WxLoginException.class)
    public R<String> wxLoginException(Exception e) {
        log.error("出现了异常 " ,e);
        return R.fail(HttpStatus.ERROR,"服务器请求异常");
    }

    @ExceptionHandler(ValidateException.class)
    public R<String> validateException(Exception e) {
        log.error("出现了异常 " ,e);
        return R.fail(HttpStatus.VALIDATE_FAIL,e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public R<String> authenticationException(Exception e) {
        log.error("出现了异常 " ,e);
        return R.fail(HttpStatus.UNLOGINED,e.getMessage());
    }

    /**
     * 参数校验异常
     * @param e 异常
     * @return R
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class, BindException.class})
    public R<String> validateExceptions(Exception e) {
        log.error("出现了异常 " ,e);
        String error = "";
        if (e instanceof MethodArgumentNotValidException) {
            error = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage();

        } else if(e instanceof ConstraintViolationException) {
            error = ((ConstraintViolationException) e).getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList()).get(0);
        } else if (e instanceof BindException) {
            error = ((BindException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }

        return R.fail(HttpStatus.VALIDATE_FAIL,error);
    }


    @ExceptionHandler(Exception.class)
    public R<String> exceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了异常 " ,e);
        //从异常对象中获取提示信息封装返回
        String msg = e.getMessage();
        if (msg.length() > 100) {
            msg = msg.substring(0,30);
        }
        return R.fail(HttpStatus.ERROR,msg);
    }
}