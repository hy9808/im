package com.yj.im.project.service;

import com.yj.im.project.entity.ChatSysRead;

import java.util.List;

/**
 * 系统级消息表读取表(ChatSysRead)表服务接口
 *
 * @author makejava
 * @since 2020-04-11 11:16:41
 */
public interface ChatSysReadService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ChatSysRead queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ChatSysRead> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param chatSysRead 实例对象
     * @return 实例对象
     */
    ChatSysRead insert(ChatSysRead chatSysRead);

    /**
     * 修改数据
     *
     * @param chatSysRead 实例对象
     * @return 实例对象
     */
    ChatSysRead update(ChatSysRead chatSysRead);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据用户id分组消息类型查看已读消息
     *
     * @param userId
     * @return
     */
    List<ChatSysRead> queryByIdGroupReadType(Long userId);


    /**
     * 根据用户id和类型修改对应的读取数量
     *
     * @param
     * @return
     */
    int updateByTypeAndIdToReadCount(ChatSysRead chatSysRead);

}