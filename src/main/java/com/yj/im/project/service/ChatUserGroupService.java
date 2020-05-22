package com.yj.im.project.service;

import com.yj.im.project.entity.ChatUserGroup;
import com.yj.im.project.util.CommonResult;

import java.util.List;

/**
 * 用户和群关联表(ChatUserGroup)表服务接口
 *
 * @author makejava
 * @since 2020-04-13 18:03:07
 */
public interface ChatUserGroupService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ChatUserGroup queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ChatUserGroup> queryAllByLimit(int offset, int limit);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param userId  用户id
     * @param groupId 群组id
     * @return 对象列表
     */
    Object queryAllUser(Long userId, Long groupId);

    /**
     * 新增数据
     *
     * @param chatUserGroup 实例对象
     * @return 实例对象
     */
    ChatUserGroup insert(ChatUserGroup chatUserGroup);

    /**
     * 修改数据
     *
     * @param chatUserGroup 实例对象
     * @return 实例对象
     */
    ChatUserGroup update(ChatUserGroup chatUserGroup);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 通过群组id和用户id退出群聊
     *
     * @param userId  用户id
     * @param groupId 群组id
     * @return 影响行数
     */
    Object updateStatus(Long userId, Long groupId);

    /**
     * 修改数据
     *
     * @param chatUserGroup 实例对象
     * @return 影响行数
     */
    Object insertByCrCode(ChatUserGroup chatUserGroup);

    /**
     * 通过群组id和用户id判断用户是否群员
     *
     * @param userId  用户id
     * @param groupId 群组id
     * @return 状态
     */
    Object check(Long userId, Long groupId);

    /**
     * 通过群组id，自己的id修改自己在该群的备注
     * @param groupId  群id
     * @param userId  用户id
     * @param remarks  备注
     * @return
     */
    Long rewriteRemarksById(Long groupId, Long userId, String remarks);

    /**
     * 群主踢人
     * @param userId 群主的id
     * @param id 被踢的用户id
     * @param groupId 群组id
     * @return
     */
    CommonResult kickManById(Long userId, Long id, Long groupId);
}