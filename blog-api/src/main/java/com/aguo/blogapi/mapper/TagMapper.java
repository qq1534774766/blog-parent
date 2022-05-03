package com.aguo.blogapi.mapper;

import com.aguo.blogapi.pojo.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: aguo
 * @DateTime: 2022/4/25 0:21
 * @Description: TODO
 */
@Mapper
@Repository
public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> getTagVoByArticleId(Long id);

    List<Long> listHotTagsId(int limit);
}
