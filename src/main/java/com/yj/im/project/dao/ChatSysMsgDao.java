package com.yj.im.project.dao;

import com.yj.im.project.entity.ChatSysMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统级消息记录(ChatSysMsg)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-11 11:16:17
 */
public interface ChatSysMsgDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ChatSysMsg queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ChatSysMsg> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param chatSysMsg 实例对象
     * @return 对象列表
     */
    List<ChatSysMsg> queryAll(ChatSysMsg chatSysMsg);

    /**
     * 新增数据
     *
     * @param chatSysMsg 实例对象
     * @return 影响行数
     */
    int insert(ChatSysMsg chatSysMsg);

    /**
     * 修改数据
     *
     * @param chatSysMsg 实例对象
     * @return 影响行数
     */
    int update(ChatSysMsg chatSysMsg);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 分组系统消息列表
     *
     * @return
     */
    List<ChatSysMsg> queryGroupBySysType();

    /**
     * 分组系统消息列表最新信息
     *
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