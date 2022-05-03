package com.aguo.blogapi.pojo;

import lombok.Data;

/**
 * @Author: aguo
 * @DateTime: 2022/4/30 10:09
 * @Description: TODO
 */
@Data
public class ArticleBody {
    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;

}
