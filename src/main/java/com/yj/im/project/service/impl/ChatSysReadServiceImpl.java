package com.yj.im.project.service.impl;

import com.yj.im.project.entity.ChatSysRead;
import com.yj.im.project.dao.ChatSysReadDao;
import com.yj.im.project.service.ChatSysMsgService;
import com.yj.im.project.service.ChatSysReadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统级消息表读取表(ChatSysRead)表服务实现类
 *
 * @author makejava
 * @since 2020-04-11 11:16:41
 */
@Service("chatSysReadService")
public class ChatSysReadServiceImpl implements ChatSysReadService {
    @Resource
    private ChatSysReadDao chatSysReadDao;

    @Resource
    private ChatSysMsgService chatSysMsgService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ChatSysRead queryById(Long id) {
        return this.chatSysReadDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ChatSysRead> queryAllByLimit(int offset, int limit) {
        return this.chatSysReadDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param chatSysRead 实例对象
     * @return 实例对象
     */
    @Override
    public ChatSysRead insert(ChatSysRead chatSysRead) {
        this.chatSysReadDao.insert(chatSysRead);
        return chatSysRead;
    }

    /**
     * 修改数据
     *
     * @param chatSysRead 实例对象
     * @return 实例对象
     */
    @Override
    public ChatSysRead update(ChatSysRead chatSysRead) {
        this.chatSysReadDao.update(chatSysRead);
        return this.queryById(chatSysRead.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.chatSysReadDao.deleteById(id) > 0;
    }

    @Override
    public List<ChatSysRead> queryByIdGroupReadType(Long userId) {
        return this.chatSysReadDao.queryByIdGroupReadType(userId);
    }

    @Override
    public int updateByTypeAndIdToReadCount(ChatSysRead chatSysRead){
        chatSysRead.setReadCount(chatSysMsgService.selectByTypeCount(chatSysRead.getReadType()));
        if(this.chatSysReadDao.selectByUserIdCount(chatSysRead.getUserId())==0){
            //莫得数据 创建一条
            this.chatSysReadDao.insert(chatSysRead);
        }
        return this.chatSysReadDao.updateByTypeAndIdToReadCount(chatSysRead);
    }
}