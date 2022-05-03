package com.aguo.blogadmin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * @Author: aguo
 * @DateTime: 2022/5/3 16:13
 * @Description: TODO
 */
@Data
@AllArgsConstructor
public class AGuoResult {
    private Boolean success;
    private Integer code;
    private String message;
    private Object data;

    public AGuoResult(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public static AGuoResult success(Object data){
        return new AGuoResult(true,200,"success",data);
    }
    public static AGuoResult fail(Integer code,String message){
        return new AGuoResult(false,code,message);
    }
}
