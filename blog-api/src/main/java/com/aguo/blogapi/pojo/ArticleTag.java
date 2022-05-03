package com.aguo.blogapi.pojo;

import lombok.Data;

/**
 * @Author: aguo
 * @DateTime: 2022/5/1 14:46
 * @Description: TODO
 */
@Data
public class ArticleTag {
    private Long id;
    private Long articleId;
    private Long tagId;
}
