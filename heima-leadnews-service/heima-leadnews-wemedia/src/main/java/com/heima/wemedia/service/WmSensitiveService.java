package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.ChannelDto;
import com.heima.model.wemedia.dtos.SensitiveDto;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.model.wemedia.pojos.WmSensitive;

public interface WmSensitiveService extends IService<WmSensitive> {
    /**
     * 新增敏感词
     * @param wmSensitive
     * @return
     */
    ResponseResult saveData(WmSensitive wmSensitive);
    /**
     * 分页查询
     * @param sensitiveDto
     * @return
     */
    ResponseResult pageQuery(SensitiveDto sensitiveDto);
    /**
     * 根据id删除敏感词
     * @param id
     * @return
     */
    ResponseResult deleteDataById(Integer id);
    /**
     * 修改数据
     * @param wmSensitive
     * @return
     */
    ResponseResult updateData(WmSensitive wmSensitive);
}