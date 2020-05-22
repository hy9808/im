package com.yj.im.project.dao;

import com.yj.im.project.entity.ChatUserGroup;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户和群关联表(ChatUserGroup)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-13 18:03:07
 */
public interface ChatUserGroupDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ChatUserGroup queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ChatUserGroup> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param chatUserGroup 实例对象
     * @return 对象列表
     */
    List<ChatUserGroup> queryAll(ChatUserGroup chatUserGroup);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param userId 用户id
     * @param groupId 群组id
     * @return 对象列表
     */
    List<ChatUserGroup> queryAllUser(Long userId,Long groupId);

    /**
     * 新增数据
     *
     * @param chatUserGroup 实例对象
     * @return 影响行数
     */
    int insert(ChatUserGroup chatUserGroup);

    /**
     * 修改数据
     *
     * @param chatUserGroup 实例对象
     * @return 影响行数
     */
    int update(ChatUserGroup chatUserGroup);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 通过群组id和用户id退出群聊
     *
     * @param userId 用户id
     * @param groupId 群组id
     * @param updateTime 修改时间
     * @param  status 状态
     * @return 影响行数
     */
    int updateStatus(Long userId, Long groupId, Date updateTime,int status);

    /**
     * 插入数据
     *
     * @param chatUserGroup 实例对象
     * @return 影响行数
     */
    int insertByCrCode(ChatUserGroup chatUserGroup);

    /**
     * 通过群组id和用户id判断用户是否群员
     *
     * @param userId 用户id
     * @param groupId 群组id
     * @return 状态
     */
    ChatUserGroup check(Long userId,Long groupId);

    /**
     * 批量插入chat_user_group
     * @param userGroupsList
     */
    int batchInsert(@Param("list") ArrayList<ChatUserGroup> userGroupsList);

    /**
     * 修改群的个人备注
     * @param chatUserGroup
     * @return
     */
    Long updateRemarks(ChatUserGroup chatUserGroup);

    /**
     * 群主userId把用户id提出群groupId
     * @param map map里有chatUserGroup对象和群主id  userId
     * @return
     */
    Long deleteManById(Map<String, Object> map);

    /**
     * 查询群成员数量
     * @param groupId
     * @return
     */
    int queryByGroupId(Long groupId);

    /**
     * 通过群组id和用户id查询用户在群里面的名称
     * @param groupId
     * @param userId
     * @return
     */
    String selectByGroupIdAndUserIdGetRemarks(Long groupId,Long userId);
}