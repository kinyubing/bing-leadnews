package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsDto2;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.wemedia.service.WmNewsService;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/news")
public class WmNewsController {
    @Autowired
    private WmNewsService wmNewsService;


    @PostMapping("/list")
    public ResponseResult findAll(@RequestBody WmNewsPageReqDto dto){

        return  wmNewsService.findAll(dto);
    }
    @PostMapping("/submit")
    public ResponseResult submitNews(@RequestBody WmNewsDto dto) throws TesseractException, IOException {

        return  wmNewsService.submitNews(dto);
    }

    /**
     * 根据文章id查询详情
     * @param id
     * @return
     */
    @GetMapping("/one/{id}")
    public ResponseResult<WmNews> getNewById(@PathVariable Integer id){

        return  wmNewsService.getNewById(id);
    }
    /**
     * 根据文章id删除文章
     * @param id
     * @return
     */
    @GetMapping("/del_news/{id}")
    public ResponseResult<WmNews> delNewById(@PathVariable Integer id){

        return  wmNewsService.delNewById(id);
    }
    /**
     * 对文章进行上下架
     * @param wmNewsDto
     * @return
     */
    @PostMapping("/down_or_up")
    public ResponseResult<WmNews> down_or_up(@RequestBody WmNewsDto wmNewsDto){
        return wmNewsService.downOrUp(wmNewsDto);
    }



}