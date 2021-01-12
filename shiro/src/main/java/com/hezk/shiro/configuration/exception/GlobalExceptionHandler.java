package com.hezk.shiro.configuration.exception;

import com.google.common.collect.Maps;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> defaultErrorHandler(HttpServletRequest request, Exception e) {
        return Maps.newHashMap();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Map<String, Object> httpRequestMethodHandler() {
        return Maps.newHashMap();
    }


    @ExceptionHandler(UnauthorizedException.class)
    public Map<String, Object> unauthorizedExceptionHandler() {
        return Maps.newHashMap();
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public Map<String, Object> unauthenticatedException() {
        return Maps.newHashMap();
    }
}
