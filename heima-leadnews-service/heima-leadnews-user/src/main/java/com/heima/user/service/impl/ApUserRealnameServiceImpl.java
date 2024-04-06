package com.heima.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.admin.pojos.ApUserRealname;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.dto.AuthDto;
import com.heima.user.mapper.ApUserRealnameMapper;
import com.heima.user.service.ApUserRealnameService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;



@Service
public class ApUserRealnameServiceImpl extends ServiceImpl<ApUserRealnameMapper, ApUserRealname> implements ApUserRealnameService {
    /**
     * 分页查询认证列表
     * @param dto
     * @return
     */
    @Override
    public ResponseResult pageQuery(AuthDto dto) {
        //1.检查参数
        dto.checkParam();

        //2.分页查询
        IPage page = new Page(dto.getPage(),dto.getSize());
        LambdaQueryWrapper<ApUserRealname> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //按照审核状态进行精确查询
        lambdaQueryWrapper.eq(dto.getStatus()!=null,ApUserRealname::getStatus,dto.getStatus());
        //分页查询
        page = page(page,lambdaQueryWrapper);

        //3.结果返回
        ResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(),(int)page.getTotal());
        responseResult.setData(page.getRecords());
        return responseResult;
    }
    /**
     * 审核失败
     * @param authDto
     * @return
     */
    @Override
    public ResponseResult authFail(AuthDto authDto)  {
        ApUserRealname apUserRealname = new ApUserRealname();
        //将用户的状态改为审核失败
        apUserRealname.setId(authDto.getId());
        apUserRealname.setReason(authDto.getMsg());
        apUserRealname.setStatus((short) 2);
        updateById(apUserRealname);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
    /**
     * 审核通过
     * @param authDto
     * @return
     */
    @Override
    public ResponseResult authPass(AuthDto authDto) {
        ApUserRealname apUserRealname = new ApUserRealname();
        apUserRealname.setId(authDto.getId());
        apUserRealname.setStatus((short) 9);
        updateById(apUserRealname);
        //TODO 审核通过同时创建该用户的自媒体人
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}