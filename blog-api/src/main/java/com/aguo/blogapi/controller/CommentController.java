package com.aguo.blogapi.controller;

import com.aguo.blogapi.service.CommentService;
import com.aguo.blogapi.vo.AGuoResult;
import com.aguo.blogapi.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: aguo
 * @DateTime: 2022/4/30 21:46
 * @Description: TODO
 */
@RestController
@RequestMapping("comments")
public class CommentController {
    @Autowired
    private CommentService commentService;


    /**
     * 根据文章Id获取文章的所有评论
     * @param articleId
     * @return
     */
    @RequestMapping("article/{id}")
    public AGuoResult comments(@PathVariable("id") Long articleId){
        return commentService.getCommentsByArticleId(articleId);
    }
    @RequestMapping("create/change")
    public AGuoResult comments(@RequestBody CommentParam param){
        return commentService.comment(param);
    }
}
