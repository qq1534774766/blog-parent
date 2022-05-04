package com.aguo.blogapi.service.impl;

import com.aguo.blogapi.mapper.CommentMapper;
import com.aguo.blogapi.pojo.Comment;
import com.aguo.blogapi.pojo.SysUser;
import com.aguo.blogapi.service.CommentService;
import com.aguo.blogapi.service.SysUserService;
import com.aguo.blogapi.untils.UserThreadLocal;
import com.aguo.blogapi.vo.AGuoResult;
import com.aguo.blogapi.vo.CommentVo;
import com.aguo.blogapi.vo.UserVo;
import com.aguo.blogapi.vo.params.CommentParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: aguo
 * @DateTime: 2022/4/30 21:52
 * @Description: TODO
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public AGuoResult getCommentsByArticleId(Long articleId) {
        /**
         * 1. 通过文章id获取所有level=1的评论
         * 2. 转化为commentVo对象
         */
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, articleId);
        queryWrapper.eq(Comment::getLevel, 1);
        queryWrapper.orderByDesc(Comment::getId);
        List<Comment> commentList = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(commentList);
        return AGuoResult.success(commentVoList);
    }
    /**
     * 通过评论的父评论获取所有子评论
     *
     * @param id
     * @return
     */
    private List<CommentVo> listChildrensByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, id);
        queryWrapper.eq(Comment::getLevel, 2);
        return copyList(commentMapper.selectList(queryWrapper));
    }

    @Override
    public AGuoResult comment(CommentParam param) {
//        低级方式！
//        SysUser userById = sysUserService.getUserById(param.getArticleId());
//        高级方式
        SysUser sysUser = UserThreadLocal.get();

        Comment comment = new Comment();
        comment.setArticleId(param.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(param.getContent());
        comment.setCreateDate(System.currentTimeMillis());
//        一级评论
        Long parent = param.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        } else {
            comment.setLevel(2);
        }
//        二级
        comment.setParentId(parent != null ? parent : 0);
        Long toUserid = param.getToUserId();
        comment.setToUid(toUserid != null ? toUserid : 0);
        commentMapper.insert(comment);
        return AGuoResult.success(null);
    }

    @Override
    public void deleteCommentByArticleId(Long articleId) {
        commentMapper.deleteById(articleId);
    }

    private List<CommentVo> copyList(List<Comment> commentList) {
        ArrayList<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        BeanUtils.copyProperties(comment, commentVo);
        commentVo.setId(String.valueOf(comment.getId()));
        //作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = sysUserService.getUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //子评论
        if (1 == comment.getLevel()) {
            Long id = comment.getId();
            List<CommentVo> commentVoList = listChildrensByParentId(id);
            commentVo.setChildrens(commentVoList);
        }
        //to User 给谁评论
        if (1 < comment.getLevel()) {
            Long toUser = comment.getToUid();
            UserVo toUserVo = sysUserService.getUserVoById(toUser);
            commentVo.setToUser(toUserVo);
        }

        return commentVo;
    }


}
