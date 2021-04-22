package com.hezk.kill.response;

import java.io.Serializable;
import java.util.Objects;

/**
 * 结果状态码
 */
public class ResultCode implements Serializable {

    public String code;//状态码

    public String message;//信息

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public static final ResultCode SUCCESS = new ResultCode("S00000", "成功");

    /**
     * 框架定义的错误码范围为00001 ~ 00100
     * 业务自定义的错误码，要么提交case给我们集成到框架中，要么声明在00100之外。
     */
    public static final ResultCode UN_KNOWN = new ResultCode("E00000", "未知错误");

    public static final ResultCode SYSTEM_ERROR = new ResultCode("E00001", "系统错误");

    public static final ResultCode IMAGE_FORMAT_ERROR = new ResultCode("E00002", "图片格式不合法");

    public static final ResultCode FILE_EXISTS_ERROR = new ResultCode("E00003", "文件已经存在");

    public static final ResultCode PARAMETER_ERROR = new ResultCode("E00004", "请求参数错误");

    public static final ResultCode FILE_NOT_EXISTS_ERROR = new ResultCode("E00005", "文件不存在");

    public static final ResultCode VALIDATE_FAILURE = new ResultCode("E00006", "验证失败");//验证失败

    public static final ResultCode NOT_LOGIN = new ResultCode("E00007", "您还未登陆");

    public static final ResultCode EXPIRED = new ResultCode("E00008", "表单过期，请刷新");//token过期

    public static final ResultCode PERMISSION_DENIED = new ResultCode("E00009", "权限不足");

    public static final ResultCode SMS_TIMES_LIMIT = new ResultCode("E00010", "短信发送次数超过限制");

    public static final ResultCode SMS_SENDING_INTERVAL_LIMIT = new ResultCode("E00011", "短信发送时间间隔过短");

    public static final ResultCode NETWORK_ERROR = new ResultCode("E00012", "网络异常");

    public static final ResultCode OPERATION_TIMES_LIMIT = new ResultCode("E00013", "操作次数超过限制");

    public static final ResultCode NOT_FOUND = new ResultCode("E00014", "资源或者数据不存在");

    public static final ResultCode ALREADY_EXIST_ERROR = new ResultCode("E00015", "资源或者数据已经存在");

    public static final ResultCode REJECTION_ERROR = new ResultCode("E00016", "请求被限制或者拒绝");

    public static ResultCode create(String code, String message) {
        return new ResultCode(code, message);
    }

    public static ResultCode create(String code) {
        return new ResultCode(code, null);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ResultCode)) {
            return false;
        }
        return code.equals(((ResultCode) obj).getCode());
    }
}
