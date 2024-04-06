package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.ChannelDto;
import com.heima.model.wemedia.dtos.SensitiveDto;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.model.wemedia.pojos.WmSensitive;
import com.heima.wemedia.service.WmChannelService;
import com.heima.wemedia.service.WmSensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sensitive")
public class WmSensitiveController {
    @Autowired
    private WmSensitiveService wmSensitiveService;

  /**
   * 新增敏感词
   * @param wmSensitive
   * @return
   */
   @PostMapping("/save")
  public ResponseResult save(@RequestBody WmSensitive wmSensitive){
     return wmSensitiveService.saveData(wmSensitive);
  }
    /**
     * 分页查询
     * @param sensitiveDto
     * @return
     */
    @PostMapping("/list")
    public ResponseResult pageQuery(@RequestBody SensitiveDto sensitiveDto){
        return wmSensitiveService.pageQuery(sensitiveDto);
    }
    /**
     * 根据id删除敏感词
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public ResponseResult deleteDataById(@PathVariable Integer id){
        return wmSensitiveService.deleteDataById(id);
    }
    /**
     * 修改数据
     * @param wmSensitive
     * @return
     */
    @PostMapping("/update")
    public ResponseResult updateData(@RequestBody WmSensitive wmSensitive){
        return wmSensitiveService.updateData(wmSensitive);
    }

}