package com.aguo.blogapi.service;

import com.aguo.blogapi.pojo.SysUser;
import com.aguo.blogapi.vo.AGuoResult;
import com.aguo.blogapi.vo.params.LoginParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @Author: aguo
 * @DateTime: 2022/4/26 13:34
 * @Description: TODO
 */
public interface LoginService {

    /**
     * 验证账号密码，完成登录
     * @param loginParam 账号，密码
     * @return 登录结果，成功则 data：token
     */
    AGuoResult login(LoginParam loginParam);

    /**
     * 验证token合法性，合法返回包含SysUser，否则返回错误信息<BR/>
     * 合法：非空，token符合规则，redis未过期
     * @param token
     * @return map结果: <BR/>
     * Boolean success <BR/>
     * Integer code <BR/>
     * String msg 错误描述<BR/>
     * SysUser sysUser <BR/>
     */
    Map<String, Object> verifyToken(String token);

    /**
     * 退出登录
     * 直接把token信息删除
     * @param token
     * @return
     */
    AGuoResult logout(String token);

    /**
     * 验证账号密码，完成注册，并登录
     * @param param
     * @return Token
     */
    @Transactional
    AGuoResult register(LoginParam param);
}
