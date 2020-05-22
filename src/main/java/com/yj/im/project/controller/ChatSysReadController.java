package com.yj.im.project.controller;

import com.yj.im.project.entity.ChatSysRead;
import com.yj.im.project.service.ChatSysReadService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 系统级消息表读取表(ChatSysRead)表控制层
 *
 * @author makejava
 * @since 2020-04-11 11:16:41
 */
@RestController
@RequestMapping("chatSysRead")
public class ChatSysReadController {
    /**
     * 服务对象
     */
    @Resource
    private ChatSysReadService chatSysReadService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ChatSysRead selectOne(Long id) {
        return this.chatSysReadService.queryById(id);
    }

}