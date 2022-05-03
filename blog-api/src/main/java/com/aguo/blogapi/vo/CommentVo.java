package com.aguo.blogapi.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: aguo
 * @DateTime: 2022/4/30 22:07
 * @Description: TODO
 */
@Data
public class CommentVo {
//    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
    private String content;
    private String createDate;
    private UserVo author;
    private UserVo toUser;
    private List<CommentVo> childrens;
    private Integer level;

}
