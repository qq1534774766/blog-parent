package com.aguo.blogapi.vo.params;

import com.aguo.blogapi.vo.CategoryVo;
import com.aguo.blogapi.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @Author: aguo
 * @DateTime: 2022/5/1 14:31
 * @Description: TODO
 */
@Data
public class ArticleParam {
    private Long id;
    private ArticleBodyParam body;
    private CategoryVo category;
    private String summary;
    private List<TagVo> tags;
    private String title;

}
