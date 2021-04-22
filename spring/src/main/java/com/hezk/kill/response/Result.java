package com.hezk.kill.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.MDC;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author liuguanqing
 * created 2018/11/10 4:30 PM
 * 通用REST请求结果格式，支持debug模式，debug模式下可以输出
 **/
public class Result implements Serializable {

    private static final boolean DEFAULT_DEBUG_ENABLED;

    static {
        DEFAULT_DEBUG_ENABLED = Boolean.parseBoolean(System.getProperty("debug"));
    }

    /**
     * 请求调用是否成功
     */
    private boolean success = false;

    /**
     * 结果状态code
     */
    private transient ResultCode resultCode = ResultCode.SYSTEM_ERROR;


    private transient Boolean debugEnabled;

    private String exception;

    /**
     * 错误消息
     */
    private String message = resultCode.message;

    private Map<String, Long> pointers;//检测点

    /**
     * 用于保存执行的结果，K-V结构，不过调用者需要明确内部的约定
     */
    private final Map<String, Object> data = new HashMap<>(32);


    //响应的URL，需要跳转的URL;
    //可以指定responseUrl,以告知页面层面如何进行下一步操作。
    private String redirectUrl;

    private String token;//表单token值
    private String form;//form名称


    public Result() {
        this(false);
    }

    public Result(boolean success) {
        setSuccess(success);
        try {
            debugEnabled = Boolean.parseBoolean(MDC.get("debug")) || Boolean.parseBoolean(System.getProperty("DEBUG"));
        } catch (Exception e) {
            //
        }
    }

    public Result debug() {
        debugEnabled = true;
        return this;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("_redirect_url")
    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public boolean isSuccess() {
        return success;
    }

    private boolean isDebugEnabled() {
        return debugEnabled == null ? false : debugEnabled;
    }

    public void setSuccess(boolean success) {
        this.success = success;
        if (success) {
            setResultCode(ResultCode.SUCCESS);
        } else {
            setResultCode(ResultCode.UN_KNOWN);
        }
    }

    public void setSuccess(boolean success, String message) {
        this.success = success;
        if (success) {
            setResultCode(ResultCode.SUCCESS, message);
        } else {
            setResultCode(ResultCode.UN_KNOWN, message);
        }
    }

    public String getCode() {
        return resultCode.code;
    }

    @JsonIgnore
    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        setResultCode(resultCode, null);
    }

    public void setResultCode(ResultCode resultCode, String message) {
        this.resultCode = resultCode;
        this.message = resultCode.message;
        if (message != null) {
            this.message = message;
        }
        if (resultCode == ResultCode.SUCCESS) {
            success = true;
        } else {
            success = false;
        }
    }

    /**
     * 将result以异常的方式返回
     * 此时将会把resultCode重置为SYSTEM_ERROR
     * 此外，如果开启了debug功能，将会在result响应结果中展示exception栈信息
     *
     * @param throwable
     */
    public void exception(Throwable throwable) {
        exception(throwable, throwable.getMessage());
    }

    public void exception(Throwable throwable, String message) {
        exception(ResultCode.SYSTEM_ERROR, throwable, message);
    }

    public void exception(ResultCode rc, Throwable throwable, String message) {
        if (rc == null) {
            rc = ResultCode.SYSTEM_ERROR;
        }
        setResultCode(rc);
        setMessage(message);
        if (isDebugEnabled() || DEFAULT_DEBUG_ENABLED) {
            exception = ExceptionUtils.getRootCauseMessage(throwable);
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("_exception")
    public String getException() {
        return exception;
    }

    public String getMessage() {
        if (message != null) {
            return message;
        }
        if (resultCode != null) {
            return resultCode.message;
        }
        return null;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * 调用栈检测点追踪，mark作为key，value为时间戳
     * 当开启debug模式时，将会在result响应结果汇总打印每个mark执行的时间点
     * 此功能可以用于判断程序执行的效率。
     *
     * @param mark
     * @return
     */
    public Result pointer(String mark) {
        if (isDebugEnabled() || DEFAULT_DEBUG_ENABLED) {
            long time = System.currentTimeMillis();
            if (pointers == null) {
                pointers = new TreeMap<>();
            }
            pointers.put(mark, time);
        }
        return this;
    }

    /**
     * DEBUG信息
     *
     * @return
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("_pointers")
    public Map<String, Long> getPointers() {
        return this.pointers;
    }


    public Result putData(Map<String, Object> map) {
        if (map != null) {
            this.data.putAll(map);
        }
        return this;
    }

    public Result putData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     * 注意类型安全
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T get(String key) {
        return (T) this.data.get(key);
    }

    //获取数据副本
    public Map<String, Object> retain() {
        return new HashMap<>(this.data);
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public String render() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void token(String token) {
        this.token = token;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("_token")
    public String getToken() {
        return this.token;
    }

    public void form(String form) {
        this.form = form;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("_form")
    public String getForm() {
        return this.form;
    }

    public void clear() {
        this.data.clear();
        resultCode = ResultCode.SYSTEM_ERROR;
    }

}
