package com.aguo.blogadmin.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @Author: aguo
 * @DateTime: 2022/5/3 16:24
 * @Description: TODO
 */
@Data
public class Permission {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    private String path;
    private String description;
}
