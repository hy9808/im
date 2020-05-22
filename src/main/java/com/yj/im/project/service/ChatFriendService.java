package com.yj.im.project.service;

import com.yj.im.project.entity.ChatFriend;

import java.util.Date;
import java.util.List;

/**
 * (ChatFriend)表服务接口
 *
 * @author makejava
 * @since 2020-04-07 09:38:24
 */
public interface ChatFriendService {

    /**
     * 通过ID查询单条数据
     *
     * @param friendId 主键
     * @return 实例对象
     */
    ChatFriend queryById(Long friendId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ChatFriend> queryAllByLimit(int offset, int limit);

    /**
    *查询
    * @param userId 用户id
    * @return 对象集合
    *  */
    List<ChatFriend> queryAllById(Long userId);

    /**
    * 分组查询
    * @param userId 用户id
    * @param friendGroupId 分组id
    * @return 对象集合
    * */
    List<ChatFriend> queryByIdAndGroupId(Long userId,int friendGroupId);

    /**
     * 通过用户id查询所有分组下的好友
     *
     * @param userId 用户id
     * @return 对象集合
     */
    Object queryAllGroupFriend(Long userId);

    /**
    * 模糊查询搜索
    * @param name 根据名字查询
    * @param userId 根据id查询
    * */
    List<ChatFriend> queryAllLike(String name,Long userId);
    /**
     * 新增数据
     *
     * @param chatFriend 实例对象
     * @return 实例对象
     */
    ChatFriend insert(ChatFriend chatFriend);

    /**
     * 修改数据
     *
     * @param chatFriend 实例对象
     * @return 实例对象
     */
    ChatFriend update(ChatFriend chatFriend);

    /**
     * 修改好友分组
     * @param userId 用户id
     * @param userFriendId 好友id
     * @param friendGroupId 好友分组id
     * @return 影响行数
     * */
    Object updateGroup(Long userId,Long userFriendId,int friendGroupId);


    /**
     * 删除好友
     * @param userId 用户id
     * @param userFriendId 好友id
     * @return 影响行数
     * */
     Object updateStatus(Long userId, Long userFriendId);

    /**
     * 通过主键删除数据
     *
     * @param friendId 主键
     * @return 是否成功
     */
    boolean deleteById(Long friendId);

    /**
     * 通过朋友的id，自己的id找到对应的数据，更改朋友的备注
     * @param id  朋友的id
     * @param userId  自己的id
     * @param remarks  朋友的备注
     * @return
     */
    Long updateFriendRemarks(Long id, Long userId, String remarks);
}