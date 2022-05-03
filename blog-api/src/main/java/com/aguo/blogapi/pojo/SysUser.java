package com.aguo.blogapi.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SysUser {

    public SysUser(String nickname) {
        this.nickname = nickname;
    }

    public SysUser(Long id, String account, Integer admin, String avatar, Long createDate, Integer deleted, String email, Long lastLogin, String mobilePhoneNumber, String nickname, String password, String salt, String status) {
        this.id = id;
        this.account = account;
        this.admin = admin;
        this.avatar = avatar;
        this.createDate = createDate;
        this.deleted = deleted;
        this.email = email;
        this.lastLogin = lastLogin;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.nickname = nickname;
        this.password = password;
        this.salt = salt;
        this.status = status;
    }

    @TableId(type = IdType.AUTO)
    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;


    @TableLogic
    private Integer deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}
