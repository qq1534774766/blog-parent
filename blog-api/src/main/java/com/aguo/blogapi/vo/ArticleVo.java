package com.aguo.blogapi.vo;

import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {

//    解决BeanUntil.copy方法导致的int→string精度丢失问题
//    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

    private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo category;

}
