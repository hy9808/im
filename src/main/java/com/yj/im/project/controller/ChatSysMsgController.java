package com.yj.im.project.controller;

import com.yj.im.project.entity.ChatSysMsg;
import com.yj.im.project.service.ChatSysMsgService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 系统级消息记录(ChatSysMsg)表控制层
 *
 * @author makejava
 * @since 2020-04-11 11:16:17
 */
@RestController
@RequestMapping("chatSysMsg")
public class ChatSysMsgController {
    /**
     * 服务对象
     */
    @Resource
    private ChatSysMsgService chatSysMsgService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ChatSysMsg selectOne(Integer id) {
        return this.chatSysMsgService.queryById(id);
    }

}