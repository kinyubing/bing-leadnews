package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.NewsAuthDto;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.wemedia.service.WmNewsAuthService;
import com.heima.wemedia.service.WmNewsService;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/news")
public class WmNewsAuthController {
    @Autowired
    private WmNewsAuthService wmNewsAuthService;

    /**
     * 管理端人工审核列表查询
     * @param dto
     * @return
     */
    @PostMapping("/list_vo")
    public ResponseResult findAll(@RequestBody NewsAuthDto dto){

        return  wmNewsAuthService.findAll(dto);
    }
    /**
     * 管理端查看文章详情
     * @param id
     * @return
     */
    @GetMapping("/one_vo/{id}")
    public ResponseResult getDetail(@PathVariable Integer id){

        return  wmNewsAuthService.getDetail(id);
    }
    /**
     * 管理端人工审核失败
     * @param dto
     * @return
     */
    @PostMapping("/auth_fail")
    public ResponseResult authFail(@RequestBody NewsAuthDto dto){

        return  wmNewsAuthService.authFail(dto);
    }
    /**
     * 管理端人工审核成功
     * @param dto
     * @return
     */
    @PostMapping("/auth_pass")
    public ResponseResult authPass(@RequestBody NewsAuthDto dto){

        return  wmNewsAuthService.authPass(dto);
    }

}