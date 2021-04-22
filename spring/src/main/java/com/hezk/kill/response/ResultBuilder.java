package com.hezk.kill.response;

public final class ResultBuilder {


    public static Result create(String code, String message) {
        ResultCode resultCode = ResultCode.create(code, message);
        return create(resultCode);
    }

    public static Result create(ResultCode resultCode, String message) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setMessage(message);
        return result;
    }

    public static Result create(ResultCode resultCode) {
        return create(resultCode, null);
    }

    public static Result create() {
        return create(ResultCode.UN_KNOWN);
    }

    public static Result success() {
        return success(null);
    }

    public static Result success(String message) {
        return create(ResultCode.SUCCESS, message);
    }


    public static Result systemError() {
        return systemError(null);
    }

    public static Result systemError(String message) {
        return systemError(message, null);
    }

    public static Result systemError(String message, Exception exception) {
        Result result = create();
        result.exception(ResultCode.SYSTEM_ERROR, exception, message);
        return result;
    }

    public static Result validateFailure() {
        return validateFailure(null);
    }

    public static Result validateFailure(String message) {
        return create(ResultCode.VALIDATE_FAILURE, message);
    }

    public static Result parameterError() {
        return parameterError(null);
    }

    public static Result parameterError(String message) {
        return parameterError(message, null);
    }

    public static Result parameterError(String message, Exception exception) {
        Result result = create();
        result.exception(ResultCode.PARAMETER_ERROR, exception, message);
        return result;
    }

    public static Result notLogin() {
        return notLogin(null);
    }

    public static Result notLogin(String message) {
        return create(ResultCode.NOT_LOGIN, message);
    }

    public static Result expired() {
        return expired(null);
    }

    public static Result expired(String message) {
        return create(ResultCode.EXPIRED, message);
    }

    public static Result permissionDenied() {
        return permissionDenied(null);
    }

    public static Result permissionDenied(String message) {
        return create(ResultCode.PERMISSION_DENIED, message);
    }

    public static Result networkError() {
        return networkError(null);
    }

    public static Result networkError(String message) {
        return networkError(message, null);
    }

    public static Result networkError(String message, Exception exception) {
        Result result = create();
        result.exception(ResultCode.NETWORK_ERROR, exception, message);
        return result;
    }


    public static Result notFound() {
        return notFound(null);
    }

    public static Result notFound(String message) {
        return create(ResultCode.NOT_FOUND, message);
    }

    public static Result alreadyExistError() {
        return alreadyExistError(null);
    }

    public static Result alreadyExistError(String message) {
        return create(ResultCode.ALREADY_EXIST_ERROR, message);
    }

    public static Result rejectionError() {
        return rejectionError(null);
    }

    public static Result rejectionError(String message) {
        return create(ResultCode.REJECTION_ERROR, message);
    }

}
