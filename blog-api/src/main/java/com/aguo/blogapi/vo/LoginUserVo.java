package com.aguo.blogapi.vo;

import lombok.Data;

/**
 * @Author: aguo
 * @DateTime: 2022/4/26 22:10
 * @Description: TODO
 */
@Data
public class LoginUserVo {
    private String id;
    private String account;
    private String nickname;
    private String avatar;
}
