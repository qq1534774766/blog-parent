package com.aguo.blogapi.service;

import com.aguo.blogapi.pojo.SysUser;
import com.aguo.blogapi.vo.AGuoResult;
import com.aguo.blogapi.vo.UserVo;
import com.aguo.blogapi.vo.params.LoginParam;

/**
 * @Author: aguo
 * @DateTime: 2022/4/25 1:11
 * @Description: TODO
 */
public interface SysUserService {
    /**
     * 通过用户id查询用户对象
     * @param id
     * @return
     */
    SysUser getUserById(Long id);

    /**
     * 根据用户名和密码查找数据库中的用户
     * @param loginParam
     * @return id，account，nickname，avatar
     */
    SysUser findUserByUsernameAndPassword(LoginParam loginParam);

    /**
     * 根据token查询用户信息
     * @param token
     * @return id，account，nickname，avatar
     */
    AGuoResult findUserByToken(String token);

    /**
     * 注册用户，插入到数据库中
     * @param param
     * @return true：成功插入
     */
    Boolean insertRegisterUser(LoginParam param);

    UserVo getUserVoById(Long authorId);
}
