package com.aguo.blogapi.pojo;

import lombok.Data;

/**
 * @Author: aguo
 * @DateTime: 2022/4/30 10:11
 * @Description: TODO
 */
@Data
public class Category {
    private Long id;
    private String avatar;
    private String categoryName;
    private String description;
}
