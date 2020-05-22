package com.yj.im.project.dao;

import com.yj.im.project.entity.ChatFriendGrouping;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * (ChatFriendGrouping)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-07 09:38:50
 */
public interface ChatFriendGroupingDao {

    /**
     * 通过ID查询单条数据
     *
     * @param friendGroupId 主键
     * @return 实例对象
     */
    ChatFriendGrouping queryById(Long friendGroupId);
    /**
    * 通过用户id查询所有分组
    * @param userId 用户id
    * @retrun 数据集合
    * */
    List<ChatFriendGrouping> queryAllById(Long userId);
    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ChatFriendGrouping> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param chatFriendGrouping 实例对象
     * @return 对象列表
     */
    List<ChatFriendGrouping> queryAll(ChatFriendGrouping chatFriendGrouping);

    /**
     * 新增数据
     *
     * @param chatFriendGrouping 实例对象
     * @return 影响行数
     */
    int insert(ChatFriendGrouping chatFriendGrouping);

    /**
     * 修改数据
     *
     * @param chatFriendGrouping 实例对象
     * @return 影响行数
     */
    int update(ChatFriendGrouping chatFriendGrouping);

    /**
     * 新增分组
     *
     * @param userId 用户id
     * @param groupName 分组名
     * @param createTime 创建时间
     * @param updateTime 修改时间
     * @param status 状态
     * @return 影响行数
     */
    int addGroup(Long userId, String groupName, Date createTime,Date updateTime,int status);

    /**
     * 删除分组
     *
     * @param friendGroupId 分组id
     * @return 影响行数
     */
    int deleteGroup(int friendGroupId,Date updateTime);

    /**
     * 修改分组名
     *
     * @param groupName 分组名
     * @param friendGroupId 分组id
     * @return 影响行数
     */
    int updateGroupName(String groupName,int friendGroupId,Date updateTime);
    /**
     * 通过主键删除数据
     *
     * @param friendGroupId 主键
     * @return 影响行数
     */
    int deleteById(Long friendGroupId);

}