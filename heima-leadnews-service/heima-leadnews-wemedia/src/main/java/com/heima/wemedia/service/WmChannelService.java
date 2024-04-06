package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.ChannelDto;
import com.heima.model.wemedia.pojos.WmChannel;

public interface WmChannelService extends IService<WmChannel> {

    /**
     * 查询所有频道
     * @return
     */
    public ResponseResult findAll();

    /**
     * 新增频道
     * @param wmChannel
     * @return
     */
    ResponseResult saveChannel(WmChannel wmChannel);
    /**
     * 频道分页查询
     * @param channelDto
     * @return
     */
    ResponseResult pageQuery(ChannelDto channelDto);
    /**
     * 根据id删除频道
     * @param id
     * @return
     */
    ResponseResult delById(Integer id);
    /**
     * 根据id获取频道
     * @param id
     * @return
     */
    ResponseResult getDataById(Integer id);
    /**
     * 修改频道
     * @param wmChannel
     * @return
     */
    ResponseResult updateData(WmChannel wmChannel);
}