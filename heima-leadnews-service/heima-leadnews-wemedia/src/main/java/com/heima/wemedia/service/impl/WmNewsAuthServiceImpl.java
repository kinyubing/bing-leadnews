package com.heima.wemedia.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.apis.article.IArticleClient;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.NewsAuthDto;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.model.wemedia.vos.WmNewsAuthVo;
import com.heima.wemedia.mapper.WmChannelMapper;
import com.heima.wemedia.mapper.WmNewsAuthMapper;
import com.heima.wemedia.mapper.WmUserMapper;
import com.heima.wemedia.service.WmNewsAuthService;
import com.heima.wemedia.service.WmNewsAutoScanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
public class WmNewsAuthServiceImpl extends ServiceImpl<WmNewsAuthMapper, WmNews> implements WmNewsAuthService {
    /**
     * 管理端人工审核列表查询
     * @param dto
     * @return
     */
    @Autowired
    private WmUserMapper wmUserMapper;
    @Override
    public ResponseResult findAll(NewsAuthDto dto) {
        //分页参数检查
        dto.checkParam();
        //2.分页条件查询
        IPage page = new Page(dto.getPage(),dto.getSize());
        LambdaQueryWrapper<WmNews> lambdaQueryWrapper = new LambdaQueryWrapper<WmNews>();
        //状态精确查询
        if(dto.getStatus() != null){   //向上转型
            lambdaQueryWrapper.eq(WmNews::getStatus,dto.getStatus());
        }
        //标题模糊查询
        lambdaQueryWrapper.like(WmNews::getTitle,dto.getTitle());

        //创建时间倒序查询
        lambdaQueryWrapper.orderByDesc(WmNews::getCreatedTime);

        page = page(page,lambdaQueryWrapper);
        List<WmNewsAuthVo> list=new ArrayList<>();
        //获取用户名称
        List records = page.getRecords();
        LambdaQueryWrapper<WmUser> queryWrapper=new LambdaQueryWrapper<>();
        for (Object record : records) {
            WmNewsAuthVo wmNewsAuthVo = new WmNewsAuthVo();
            BeanUtils.copyProperties(record,wmNewsAuthVo );
            //根据自媒体文章的user_id查询作者名
            queryWrapper.eq(WmUser::getId,wmNewsAuthVo.getUserId());
            WmUser wmUser = wmUserMapper.selectOne(queryWrapper);
            wmNewsAuthVo.setAuthorName(wmUser.getName());
            list.add(wmNewsAuthVo);
        }
       // 3.结果返回
        ResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(),(int)page.getTotal());
        responseResult.setData(list);
        return responseResult;
    }
    /**
     * 管理端查看文章详情
     * @param id
     * @return
     */
    @Override
    public ResponseResult getDetail(Integer id) {
        //参数校验
        if(id==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        LambdaQueryWrapper<WmUser> queryWrapper=new LambdaQueryWrapper<>();
        //根据id查看文章详情
        WmNews wmNews = getById(id);
        //获取文章的作者
        //根据自媒体文章的user_id查询作者名
        WmNewsAuthVo wmNewsAuthVo = new WmNewsAuthVo();
        BeanUtils.copyProperties(wmNews,wmNewsAuthVo);
        queryWrapper.eq(WmUser::getId,wmNews.getUserId());
        WmUser wmUser = wmUserMapper.selectOne(queryWrapper);
        wmNewsAuthVo.setAuthorName(wmUser.getName());
        return ResponseResult.okResult(wmNewsAuthVo);
    }
    /**
     * 管理端人工审核失败
     * @param dto
     * @return
     */
    @Override
    public ResponseResult authFail(NewsAuthDto dto) {
       //根据id修改文章状态并给出拒绝理由
        WmNews wmNews = new WmNews();
        wmNews.setId(dto.getId());
        wmNews.setStatus((dto.getStatus()).shortValue());//向下转型
        wmNews.setReason(dto.getMsg());
        updateById(wmNews);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
    @Autowired
    private IArticleClient articleClient;
    @Autowired
    private WmNewsAutoScanServiceImpl wmNewsAutoScanService;
    /**
     * 管理端人工审核成功
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public ResponseResult authPass(NewsAuthDto dto) {
        //根据id修改文章状态并给出拒绝理由
        WmNews wmNews = new WmNews();
        wmNews.setId(dto.getId());
        wmNews.setStatus((dto.getStatus()).shortValue());//向下转型
        updateById(wmNews);
        //重新获取最新的wmnews
        WmNews wmNews1 = getById(dto.getId());
        //创建app端的文章信息（远程调用）
        //4.审核成功，保存app端的相关的文章数据（远程调用）
        ResponseResult responseResult = saveAppArticle(wmNews1);
        if(!responseResult.getCode().equals(200)){
            throw new RuntimeException("WmNewsAutoScanServiceImpl-文章审核，保存app端相关文章数据失败");
        }
        //回填article_id
        wmNews.setArticleId((Long) responseResult.getData());
        //发布成功
        wmNews.setStatus(WmNews.Status.PUBLISHED.getCode());
        updateById(wmNews);
        return findAll(dto);
    }
    @Autowired
    private WmChannelMapper wmChannelMapper;
    /**
     * 保存app端相关的文章数据
     * @param wmNews
     */
    private ResponseResult saveAppArticle(WmNews wmNews) {

        ArticleDto dto = new ArticleDto();
        //属性的拷贝
        BeanUtils.copyProperties(wmNews,dto);
        //文章的布局
        dto.setLayout(wmNews.getType());
        //频道
        WmChannel wmChannel = wmChannelMapper.selectById(wmNews.getChannelId());
        if(wmChannel != null){
            dto.setChannelName(wmChannel.getName());
        }

        //作者
        dto.setAuthorId(wmNews.getUserId().longValue());
        WmUser wmUser = wmUserMapper.selectById(wmNews.getUserId());
        if(wmUser != null){
            dto.setAuthorName(wmUser.getName());
        }

        //设置文章id
        if(wmNews.getArticleId() != null){
            dto.setId(wmNews.getArticleId());
        }
        dto.setCreatedTime(new Date());

        ResponseResult responseResult = articleClient.saveArticle(dto);
        return responseResult;

    }
}