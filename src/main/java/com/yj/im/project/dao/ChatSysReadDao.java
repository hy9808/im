package com.yj.im.project.dao;

import com.yj.im.project.entity.ChatSysRead;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统级消息表读取表(ChatSysRead)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-11 11:16:41
 */
public interface ChatSysReadDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ChatSysRead queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ChatSysRead> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param chatSysRead 实例对象
     * @return 对象列表
     */
    List<ChatSysRead> queryAll(ChatSysRead chatSysRead);

    /**
     * 新增数据
     *
     * @param chatSysRead 实例对象
     * @return 影响行数
     */
    int insert(ChatSysRead chatSysRead);

    /**
     * 修改数据
     *
     * @param chatSysRead 实例对象
     * @return 影响行数
     */
    int update(ChatSysRead chatSysRead);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);


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

    /**
     * 通过userId判断有么有数据
     *
     * @param userId
     * @return
     */
    int selectByUserIdCount(Long userId);

    int updateSysMsgType(Long id, Long readCount);
}