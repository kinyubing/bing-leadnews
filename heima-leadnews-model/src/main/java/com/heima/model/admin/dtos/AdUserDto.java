package com.heima.model.admin.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户登录接收dto")
public class AdUserDto {
    @ApiModelProperty(value = "用户名",required = true)
    private String name;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
}
