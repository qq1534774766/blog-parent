package com.aguo.blogapi.enums;

public enum  ErrorCode {

    PARAMS_ERROR(10001,"参数有误"),
    ACCOUNT_PWD_NOT_EXIST(10002,"用户名或密码不存在"),
    TOKEN_ILLEGAL(10003,"Token不合法"),
    TOKEN_TIMEOUT(10004,"Token过期"),
    TOKEN_CACHE_FAILURE(10005,"用户已经登出，或Token缓存失效或不存在"),
    USER_ALREADY_EXISTS(10006,"用户名已存在"),
    NO_PERMISSION(70001,"无访问权限"),
    SESSION_TIME_OUT(90001,"会话超时"),
    NO_LOGIN(90002,"未登录"),
    FILE_UPLOAD_ERROR(20001,"文件上传失败"),
    NOT_BLANK(9003,"不能为空");

    private int code;
    private String msg;

    ErrorCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
