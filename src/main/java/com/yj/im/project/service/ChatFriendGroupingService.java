package com.yj.im.project.service;

import com.yj.im.project.entity.ChatFriendGrouping;
import java.util.List;

/**
 * (ChatFriendGrouping)表服务接口
 *
 * @author makejava
 * @since 2020-04-07 09:38:50
 */
public interface ChatFriendGroupingService {

    /**
     * 通过ID查询单条数据
     *
     * @param friendGroupId 主键
     * @return 实例对象
     */
    ChatFriendGrouping queryById(Long friendGroupId);

    /**
     * 查询多条数据
     *
     * @param userId 查询用户所有分组
     * @return 对象集合
     */
    List<ChatFriendGrouping> queryAllById(Long userId);
    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ChatFriendGrouping> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param chatFriendGrouping 实例对象
     * @return 实例对象
     */
    ChatFriendGrouping insert(ChatFriendGrouping chatFriendGrouping);

    /**
     * 新增用户分组
     *
     * @param userId 用户id
     * @param groupName 分组名
     * @return 实例对象
     */
    Object addGroup(Long userId,String groupName);
    /**
     * 修改数据
     *
     * @param chatFriendGrouping 实例对象
     * @return 实例对象
     */
    ChatFriendGrouping update(ChatFriendGrouping chatFriendGrouping);

    /**
     * 通过用户id和分组名删除分组
     *
     * @param friendGroudId 分组id
     * @return 影响数
     */
    Object deleteGroup(int friendGroudId);

    /**
     * 修改分组名
     *
     * @param friendGroupId 用户id
     * @param groupName 分组名
     * @return 影响数
     */
    Object updateGroupName(int friendGroupId,String groupName);

    /**
     * 通过主键删除数据
     *
     * @param friendGroupId 主键
     * @return 是否成功
     */
    boolean deleteById(Long friendGroupId);

}