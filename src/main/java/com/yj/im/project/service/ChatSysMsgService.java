package com.yj.im.project.service;

import com.yj.im.project.entity.ChatSysMsg;
import java.util.List;

/**
 * 系统级消息记录(ChatSysMsg)表服务接口
 *
 * @author makejava
 * @since 2020-04-11 11:16:17
 */
public interface ChatSysMsgService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ChatSysMsg queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ChatSysMsg> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param chatSysMsg 实例对象
     * @return 实例对象
     */
    ChatSysMsg insert(ChatSysMsg chatSysMsg);

    /**
     * 修改数据
     *
     * @param chatSysMsg 实例对象
     * @return 实例对象
     */
    ChatSysMsg update(ChatSysMsg chatSysMsg);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);


    /**
     * 分组系统消息列表
     *
     * @return
     */
    List<ChatSysMsg> queryGroupBySysType();

    /**
     * 分组系统消息列表最新信息
     * @return
     */
    List<ChatSysMsg> queryGroupByMsg();

    /**
     * 根据系统消息的类型查询count
     *
     * @return
     */
    int selectByTypeCount(Integer smt);

}