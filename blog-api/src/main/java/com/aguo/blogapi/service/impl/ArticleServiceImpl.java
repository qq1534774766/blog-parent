package com.aguo.blogapi.service.impl;


import com.aguo.blogapi.dos.Archives;
import com.aguo.blogapi.mapper.ArticleMapper;
import com.aguo.blogapi.mapper.ArticleTagMapper;
import com.aguo.blogapi.pojo.Article;
import com.aguo.blogapi.pojo.ArticleBody;
import com.aguo.blogapi.pojo.ArticleTag;
import com.aguo.blogapi.pojo.SysUser;
import com.aguo.blogapi.service.*;
import com.aguo.blogapi.untils.UserThreadLocal;
import com.aguo.blogapi.vo.AGuoResult;
import com.aguo.blogapi.vo.ArticleVo;
import com.aguo.blogapi.vo.TagVo;
import com.aguo.blogapi.vo.params.ArticleParam;
import com.aguo.blogapi.vo.params.PageParams;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: aguo
 * @DateTime: 2022/4/24 20:50
 * @Description: TODO
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ArticleBodyService articleBodyService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public AGuoResult listArticle(PageParams pageParams){
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articlePage = articleMapper.listArticle(page,pageParams);
        List<Article> records = page.getRecords();
        return AGuoResult.success(copyList(records,true,true));
    }
    /*@Override
    public AGuoResult listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
        //where start
//        分类ID不为空时，用于通过选择指定类别来显示指定的文章
        Long categoryId = pageParams.getCategoryId();
        articleQueryWrapper.eq( categoryId!= null&&categoryId!=0, Article::getCategoryId, categoryId);
//        标签ID不为空时，用于通过选择指定的标签来显示指定的文章
        List<Long> articleIdList = new ArrayList<>();
        if (pageParams.getTagId() != null&&pageParams.getTagId()!=0) {
            LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ArticleTag::getTagId, pageParams.getTagId());
            // 此时的articleTags 包含有tagID，和所有文章的ID
            List<ArticleTag> articleTags = articleTagMapper.selectList(queryWrapper);
            // 将articleTags中的文章Id提取到一个新集合当中
            articleTags.forEach(articleTag -> {
                articleIdList.add(articleTag.getArticleId());
            });
            // id in (1,2,3)
            if (articleIdList.size() > 0)
                articleQueryWrapper.in(Article::getId, articleIdList);
        }
        //where end
        articleQueryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, articleQueryWrapper);
        //得到结果的所有记录，但是是Article的数据库对象
        List<Article> article = articlePage.getRecords();
        //通过copyList转化为Vo对象
        List<ArticleVo> articleVoList = copyList(article, true, true);
        return AGuoResult.success(articleVoList);
    }*/

    @Override
    public AGuoResult hotArticles(int limit) {
        LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
        articleQueryWrapper.select(Article::getId, Article::getTitle);
        articleQueryWrapper.orderByDesc(Article::getViewCounts);
        //在SQL追加语句
        articleQueryWrapper.last("limit " + limit);
        List<Article> articleList = articleMapper.selectList(articleQueryWrapper);
        return AGuoResult.success(copyList(articleList, true, true));
    }

    @Override
    public AGuoResult newArticles(int limit) {
        LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
        articleQueryWrapper.select(Article::getId, Article::getTitle);
        articleQueryWrapper.orderByDesc(Article::getCreateDate);
        //在SQL追加语句
        articleQueryWrapper.last("limit " + limit);
        List<Article> articleList = articleMapper.selectList(articleQueryWrapper);
        return AGuoResult.success(copyList(articleList, true, true));
    }

    @Override
    public AGuoResult listArchives() {
        List<Archives> archiveList = articleMapper.listArchives();
        return AGuoResult.success(archiveList);
    }

    @Override
    public AGuoResult findArticleById(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article, true, true, true, true);
//        使用线程池异步技术，对文章阅读次数进行加1，实现既不影响该业务的速度，又能满足需求
        threadService.increaseArticleViewCount(articleMapper, article);
        return AGuoResult.success(articleVo);
    }

    @Override
    public AGuoResult publish(ArticleParam articleParam) {
        //获取当前登录用户
        SysUser sysUser = UserThreadLocal.get();
        /**
         * 1.发布文章，构建article对象
         * 2. 插入article与tag映射表
         * 3. 插入articlebody
         */
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setWeight(Article.Article_Common);
        article.setCommentCounts(0);
        article.setViewCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setCategoryId(Long.valueOf(articleParam.getCategory().getId()));
        //插入之后会生成文章id会注入到article当中
        articleMapper.insert(article);
        List<TagVo> tags = articleParam.getTags();
        Long articleId = article.getId();
        if (null != tags) {
            for (TagVo tag : tags) {
                Long id = Long.valueOf(tag.getId());
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(id);
                articleTagMapper.insert(articleTag);
            }
        }
        //插入body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(articleId);
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyService.save(articleBody);
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", article.getId().toString());
        return AGuoResult.success(map);
    }


    private List<ArticleVo> copyList(List<Article> article, boolean isTag, boolean isAuthor) {
        return copyList(article, isTag, isAuthor, false, false);
    }

    private List<ArticleVo> copyList(List<Article> article, boolean isTag, boolean isAuthor, boolean isBoby, boolean isCategory) {
        List<ArticleVo> list = new ArrayList<>();
        for (Article articlevar : article) {
            list.add(copy(articlevar, isTag, isAuthor, isBoby, isCategory));
        }
        return list;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBoby, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setId(String.valueOf(article.getId()));
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //不是所有文章都有接口都需要标签和作者
        if (isTag) {
            Long id = article.getId();
            articleVo.setTags(tagService.getTagVoByArticleId(id));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.getUserById(authorId).getNickname());
        }
        if (isBoby) {
            Long BodyId = article.getBodyId();
            articleVo.setBody(articleBodyService.findArticleBodyById(BodyId));
        }
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.getCategoryVo(categoryId));
        }

        return articleVo;
    }
}
