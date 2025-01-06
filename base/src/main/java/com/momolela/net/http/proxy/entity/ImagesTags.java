package com.momolela.net.http.proxy.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2025/1/6 11:13
 */
@Data
public class ImagesTags {
    @ExcelProperty(value = "imageName", index = 0)
    private String imageName;

    @ExcelProperty(value = "imageTag", index = 1)
    private String imageTag;

    public ImagesTags() {
    }

    public ImagesTags(String imageName, String imageTag) {
        this.imageName = imageName;
        this.imageTag = imageTag;
    }
}
