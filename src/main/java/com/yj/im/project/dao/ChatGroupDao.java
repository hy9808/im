package com.yj.im.project.dao;

import com.yj.im.project.entity.ChatGroup;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 群组基础表(ChatGroup)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-13 18:02:42
 */
public interface ChatGroupDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ChatGroup queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ChatGroup> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param chatGroup 实例对象
     * @return 对象列表
     */
    List<ChatGroup> queryAll(ChatGroup chatGroup);

    /**
     * 通过用户ID查询所有群
     *
     * @param userId 用户id
     * @return 对象列表
     */
    List<ChatGroup> queryAllGroup(Long userId);

    /**
     * 通过群id修改群名
     *
     * @param groupName 群名称
     * @param id 群组id
     * @return 影响行数
     */
    int updateGroupName(String groupName,Long id);
    /**
     * 新增数据
     *
     * @param chatGroup 实例对象
     * @return 影响行数
     */
    int insert(ChatGroup chatGroup);

    /**
     * 新增数据
     *
     * @param chatGroup 实例对象
     * @return 返回群聊Id
     */
    Long insertNewOne(ChatGroup chatGroup);

    /**
     * 修改数据
     *
     * @param chatGroup 实例对象
     * @return 影响行数
     */
    int update(ChatGroup chatGroup);
    /**
     * 修改数据
     * @param  groupName 群组名
     * @param id 群组id
     * @return 影响行数
     */
    int updateGroupName(Long id,String groupName);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 判断用户是否为群主
     *
     * @param id  群id
     * @param groupCreateUserId 用户id
     * @return 影响行数
     */
    ChatGroup checkUser(Long id,Long groupCreateUserId);

}