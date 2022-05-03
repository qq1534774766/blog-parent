package com.aguo.blogadmin.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: aguo
 * @DateTime: 2022/5/3 16:31
 * @Description: TODO
 */
@Data
public class PageResult<T> {
    private List<T> list;
    private Long total;
}
