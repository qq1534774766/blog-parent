package com.aguo.blogapi.service.impl;

import com.aguo.blogapi.enums.Salt;
import com.aguo.blogapi.mapper.SysUserMapper;
import com.aguo.blogapi.pojo.SysUser;
import com.aguo.blogapi.service.LoginService;
import com.aguo.blogapi.service.SysUserService;
import com.aguo.blogapi.vo.AGuoResult;
import com.aguo.blogapi.vo.LoginUserVo;
import com.aguo.blogapi.vo.UserVo;
import com.aguo.blogapi.vo.params.LoginParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: aguo
 * @DateTime: 2022/4/25 1:12
 * @Description: TODO
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private LoginService loginService;

    @Override
    public UserVo getUserVoById(Long authorId) {
        UserVo userVo = new UserVo();
        SysUser sysUser = sysUserMapper.selectById(authorId);
        BeanUtils.copyProperties(sysUser, userVo);
        userVo.setId(String.valueOf(authorId));
        return userVo;
    }

    @Override
    public SysUser getUserById(Long id) {
        if (id == null) {return new SysUser("阿果");}
        return sysUserMapper.selectById(id);
    }
    @Override
    public SysUser findUserByUsernameAndPassword(LoginParam loginParam) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(loginParam.getAccount()!=null,SysUser::getAccount,loginParam.getAccount());
        queryWrapper.eq(loginParam.getPassword()!=null,SysUser::getPassword,loginParam.getPassword());
        queryWrapper.select(SysUser::getId,SysUser::getAccount,SysUser::getAvatar,SysUser::getNickname);
//        提高效率，这里或者用联合索引。同时避免selectOne出现多条结果，不过不可能就是了
        queryWrapper.last("limit 1");

        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public AGuoResult findUserByToken(String token) {
        /**
         * 1. 校验token是否合法
         *      合法：Jwt验证 ？查redis过期？ 返回查询结果
         * 2. 失败返回错误
         */
        Map<String, Object> resultMap = loginService.verifyToken(token);
        if ((Boolean) resultMap.get("success")){
            SysUser sysUser = (SysUser) resultMap.get("sysUser");
            //        转化为LoginUserVo对象
            LoginUserVo loginUserVo = new LoginUserVo();
            loginUserVo.setId(String.valueOf(sysUser.getId()));
            loginUserVo.setAccount(sysUser.getAccount());
            loginUserVo.setNickname(sysUser.getNickname());
            loginUserVo.setAvatar(sysUser.getAvatar());

            return AGuoResult.success(loginUserVo);
        }
        return AGuoResult.failed((Integer) resultMap.get("code"),(String) resultMap.get("msg"));
    }
    @Override
    public Boolean insertRegisterUser(LoginParam param) {
        SysUser sysUser = new SysUser();
        sysUser.setNickname(param.getNickname());
        sysUser.setAccount(param.getAccount());
        sysUser.setPassword(DigestUtils.md5Hex(param.getPassword()+ Salt.SALT.getCode()));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("http://img.aguo.pro/236f4546deae4e048a3c96bbadc755f4.png");
        sysUser.setAdmin(1);
        sysUser.setDeleted(0);
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        return sysUserMapper.insert(sysUser)==1;
    }
}
