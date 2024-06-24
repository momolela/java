package com.momolela.net.http.proxy.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2024/3/26 08:04
 */
@Data
public class GroupProjectUser {

    @ExcelProperty(value = "根组", index = 0)
    private String rootGroup;

    @ExcelProperty(value = "基础分组", index = 1)
    private String baseGroup;

    @ExcelProperty(value = "项目名", index = 2)
    private String projectName;

    @ExcelProperty(value = "项目描述", index = 3)
    private String projectDesc;

    @ExcelProperty(value = "用户名", index = 4)
    private String userName;

    public GroupProjectUser() {
    }

    public GroupProjectUser(String rootGroup, String baseGroup, String projectName, String projectDesc, String userName) {
        this.rootGroup = rootGroup;
        this.baseGroup = baseGroup;
        this.projectName = projectName;
        this.projectDesc = projectDesc;
        this.userName = userName;
    }

}
