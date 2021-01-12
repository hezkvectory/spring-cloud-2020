package com.hezk.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/auth")
    public Map<String, Object> authLogin(@RequestParam(value = "username") String username,
                                         @RequestParam(value = "password") String password) {
        System.out.println(username + ":" + password);

        Map<String, Object> info = new HashMap<>();
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            currentUser.login(token);
            info.put("result", "success");
        } catch (AuthenticationException e) {
            info.put("result", "fail");
        }
        return new HashMap<>();
    }

    @RequiresPermissions(value = {"main"})
    @GetMapping("/info")
    public Object info() {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.getPrincipal();
    }

    @GetMapping("/getInfo")
    public Object getInfo() {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.getPrincipal();
    }

    @GetMapping("/logout")
    public Map<String, Object> logout() {
        return new HashMap<>();
    }
}
