package com.yj.im.project.dao;

import com.yj.im.project.entity.ChatFriendRequest;
import com.yj.im.project.entity.pojo.ChatFriendVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (ChatFriendRequest)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-07 09:39:03
 */
public interface ChatFriendRequestDao {

    /**
     * 通过ID查询单条数据
     *
     * @param friendRequestId 主键
     * @return 实例对象
     */
    ChatFriendRequest queryById(Long friendRequestId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ChatFriendRequest> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param chatFriendRequest 实例对象
     * @return 对象列表
     */
    List<ChatFriendRequest> queryAll(ChatFriendRequest chatFriendRequest);

    /**
     * 新增数据
     *
     * @param chatFriendRequest 实例对象
     * @return 影响行数
     */
    int insert(ChatFriendRequest chatFriendRequest);

    /**
     * 修改数据
     *
     * @param chatFriendRequest 实例对象
     * @return 影响行数
     */
    int update(ChatFriendRequest chatFriendRequest);

    /**
     * 通过主键删除数据
     *
     * @param friendRequestId 主键
     * @return 影响行数
     */
    int deleteById(Long friendRequestId);

    /**
     *
     * @param chatFriendRequest
     * @return
     */
    Long insertByUserId(ChatFriendRequest chatFriendRequest);


    Long  updateRequestStatusByUserId(ChatFriendRequest chatFriendRequest);

    /**
     *
     * @param userId
     * @return
     */
    List<ChatFriendVO> queryRequestByUserId(Long userId);
}