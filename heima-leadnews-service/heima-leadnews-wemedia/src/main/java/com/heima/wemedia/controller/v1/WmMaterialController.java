package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.wemedia.service.WmMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/material")
public class WmMaterialController {
    @Autowired
    private WmMaterialService wmMaterialService;


    @PostMapping("/upload_picture")
    public ResponseResult uploadPicture(MultipartFile multipartFile){

        return wmMaterialService.uploadPicture(multipartFile);
    }
    @PostMapping("/list")
    public ResponseResult findList(@RequestBody WmMaterialDto dto){

        return wmMaterialService.findList(dto);
    }

    /**
     * 根据素材id删除素材图片
     * @param id
     * @return
     */
    @GetMapping("/del_picture/{id}")
    public ResponseResult delPictureById(@PathVariable Integer id){

        return wmMaterialService. delPictureById(id);
    }
    /**
     * 根据素材id收藏素材图片
     * @param id
     * @return
     */
    @GetMapping("/collect/{id}")
    public ResponseResult collectPicture(@PathVariable Integer id){

        return wmMaterialService. collectPicture(id);
    }
    /**
     * 根据素材id取消收藏素材图片
     * @param id
     * @return
     */
    @GetMapping("/cancel_collect/{id}")
    public ResponseResult cancelCollectPicture(@PathVariable Integer id){

        return wmMaterialService. cancelCollectPicture(id);
    }


}