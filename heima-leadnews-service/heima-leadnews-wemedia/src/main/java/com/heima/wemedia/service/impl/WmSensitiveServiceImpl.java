package com.heima.wemedia.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.SensitiveDto;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.model.wemedia.pojos.WmSensitive;
import com.heima.wemedia.mapper.WmSensitiveMapper;
import com.heima.wemedia.service.WmSensitiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@Slf4j
public class WmSensitiveServiceImpl extends ServiceImpl<WmSensitiveMapper, WmSensitive> implements WmSensitiveService {
    /**
     * 新增敏感词
     * @param wmSensitive
     * @return
     */
    @Override
    public ResponseResult saveData(WmSensitive wmSensitive) {
        //查询该敏感词是否已经存在
        LambdaQueryWrapper<WmSensitive> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(WmSensitive::getSensitives,wmSensitive.getSensitives());
        WmSensitive wmSensitive1 = getOne(queryWrapper);
        if(wmSensitive1!=null){
            //敏感词已经存在
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST,"敏感词已经存在");
        }
        //保存数据
        wmSensitive.setCreatedTime(new Date());
        save(wmSensitive);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
    /**
     * 分页查询
     * @param dto
     * @return
     */
    @Override
    public ResponseResult pageQuery(SensitiveDto dto) {
        //1.检查参数
        dto.checkParam();

        //2.分页查询
        IPage page = new Page(dto.getPage(),dto.getSize());
        LambdaQueryWrapper<WmSensitive> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //按照频道名称模糊查询
        lambdaQueryWrapper.like(dto.getName()!=null,WmSensitive::getSensitives,dto.getName());
        //按照创建时间倒序排序
        lambdaQueryWrapper.orderByDesc(WmSensitive::getCreatedTime);
        //分页查询
        page = page(page,lambdaQueryWrapper);

        //3.结果返回
        ResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(),(int)page.getTotal());
        responseResult.setData(page.getRecords());
        return responseResult;
    }
    /**
     * 根据id删除敏感词
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteDataById(Integer id) {
        removeById(id);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
    /**
     * 修改数据
     * @param wmSensitive
     * @return
     */
    @Override
    public ResponseResult updateData(WmSensitive wmSensitive) {
        updateById(wmSensitive);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

}