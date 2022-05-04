package com.aguo.blogapi.service;

import com.aguo.blogapi.vo.AGuoResult;
import com.aguo.blogapi.vo.params.CommentParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: aguo
 * @DateTime: 2022/4/30 21:52
 * @Description: TODO
 */
public interface CommentService {
    /**
     * 通过文章的id获取所有评论
     * @param articleId
     * @return
     */
    AGuoResult getCommentsByArticleId(Long articleId);

    /**
     * 评论功能
     * @param param
     * @return
     */
    @Transactional
    AGuoResult comment(CommentParam param);

    void deleteCommentByArticleId(Long articleId);
}
