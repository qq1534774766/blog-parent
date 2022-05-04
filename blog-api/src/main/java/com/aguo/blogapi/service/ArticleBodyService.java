package com.aguo.blogapi.service;

import com.aguo.blogapi.mapper.ArticleBodyMapper;
import com.aguo.blogapi.pojo.ArticleBody;
import com.aguo.blogapi.vo.ArticleBodyVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: aguo
 * @DateTime: 2022/4/30 10:48
 * @Description: TODO
 */
public interface ArticleBodyService{

    ArticleBodyVo findArticleBodyById(Long bodyId);

    void save(ArticleBody articleBody);

    void deleteArticleBodyByArticleId(Long articleId);
}
