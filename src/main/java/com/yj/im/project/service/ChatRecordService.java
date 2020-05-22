package com.yj.im.project.service;

import com.yj.im.project.entity.ChatRecord;
import com.yj.im.project.entity.pojo.ChatRecordMsgListVo;

import java.util.List;
import java.util.Map;

/**
 * 聊天记录表(ChatRecord)表服务接口
 *
 * @author makejava
 * @since 2020-04-07 16:14:52
 */
public interface ChatRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ChatRecord queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ChatRecord> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param chatRecord 实例对象
     * @return 实例对象
     */
    ChatRecord insert(ChatRecord chatRecord);

    /**
     * 修改数据
     *
     * @param chatRecord 实例对象
     * @return 实例对象
     */
    ChatRecord update(ChatRecord chatRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);


    /**
     * 通过主键删除数据
     *
     * @param chatRecord 主键
     * @return 聊天列表
     */
    Object queryAllByEntity(ChatRecord chatRecord);


    /**
     * 单个聊天的信息
     *
     * @param userId
     * @param recipientId
     * @return
     */
    List<ChatRecord> selectByUserIdAndRId(Long userId, Long recipientId);


    /**
     * 通过用户id和好友id修改数据
     *
     * @return
     */
    int updateByUserAndRecordId(ChatRecord chatRecord);


    /**
     * 通过Mq异步储存消息，降低数据库压力
     *
     * @param chatRecord
     */
    void insertToMq(ChatRecord chatRecord) throws Exception;


    /**
     * @return
     */
    Map selectByIdGetNotReadMsg(Long userId);


    List<ChatRecordMsgListVo> selectByIdGetNotReadCount(List ids, Long userId);

    void chatGroupInsertMq(ChatRecord ChatRecord);

    Object updateSysMsgType(String id, Long readCount, Integer type);

}