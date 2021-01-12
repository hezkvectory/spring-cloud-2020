package com.hezk.shiro.configuration.exception;

import com.google.common.collect.Maps;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    public Map<String, Object> handleError() {
        return Maps.newHashMap();
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
