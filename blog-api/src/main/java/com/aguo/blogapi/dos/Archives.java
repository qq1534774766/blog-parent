package com.aguo.blogapi.dos;

import lombok.Data;

/**
 * @Author: aguo
 * @DateTime: 2022/4/26 10:25
 * @Description: TODO
 */
@Data
public class Archives {
    private Integer year;
    private Integer month;
    private Long count;
}
