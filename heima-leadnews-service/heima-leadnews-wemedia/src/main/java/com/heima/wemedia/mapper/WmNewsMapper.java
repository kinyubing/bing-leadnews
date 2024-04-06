package com.heima.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.wemedia.dtos.WmNewsDto2;
import com.heima.model.wemedia.pojos.WmNews;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WmNewsMapper  extends BaseMapper<WmNews> {
    //修改文章的上下架字段enable
    void down_or_up(Integer id, Short enable);
}