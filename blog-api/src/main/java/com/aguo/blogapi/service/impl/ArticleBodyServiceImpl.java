package com.aguo.blogapi.service.impl;

import com.aguo.blogapi.mapper.ArticleBodyMapper;
import com.aguo.blogapi.pojo.ArticleBody;
import com.aguo.blogapi.service.ArticleBodyService;
import com.aguo.blogapi.vo.ArticleBodyVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: aguo
 * @DateTime: 2022/4/30 10:49
 * @Description: TODO
 */
@Service
public class ArticleBodyServiceImpl implements ArticleBodyService {
    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Override
    public ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        BeanUtils.copyProperties(articleBody, articleBodyVo);
        return articleBodyVo;
    }

    @Override
    public void save(ArticleBody articleBody) {
        articleBodyMapper.insert(articleBody);
    }
}
