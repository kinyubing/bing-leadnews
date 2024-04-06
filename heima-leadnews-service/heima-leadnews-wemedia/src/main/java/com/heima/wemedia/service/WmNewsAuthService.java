package com.heima.wemedia.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.NewsAuthDto;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;
import net.sourceforge.tess4j.TesseractException;

import java.io.IOException;

public interface WmNewsAuthService extends IService<WmNews> {
    /**
     * 管理端人工审核列表查询
     * @param dto
     * @return
     */
    ResponseResult findAll(NewsAuthDto dto);
    /**
     * 管理端查看文章详情
     * @param id
     * @return
     */
    ResponseResult getDetail(Integer id);
    /**
     * 管理端人工审核失败
     * @param dto
     * @return
     */
    ResponseResult authFail(NewsAuthDto dto);
    /**
     * 管理端人工审核成功
     * @param dto
     * @return
     */
    ResponseResult authPass(NewsAuthDto dto);
}