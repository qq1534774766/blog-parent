package com.aguo.blogapi.vo.params;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Author: aguo
 * @DateTime: 2022/4/26 13:35
 * @Description: TODO
 */
@Data
public class LoginParam {
    @NotNull
    private String account;
    @NotNull
    private String password;
    private String nickname;


    public LoginParam(String account) {
        this.account = account;
    }
    public LoginParam() {
    }
}
