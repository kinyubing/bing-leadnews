package com.heima.wemedia.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsDto2;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;
import net.sourceforge.tess4j.TesseractException;

import java.io.IOException;

public interface WmNewsService extends IService<WmNews> {

    /**
     * 查询文章
     * @param dto
     * @return
     */
    public ResponseResult findAll(WmNewsPageReqDto dto);
    /**
     *  发布文章或保存草稿
     * @param dto
     * @return
     */
    public ResponseResult submitNews(WmNewsDto dto) throws TesseractException, IOException;
    /**
     * 根据文章id查询详情
     * @param id
     * @return
     */
    ResponseResult<WmNews> getNewById(Integer id);
    /**
     * 根据文章id删除文章
     * @param id
     * @return
     */
    ResponseResult<WmNews> delNewById(Integer id);
    /**
     * 文章的上下架
     * @param dto
     * @return
     */
    public ResponseResult downOrUp(WmNewsDto dto);
}