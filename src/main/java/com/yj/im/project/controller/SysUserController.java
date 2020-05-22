package com.yj.im.project.controller;

import com.yj.im.project.entity.SysUser;
import com.yj.im.project.service.SysUserService;
import com.yj.im.project.util.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户基础信息表(SysUser)表控制层
 *
 * @author makejava
 * @since 2020-04-06 12:04:08
 */
@RestController
@RequestMapping("/api")
@Api(tags = "用户管理相关接口")
public class SysUserController {
    /**
     * 服务对象
     */
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    RedisOperator redisOperator;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("根据id查找用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", defaultValue = "1", required = true, paramType = "query", dataType = " integer"),
    })
    @RequestMapping(value = "/selectOne", method = {RequestMethod.POST, RequestMethod.GET})
    public SysUser selectOne(Long id) {
        return this.sysUserService.queryById(id);
    }

    /**
     * 通过用户账号（亿家号或电话号码）查询是否存在该用户
     * @param account
     * @return
     */
    @ApiOperation("根据用户账号（亿家号或电话号码）查询是否存在该用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "用户账号", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/selectByAccount", method = {RequestMethod.GET, RequestMethod.POST})
    public Object selectByAccount(String account){
        return sysUserService.selectByAccount(account);
    }

}