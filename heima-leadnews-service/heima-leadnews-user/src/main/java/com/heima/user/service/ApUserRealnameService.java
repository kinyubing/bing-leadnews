package com.heima.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.admin.pojos.ApUserRealname;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dto.AuthDto;
import com.heima.model.user.dto.LoginDto;
import com.heima.model.user.pojos.ApUser;

import java.lang.reflect.InvocationTargetException;

public interface ApUserRealnameService extends IService<ApUserRealname>{

    /**
     * 分页查询认证列表
     * @param authDto
     * @return
     */
    ResponseResult pageQuery(AuthDto authDto);
    /**
     * 审核失败
     * @param authDto
     * @return
     */
    ResponseResult authFail(AuthDto authDto);
    /**
     * 审核通过
     * @param authDto
     * @return
     */
    ResponseResult authPass(AuthDto authDto);
}