package com.hezk.shiro.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@ConfigurationProperties(prefix = "user")
public class UserProperties {
    private String username;
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-ss HH:mm:dd", iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime currentTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }
}
