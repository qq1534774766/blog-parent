package com.aguo.blogadmin.vo.param;

import lombok.Data;

/**
 * @Author: aguo
 * @DateTime: 2022/5/3 16:16
 * @Description: TODO
 */
@Data
public class PageParam {
    private Integer currentPage;
    private Integer pageSize;
    private String queryString;
}
