package com.aguo.blogapi;

import com.aguo.blogapi.mapper.SysUserMapper;
import com.aguo.blogapi.pojo.SysUser;
import com.aguo.blogapi.service.LoginService;
import com.aguo.blogapi.service.SysUserService;
import com.aguo.blogapi.untils.QiniuUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BlogApiApplicationTests {

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private LoginService loginService;
    @Autowired
    private QiniuUtil qiniuUtil;

    @Autowired
    private SysUserService sysUserService;
    private static final String SALT = "1534774766AGuo";

    @Test
    void contextLoads() {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(SysUser::getId, SysUser::getAccount);
        List<SysUser> sysUsers = userMapper.selectList(null);
        for (SysUser sysUser : sysUsers) {
            sysUser.setPassword(DigestUtils.md5Hex(sysUser.getAccount()+SALT));
            userMapper.updateById(sysUser);
        }
    }
    @Test
    void test1(){
        System.out.println(qiniuUtil.getUrl());
    }


}
