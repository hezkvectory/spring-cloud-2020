package com.hezk.kill.controller;

import com.hezk.kill.domain.User;
import com.hezk.kill.mapper.UserMapper;
import com.hezk.kill.response.Result;
import com.hezk.kill.response.ResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping(value = "/login")
    public Result login(@RequestParam String userName, @RequestParam String password, HttpServletResponse response) {
        User user = userMapper.selectByUserName(userName);
        if (user == null) {
            return ResultBuilder.notFound("用户不存在");
        }
        response.addHeader("username", userName);
        response.addHeader("userId", "" + user.getId());
        if (user.getPassword().equals(password)) {
            return ResultBuilder.success();
        } else {
            return ResultBuilder.validateFailure("密码错误");
        }
    }

    @GetMapping(value = "/logout")
    public Result logout(HttpServletResponse response) {
        response.addHeader("username", "");
        response.addHeader("userId", "");
        return ResultBuilder.success();
    }

    @GetMapping(value = "user/init")
    public Result init() {
        for (int i = 0; i < 1000; i++) {
            User user = new User();
            user.setUserName("user" + i);
            user.setPassword("pass" + i);
            user.setPhone("185" + i);
            user.setEmail("185" + i + "@163.com");
            user.setIsActive(Byte.valueOf("1"));
            userMapper.insert(user);
        }
        return ResultBuilder.success();
    }
}