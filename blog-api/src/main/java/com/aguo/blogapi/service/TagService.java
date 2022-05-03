package com.aguo.blogapi.service;

import com.aguo.blogapi.vo.AGuoResult;
import com.aguo.blogapi.vo.TagVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: aguo
 * @DateTime: 2022/4/25 0:16
 * @Description: TODO
 */
public interface TagService {
    List<TagVo> getTagVoByArticleId(Long id);

    /**
     * 最热门标签
     * @param limit
     * @return
     */
    AGuoResult hots(int limit);

    /**
     * 查询所有标签
     * @return
     */
    AGuoResult tagList();

    /**
     *
     * @return
     */
    AGuoResult tagListDetail();

    /**
     * 通过标签的Id获取标签的信息
     * @param tagId
     * @return
     */
    AGuoResult tagDetailById(Long tagId);
}
