package com.aguo.blogapi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: aguo
 * @DateTime: 2022/4/24 20:29
 * @Description: 所有返回前端的数据，统统放到这里！
 */
@Data
@AllArgsConstructor
public class AGuoResult {
    /**
     * 不允许其他任何类new出结果对象，只能使用静态success或者failed方法来调用
     */
    private AGuoResult(){}
    private boolean success;
    private Integer code;
    private String msg;
    private Object data;

    public static AGuoResult success(Object data){
        return new AGuoResult(true,200,"success",data);
    }
    public static AGuoResult failed(Integer code,String msg){
        return new AGuoResult(false,code,msg,null);
    }
}
