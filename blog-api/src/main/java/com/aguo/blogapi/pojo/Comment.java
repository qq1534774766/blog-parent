package com.aguo.blogapi.pojo;

import lombok.Data;

import java.util.List;

/**
 * @Author: aguo
 * @DateTime: 2022/4/30 21:33
 * @Description: TODO
 */
@Data
public class Comment {
    private Long id;
    private String content;
    private Long createDate;
    private Long authorId;
    private Long articleId;
    private Long parentId;
    private Long toUid;
    private Integer level;
}
