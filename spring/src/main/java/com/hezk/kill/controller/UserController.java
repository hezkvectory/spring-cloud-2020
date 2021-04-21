package com.hezk.kill.controller;

import com.hezk.kill.domain.User;
import com.hezk.kill.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String userName, @RequestParam String password, HttpServletResponse response) {
        User user = userMapper.selectByUserName(userName);
        response.addHeader("username", userName);
        response.addHeader("userId", "" + user.getId());
        if (user.getPassword().equals(password)) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        return "login";
    }
}