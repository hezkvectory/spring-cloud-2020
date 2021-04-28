package com.hezk.kill.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class KillDto implements Serializable {

    @NotNull
    private Integer killId;

    private Integer userId;

    public KillDto() {
    }

    public KillDto(@NotNull Integer killId, Integer userId) {
        this.killId = killId;
        this.userId = userId;
    }

    public Integer getKillId() {
        return killId;
    }

    public void setKillId(Integer killId) {
        this.killId = killId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}