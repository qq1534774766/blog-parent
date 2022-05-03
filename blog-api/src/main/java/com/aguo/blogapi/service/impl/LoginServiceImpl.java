package com.aguo.blogapi.service.impl;

import com.aguo.blogapi.enums.Salt;
import com.aguo.blogapi.exception.UsernameOrPasswordNotExistException;
import com.aguo.blogapi.pojo.SysUser;
import com.aguo.blogapi.service.LoginService;
import com.aguo.blogapi.service.SysUserService;
import com.aguo.blogapi.untils.JWTUtil;
import com.aguo.blogapi.vo.AGuoResult;
import com.aguo.blogapi.enums.ErrorCode;
import com.aguo.blogapi.vo.params.LoginParam;
import com.alibaba.fastjson.JSON;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: aguo
 * @DateTime: 2022/4/26 13:36
 * @Description: TODO
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public AGuoResult login(LoginParam loginParam) {
        /**
         * 登录验证步骤：
         * 1. 验证表单规则
         * 2. 使用sysUserService来验证账号和密码是否存在
         * 3. 完成登录，生成加密好的token，否则登录返回登录错误
         * 4. 将token存在到redis中：token：userId 设置过期时间 选择Set数据结构，
         * 认证时：先认证token是否合法，然后查redis中token是否存在
         *
         */
//        1.通过validation包验证了。
        loginParam.setPassword(DigestUtils.md5Hex(loginParam.getPassword() + Salt.SALT.getCode()));
        SysUser sysUser = sysUserService.findUserByUsernameAndPassword(loginParam);
//        用户名不存在
        if (sysUser == null) {
            throw new UsernameOrPasswordNotExistException();
        }
//        用户名存在
//        为该用户创建token
        String token = JWTUtil.createToken(sysUser.getId());
//        放入redis
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1L, TimeUnit.DAYS);

        return AGuoResult.success(token);
    }

    @Override
    public Map<String, Object> verifyToken(String token) {
        HashMap<String, Object> resultMap = new HashMap<>();
        //        token非空校验，
        if (StringUtils.isEmpty(token)) {
            resultMap.put("success", false);
            resultMap.put("code", ErrorCode.NO_LOGIN.getCode());
            resultMap.put("msg", ErrorCode.NO_LOGIN.getMsg());
            return resultMap;
        }
//         Jwt验证token,token是否合法，是否过期
        if (JWTUtil.checkToken(token) == null) {
            resultMap.put("success", false);
            resultMap.put("code", ErrorCode.NO_LOGIN.getCode());
            resultMap.put("msg", ErrorCode.NO_LOGIN.getMsg());
            return resultMap;
        }

//        查redis中的token，如果查出结果直接返回，不需要查数据库，牛逼
        String userJson = (String) redisTemplate.opsForValue().get("TOKEN_" + token);
        if (!StringUtils.isEmpty(userJson)) {
            resultMap.put("success", true);
            resultMap.put("code", 200);
//            resultMap.put("msg","Token合法");
            resultMap.put("sysUser", JSON.parseObject(userJson, SysUser.class));
            return resultMap;
        }
        resultMap.put("success", false);
        resultMap.put("code", ErrorCode.TOKEN_CACHE_FAILURE.getCode());
        resultMap.put("msg", ErrorCode.TOKEN_CACHE_FAILURE.getMsg());
        return resultMap;
    }

    @Override
    public AGuoResult logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return AGuoResult.success(null);
    }

    @Override
    public AGuoResult register(LoginParam param) {
//        Controller已经完成账号密码的非空验证
//        验证nickname
        if (param.getNickname() == null) {
            return AGuoResult.failed(10010, "昵称不能为空");
        }
//        验证用户名是否已存在
        boolean exists = null != sysUserService.findUserByUsernameAndPassword(new LoginParam(param.getAccount()));

        if (exists) {
            return AGuoResult.failed(ErrorCode.USER_ALREADY_EXISTS.getCode(),
                    ErrorCode.USER_ALREADY_EXISTS.getMsg());
        }
//        注册 (插入数据) ,登录
        boolean success = sysUserService.insertRegisterUser(param);
        if (success) {
            return login(param);
        }
//        一般不会运行到这里，除非数据库：连接失败，插入事务超时
        return AGuoResult.failed(405, "注册失败，请稍后再试");
    }
}
