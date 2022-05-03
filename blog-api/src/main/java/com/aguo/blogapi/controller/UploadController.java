package com.aguo.blogapi.controller;

import com.aguo.blogapi.enums.ErrorCode;
import com.aguo.blogapi.untils.QiniuUtil;
import com.aguo.blogapi.vo.AGuoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @Author: aguo
 * @DateTime: 2022/5/1 20:22
 * @Description: TODO
 */
@RestController
@RequestMapping("upload")
public class UploadController {
    @Autowired
    private QiniuUtil qiniuUtil;
    @PostMapping
    public AGuoResult upload(@RequestParam("image") MultipartFile file){
        // 获得原始文件名 aa.png  → aa.png
        String originalFilename = file.getOriginalFilename();
        // 生成新的文件名，避免重复
        // UUID.png
        StringBuilder newFileName =  new StringBuilder(UUID.randomUUID().toString().replaceAll("-", ""));
        newFileName.append(".").append(StringUtils.substringAfterLast(originalFilename, "."));
        // 文件上传到哪里呢？？？
        boolean upload = qiniuUtil.upload(file,newFileName.toString());
        if (upload){
            return AGuoResult.success(qiniuUtil.getUrl()+newFileName.toString());
        }
        return AGuoResult.failed(ErrorCode.FILE_UPLOAD_ERROR.getCode(), ErrorCode.FILE_UPLOAD_ERROR.getMsg());
    }
}
