package com.yj.im.project.dao;

import com.yj.im.project.entity.ChatRecord;
import com.yj.im.project.entity.pojo.ChatRecordMsgListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 聊天记录表(ChatRecord)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-07 16:14:52
 */
public interface ChatRecordDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ChatRecord queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ChatRecord> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param chatRecord 实例对象
     * @return 对象列表
     */
    List<ChatRecord> queryAll(ChatRecord chatRecord);

    /**
     * 新增数据
     *
     * @param chatRecord 实例对象
     * @return 影响行数
     */
    int insert(ChatRecord chatRecord);

    /**
     * 修改数据
     *
     * @param chatRecord 实例对象
     * @return 影响行数
     */
    int update(ChatRecord chatRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 单个聊天的信息
     *
     * @param userId
     * @param recipientId
     * @return
     */
    List<ChatRecord> selectByUserIdAndRId(@Param("userId") Long userId, @Param("recipientId") Long recipientId);


    /**
     * 通过用户id和好友id修改数据
     *
     * @return
     */
    int updateByUserAndRecordId(ChatRecord chatRecord);

    /**
     * 通过前端传过来的id列表查询未读消息用户
     * @param ids
     * @return
     */
    List<ChatRecordMsgListVo> selectByIdGetNotReadMsg(@Param("ids") List ids, @Param("userId") Long userId);

    /**
     * 通过前端传过来的id列表查询未读消息用户数量
     * @param ids
     * @return
     */
    List<ChatRecordMsgListVo> selectByIdGetNotReadCount(@Param("ids") List ids, @Param("userId") Long userId);
}