package com.aguo.blogapi.controller;

import com.aguo.blogapi.common.cache.Cache;
import com.aguo.blogapi.service.TagService;
import com.aguo.blogapi.vo.AGuoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: aguo
 * @DateTime: 2022/4/25 13:04
 * @Description: TODO
 */
@RequestMapping("/tags")
@RestController
public class TagsController {
    @Autowired
    private TagService tagService;

    @GetMapping("hot")
    @Cache(cacheId = "tags/hot", expire = 60)
    public AGuoResult hot(@RequestParam(value = "limit", required = false,defaultValue = "6") Integer limit){
        return tagService.hots(limit);
    }
    @GetMapping("")
    @Cache(cacheId = "tags/tagList", expire = 60)
    public AGuoResult tagList(){
        return tagService.tagList();
    }

    @GetMapping("detail")
    public AGuoResult tagListDetail(){
        return tagService.tagListDetail();
    }

    @GetMapping("detail/{id}")
    public AGuoResult getTagDetailById(@PathVariable("id") Long tagId){
        return tagService.tagDetailById(tagId);
    }

}
