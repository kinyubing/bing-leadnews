package com.heima.user.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dto.AuthDto;
import com.heima.model.user.dto.LoginDto;
import com.heima.user.service.ApUserRealnameService;
import com.heima.user.service.ApUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class ApUserRealnameController {
    @Autowired
    private ApUserRealnameService apUserRealnameService;
    /**
     * 分页查询认证列表
     * @param authDto
     * @return
     */
    @PostMapping("/list")
    public ResponseResult pageQuery(@RequestBody AuthDto authDto) {

        return apUserRealnameService.pageQuery(authDto);
    }
    /**
     * 审核失败
     * @param authDto
     * @return
     */
    @PostMapping("/authFail")
    public ResponseResult authFail(@RequestBody AuthDto authDto) {

        return apUserRealnameService.authFail(authDto);
    }
    /**
     * 审核通过
     * @param authDto
     * @return
     */
    @PostMapping("/authPass")
    public ResponseResult authPass(@RequestBody AuthDto authDto) {

        return apUserRealnameService.authPass(authDto);
    }

}