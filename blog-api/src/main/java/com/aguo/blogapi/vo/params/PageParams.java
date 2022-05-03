package com.aguo.blogapi.vo.params;

import lombok.Data;

/**
 * @Author: aguo
 * @DateTime: 2022/4/24 20:14
 * @Description: TODO
 */
@Data
public class PageParams {
    private Integer page=1;
    private Integer pageSize=10;
    private Long categoryId;
    private Long tagId;
    private String year;
    private String month;

    public String getMonth() {
        if (month!=null&& !month.contains("0")){
            return "0"+month;
        }
        return month;
    }
}
