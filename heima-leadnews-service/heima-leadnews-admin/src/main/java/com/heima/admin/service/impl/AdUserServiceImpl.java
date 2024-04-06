package com.heima.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.admin.mapper.AdUserMapper;
import com.heima.admin.service.AdUserService;
import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.admin.pojos.AdUser;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.utils.common.AppJwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;


@Service
public class AdUserServiceImpl extends ServiceImpl<AdUserMapper, AdUser> implements AdUserService {


    @Override
    public ResponseResult login(AdUserDto dto){
            //1.1查询用户
           AdUser adUser = getOne(Wrappers.<AdUser>lambdaQuery().eq(AdUser::getName, dto.getName()));
         if (adUser == null) {
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"用户不存在");
            }

            //1.2 比对密码
            String salt = adUser.getSalt();
           //判断盐是否为null,若为null直接进行密码比对
          String pswd =dto.getPassword();
          if(salt!=null){
              pswd = dto.getPassword();
              pswd = DigestUtils.md5DigestAsHex((pswd + salt).getBytes());
          }
        if (!pswd.equals(adUser.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
        //1.3 返回数据  jwt
            Map<String, Object> map = new HashMap<>();
            map.put("token", AppJwtUtil.getToken(adUser.getId().longValue()));
            adUser.setSalt("");
            adUser.setPassword("");
            map.put("user", adUser);
            return ResponseResult.okResult(map);
    }
}