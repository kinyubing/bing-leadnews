package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.ChannelDto;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.wemedia.service.WmChannelService;
import com.heima.wemedia.service.WmMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/channel")
public class WmchannelController {
  @Autowired
  private WmChannelService wmChannelService;

  /**
   * 查询所有的频道
   * @return
   */
    @GetMapping("/channels")
    public ResponseResult findAll(){

        return wmChannelService.findAll();
    }

  /**
   * 新增频道
   * @param wmChannel
   * @return
   */
   @PostMapping("/save")
  public ResponseResult save(@RequestBody WmChannel wmChannel){
     return wmChannelService.saveChannel(wmChannel);
  }

    /**
     * 频道分页查询
     * @param channelDto
     * @return
     */
    @PostMapping("/list")
    public ResponseResult pageQuery(@RequestBody ChannelDto channelDto){

        return wmChannelService.pageQuery(channelDto);
    }

    /**
     * 根据id获取频道
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getById(@PathVariable Integer id){
        return wmChannelService.getDataById(id);

    }
    /**
     * 根据id删除频道
     * @param id
     * @return
     */
    @GetMapping("/del/{id}")
    public ResponseResult delById(@PathVariable Integer id){
        return wmChannelService.delById(id);

    }
    /**
     * 修改频道
     * @param wmChannel
     * @return
     */
    @PostMapping("/update")
    public ResponseResult updateData(@RequestBody WmChannel wmChannel){

        return wmChannelService.updateData(wmChannel);
    }


}