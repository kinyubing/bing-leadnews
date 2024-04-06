package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.DeleteById;
import com.baomidou.mybatisplus.core.injector.methods.SelectList;
import com.baomidou.mybatisplus.core.injector.methods.SelectOne;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.constants.ChannelStatusConstants;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.ChannelDto;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.utils.thread.WmThreadLocalUtil;
import com.heima.wemedia.mapper.WmChannelMapper;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.service.WmChannelService;
import com.heima.wemedia.service.WmNewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class WmChannelServiceImpl extends ServiceImpl<WmChannelMapper, WmChannel> implements WmChannelService {


    /**
     * 查询所有频道
     * @return
     */
    @Override
    public ResponseResult findAll() {

        return ResponseResult.okResult(list());
    }
    /**
     * 新增频道
     * @param wmChannel
     * @return
     */
    @Override
    public ResponseResult saveChannel(WmChannel wmChannel) {
        //判断频道是否已经存在
        //1.1根据频道名称查询数据库
        LambdaQueryWrapper<WmChannel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WmChannel::getName, wmChannel.getName());
        WmChannel channel = getOne(lambdaQueryWrapper);
        if(channel!=null){
            //频道已经存在
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST,"频道已经存在");
        }
        //保存频道
        save(wmChannel);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
    /**
     * 频道分页查询
     * @param dto
     * @return
     */
    @Override
    public ResponseResult pageQuery(ChannelDto dto) {
        //1.检查参数
        dto.checkParam();

        //2.分页查询
        IPage page = new Page(dto.getPage(),dto.getSize());
        LambdaQueryWrapper<WmChannel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //按照频道名称模糊查询
        lambdaQueryWrapper.like(dto.getName()!=null,WmChannel::getName,dto.getName());
        //按照状态进行精确查询
        lambdaQueryWrapper.eq(dto.getStatus()!=null,WmChannel::getStatus,dto.getStatus());
        //按照创建时间倒序排序
        lambdaQueryWrapper.orderByDesc(WmChannel::getCreatedTime);
        //分页查询
        page = page(page,lambdaQueryWrapper);

        //3.结果返回
        ResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(),(int)page.getTotal());
        responseResult.setData(page.getRecords());
        return responseResult;
    }
    /**
     * 根据id删除频道
     * @param id
     * @return
     */
    @Override
    public ResponseResult delById(Integer id) {
        //查询该频道的状态是否为禁用，禁用的频道才能进行删除
        WmChannel wmChannel = getById(id);
        if(wmChannel!=null){
            if((wmChannel.getStatus()).equals(ChannelStatusConstants.DISABLE)){
              //删除频道
                removeById(id);
                return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
            }else{
                //频道未被禁用不能被删除
                return ResponseResult.errorResult(AppHttpCodeEnum.CHANNEL_REFERENCED_NOT_DELETED);
            }
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
    /**
     * 根据id获取频道
     * @param id
     * @return
     */
    @Override
    public ResponseResult getDataById(Integer id) {
        WmChannel wmChannel = getById(id);
        return ResponseResult.okResult(wmChannel);
    }
    @Autowired
    private WmNewsService wmNewsService;
    /**
     * 修改频道
     * @param wmChannel
     * @return
     */
    @Override
    public ResponseResult updateData(WmChannel wmChannel) {
        LambdaQueryWrapper<WmNews> queryWrapper=new LambdaQueryWrapper<>();
        //判断要修改的频道是否要将频道状态改为禁用
        if(wmChannel.getStatus().equals(ChannelStatusConstants.DISABLE)){
            //查询该频道是否被引用了
            Integer wmChannelId = wmChannel.getId();
            queryWrapper.eq(WmNews::getChannelId,wmChannelId);
            WmNews wmNews = wmNewsService.getOne(queryWrapper);
            if(wmNews!=null){
                //该频道被引用了,不能进行禁用
                return ResponseResult.errorResult(AppHttpCodeEnum.CHANNEL_STATUS_UPDATE_FAIL);
            }
        }updateById(wmChannel);//否则更新数据
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

    }
}