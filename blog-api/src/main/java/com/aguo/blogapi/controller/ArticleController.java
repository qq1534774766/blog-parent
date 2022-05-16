package com.aguo.blogapi.controller;

import com.aguo.blogapi.common.aop.DoubleDeleteDelay;
import com.aguo.blogapi.common.aop.LogAnnotation;
import com.aguo.blogapi.common.cache.Cache;
import com.aguo.blogapi.service.ArticleService;
import com.aguo.blogapi.vo.AGuoResult;
import com.aguo.blogapi.vo.params.ArticleParam;
import com.aguo.blogapi.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: aguo
 * @DateTime: 2022/4/24 20:12
 * @Description: TODO
 */
@RestController()
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;


    /**
     * 博客首页的文章简略展示，默认10条。
     * RequestBody作用：前端发送json字符串时，可以转为java对象，
     * 如果没加，那么springmvc就视为全部视为一个字符串，需要接收后手动映射。
     * @param pageParams 分  页对象，有起始条数，页长度
     * @return 首页展示的结果,由业务层决定返回的内容以及状态。
     *
     * //LogAnnotation加上此注解，表示要记录这个方法的日志，aop方式
     */
    @PostMapping
    @LogAnnotation(module = "文章",operation = "获取所有文章")
    @Cache(cacheId = "articles/listArticle", expire = 60*60)
    public AGuoResult listArticle(@RequestBody PageParams pageParams){
        return articleService.listArticle(pageParams);
    }

    /**
     * 热门文章卡片
     * @param limit
     * @return
     */
    @PostMapping("hot")
    @Cache(cacheId = "articles/hotArticles", expire = 60*60)
    public AGuoResult hotArticles(@RequestParam(value = "limit", required = false,defaultValue = "5") Integer limit){
        return articleService.hotArticles(limit);
    }


    /**
     * 最新文章卡片
     * @param limit
     * @return
     */
    @PostMapping("new")
    @Cache(cacheId = "articles/newArticles", expire = 60*60)
    public AGuoResult newArticles(@RequestParam(value = "limit", required = false,defaultValue = "5") Integer limit){
        return articleService.newArticles(limit);
    }

    /**
     * 文章归档卡片
     * Archives:归档
     * @return
     */
    @PostMapping("listArchives")
    @Cache(cacheId = "articles/listArchives", expire = 60*60)
    public AGuoResult listArchives(){
        return articleService.listArchives();
    }

    /**
     * 通过文章id获取文章的详细内容
     * @param articleId
     * @return
     */
    @PostMapping("view/{id}")
    public AGuoResult findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }
    /**
     * 通过文章id获取文章的详细内容
     * @param articleId
     * @return
     */
    @PostMapping("{id}")
    public AGuoResult findArticleByIdToWrite(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    @PostMapping("publish")
    @DoubleDeleteDelay(cacheId = {"articles/listArticle","articles/newArticles","articles/listArchives"}
    ,simpleClassName = {"ArticleController","ArticleController","ArticleController"}
    ,methodName = {"listArticle","newArticles","listArchives"})
    public AGuoResult publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }

    /**
     * 删除文章
     * @param articleId
     * @return
     */
    @RequestMapping("delete/{id}")
    @DoubleDeleteDelay(cacheId = {"articles/listArticle","articles/newArticles","articles/listArchives"}
            ,simpleClassName = {"ArticleController","ArticleController","ArticleController"}
            ,methodName = {"listArticle","newArticles","listArchives"})
    public AGuoResult deleteArticle(@PathVariable("id") Long articleId){
        return articleService.deleteArticleById(articleId);
    }

}
