package com.aguo.blogapi.mapper;

import com.aguo.blogapi.pojo.Comment;
import com.aguo.blogapi.vo.CommentVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: aguo
 * @DateTime: 2022/4/30 22:05
 * @Description: TODO
 */
@Mapper
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

}
