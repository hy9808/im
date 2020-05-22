package com.yj.im.project.controller;

import com.yj.im.project.entity.UserDatingMessage;
import com.yj.im.project.service.UserDatingMessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 交友信息表(UserDatingMessage)表控制层
 *
 * @author makejava
 * @since 2020-04-07 10:42:59
 */
@RestController
@RequestMapping("userDatingMessage")
public class UserDatingMessageController {
    /**
     * 服务对象
     */
    @Resource
    private UserDatingMessageService userDatingMessageService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public UserDatingMessage selectOne(Long id) {
        return this.userDatingMessageService.queryById(id);
    }

}