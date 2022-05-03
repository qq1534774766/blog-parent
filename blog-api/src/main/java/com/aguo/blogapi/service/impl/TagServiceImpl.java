package com.aguo.blogapi.service.impl;

import com.aguo.blogapi.mapper.TagMapper;
import com.aguo.blogapi.pojo.Tag;
import com.aguo.blogapi.service.TagService;
import com.aguo.blogapi.vo.AGuoResult;
import com.aguo.blogapi.vo.TagVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: aguo
 * @DateTime: 2022/4/25 0:20
 * @Description: TODO
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> getTagVoByArticleId(Long id) {
        /**
         * 因为存在article与tag的关系表，多对多关系，需要连表查询
         */
        List<Tag> tagList = tagMapper.getTagVoByArticleId(id);
        return copyList(tagList);
    }

    @Override
    public AGuoResult hots(int limit) {
        List<Long> hotTagsIdList= tagMapper.listHotTagsId(limit);
        /**
         * 细节 可能id为空的判断
         */
        if (CollectionUtils.isEmpty(hotTagsIdList)){
            return AGuoResult.failed(404,"没有任何一个标签");
        }
        List<Tag> tagList = tagMapper.selectBatchIds(hotTagsIdList);
        //不能这样玩，应该调用success方法！
        //   return new AGuoResult(true,200,"success",copyList(tagList));

        //下面才是对的操作！
        return AGuoResult.success(copyList(tagList));
    }

    @Override
    public AGuoResult tagList() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getTagName);
        List<Tag> tags = tagMapper.selectList(queryWrapper);
        return AGuoResult.success(copyList(tags));
    }

    @Override
    public AGuoResult tagListDetail() {
        List<Tag> tags = tagMapper.selectList(null);
        return AGuoResult.success(copyList(tags));
    }

    @Override
    public AGuoResult tagDetailById(Long tagId) {
        Tag tag = tagMapper.selectById(tagId);
        return AGuoResult.success(copy(tag));
    }

    private List<TagVo> copyList(List<Tag> tagList) {
        ArrayList<TagVo> tagVoList = new ArrayList<>();
        for (Tag tagVar : tagList) {
            tagVoList.add(copy(tagVar));
        }
        return tagVoList;
    }
    private TagVo copy(Tag tagVar) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tagVar, tagVo);
        tagVo.setId(String.valueOf(tagVar.getId()));
        return tagVo;
    }

}
