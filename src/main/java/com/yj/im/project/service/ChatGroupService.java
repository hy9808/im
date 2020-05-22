package com.yj.im.project.service;

import com.yj.im.project.entity.ChatGroup;
import com.yj.im.project.util.CommonResult;

import java.util.List;

/**
 * 群组基础表(ChatGroup)表服务接口
 *
 * @author makejava
 * @since 2020-04-13 18:02:42
 */
public interface ChatGroupService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ChatGroup queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ChatGroup> queryAllByLimit(int offset, int limit);

    /**
     * 通过用户ID查询所有群
     *
     * @param userId 用户id
     * @return 对象列表
     */
    Object queryAllGroup(Long userId);

    /**
     * 新增数据
     *
     * @param chatGroup 实例对象
     * @return 实例对象
     */
    ChatGroup insert(ChatGroup chatGroup);

    /**
     * 修改数据
     *
     * @param chatGroup 实例对象
     * @return 实例对象
     */
    ChatGroup update(ChatGroup chatGroup);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 通过群id修改群名
     *
     * @param groupName 群名称
     * @param id 群组id
     * @return 影响行数
     */
    Object updateGroupName(String groupName,Long id);

    /**
     * 判断用户是否为群主
     *
     * @param id  群id
     * @param groupCreateUserId 用户id
     * @return 影响行数
     */
    boolean check(Long id,Long groupCreateUserId);

    /**
     * 通过群主createUserId选择的朋友们的ids 创建名字群主的nickName  的 “nickName的群”
     * @param ids 群主选择的好友们的id
     * @param createUserId 群主id
     * @param nickName 群主的昵称
     * @return
     */
    CommonResult createGroupChatByIds(List<Long> ids, Long createUserId, String nickName);
}
