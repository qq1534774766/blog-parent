package com.aguo.blogapi.enums;

/**
 * @Author: aguo
 * @DateTime: 2022/4/27 11:05
 * @Description: TODO
 */
public enum JWTSecretKey {
    SECRET_KEY("1534774766AGuo","JWT密钥");
    private final String code;
    private final String msg;

    JWTSecretKey(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
