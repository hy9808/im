package com.yj.im.project.dao;

import com.yj.im.project.entity.ChatFriend;
import com.yj.im.project.entity.pojo.ChatFriendVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * (ChatFriend)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-07 09:38:24
 */
public interface ChatFriendDao {

    /**
     * 通过ID查询单条数据
     *
     * @param friendId 主键
     * @return 实例对象
     */
    ChatFriend queryById(Long friendId);

    /**
     * 通过用户id查询好友
     */
    List<ChatFriend> queryAllById(Long userId);

    /**
     * 通过用户id和分组id查询好友
     *
     * @param userId        用户id
     * @param friendGroupId 分组名
     * @return 对象集合
     */
    List<ChatFriend> queryByIdAndGroupId(Long userId, int friendGroupId);

    /**
     * 通过用户id查询所有分组下的好友
     *
     * @param userId 用户id
     * @return 对象集合
     */
    List<ChatFriend> queryAllGroupFriend(Long userId);


    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ChatFriend> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /*
     * 模糊查询
     * @param name 根据名字
     * @param userId 根据id
     * @return 对象列表
     * */
    List<ChatFriend> queryAllLike(@Param("name") String name, @Param("userId") Long userId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param chatFriend 实例对象
     * @return 对象列表
     */
    List<ChatFriend> queryAll(ChatFriend chatFriend);

    /**
     * 新增数据
     *
     * @param chatFriend 实例对象
     * @return 影响行数
     */
    Long insert(ChatFriend chatFriend);

    /**
     * 修改数据
     *
     * @param chatFriend 实例对象
     * @return 影响行数
     */
    int update(ChatFriend chatFriend);

    /**
     * 修改好友分组
     *
     * @param userId        用户id
     * @param userFriendId  好友id
     * @param friendGroupId 分组id
     * @param updateTime    修改时间
     * @return 影响行数
     */
    int updateGroup(Long userId, Long userFriendId, int friendGroupId, Date updateTime);

    /**
     * 删除好友-删除俩边好友关系
     *
     * @param userId       用户id
     * @param userFriendId 好友id
     * @param updateTime   修改时间
     * @return 影响行数
     */
    int updateStatus(Long userId, Long userFriendId, Date updateTime);

    /**
     * 通过主键删除数据
     *
     * @param friendId 主键
     * @return 影响行数
     */
    int deleteById(Long friendId);

    /**
     * 查看该用户是否是好友
     *
     * @param id
     * @param userId
     * @return
     */
    ChatFriendVO isExist(Long id, Long userId);

    /**
     * 通过朋友的id， 自己的id，修改朋友的备注
     *
     * @param id
     * @param userId
     * @param remarks
     * @param updateTime
     * @return
     */
    Long updateByRemarks(Long id, Long userId, String remarks, Date updateTime);

    void updateStatusByUserId(ChatFriend chatFriend2);

}