package com.heima.model.user.dto;

import com.heima.model.common.dtos.PageRequestDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuthDto extends PageRequestDto {
    private Integer id;
    private String msg;
    private Integer status;
}
