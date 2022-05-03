package com.aguo.blogapi.vo.params;

import lombok.Data;

import java.util.List;

/**
 * @Author: aguo
 * @DateTime: 2022/5/1 10:36
 * @Description: TODO
 */
@Data
public class CommentParam {
    private Long articleId;
    private String content;
//    给子评论用
    private Long parent;
    private Long toUserId;
}
