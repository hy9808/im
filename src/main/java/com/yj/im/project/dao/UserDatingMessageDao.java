package com.yj.im.project.dao;

import com.yj.im.project.entity.UserDatingMessage;
import com.yj.im.project.entity.pojo.ChatFriendVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 交友信息表(UserDatingMessage)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-07 10:42:59
 */
public interface UserDatingMessageDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserDatingMessage queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserDatingMessage> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param userDatingMessage 实例对象
     * @return 对象列表
     */
    List<UserDatingMessage> queryAll(UserDatingMessage userDatingMessage);

    /**
     * 新增数据
     *
     * @param userDatingMessage 实例对象
     * @return 影响行数
     */
    int insert(UserDatingMessage userDatingMessage);

    /**
     * 修改数据
     *
     * @param userDatingMessage 实例对象
     * @return 影响行数
     */
    int update(UserDatingMessage userDatingMessage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 通过用户id获取用户信息，返回一个对象
     * @param userId
     * @return
     */
    ChatFriendVO queryByUserId(Long userId);
}