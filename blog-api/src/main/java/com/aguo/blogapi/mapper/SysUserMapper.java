package com.aguo.blogapi.mapper;

import com.aguo.blogapi.pojo.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: aguo
 * @DateTime: 2022/4/25 1:13
 * @Description: TODO
 */
@Mapper
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
}
