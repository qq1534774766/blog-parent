package com.aguo.blogapi.enums;

import lombok.Data;

/**
 * @Author: aguo
 * @DateTime: 2022/4/27 11:00
 * @Description: TODO
 */

public enum Salt {
    SALT("1534774766AGuo","加密盐");
    private final String code;
    private final String msg;

    Salt(String code, String msg) {
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
