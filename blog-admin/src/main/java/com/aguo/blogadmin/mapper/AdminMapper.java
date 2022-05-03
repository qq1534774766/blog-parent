package com.aguo.blogadmin.mapper;

import com.aguo.blogadmin.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: aguo
 * @DateTime: 2022/5/3 18:04
 * @Description: TODO
 */
@Mapper
@Repository
public interface AdminMapper extends BaseMapper<Admin> {

}
