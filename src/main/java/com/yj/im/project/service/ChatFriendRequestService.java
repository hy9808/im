package com.yj.im.project.service;

import com.yj.im.project.entity.ChatFriendRequest;
import com.yj.im.project.entity.pojo.ChatFriendVO;

import java.util.List;

/**
 * (ChatFriendRequest)表服务接口
 *
 * @author makejava
 * @since 2020-04-07 09:39:03
 */
public interface ChatFriendRequestService {

    /**
     * 通过ID查询单条数据
     *
     * @param friendRequestId 主键
     * @return 实例对象
     */
    ChatFriendRequest queryById(Long friendRequestId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ChatFriendRequest> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param chatFriendRequest 实例对象
     * @return 实例对象
     */
    ChatFriendRequest insert(ChatFriendRequest chatFriendRequest);

    /**
     * 修改数据
     *
     * @param chatFriendRequest 实例对象
     * @return 实例对象
     */
    ChatFriendRequest update(ChatFriendRequest chatFriendRequest);

    /**
     * 通过主键删除数据
     *
     * @param friendRequestId 主键
     * @return 是否成功
     */
    boolean deleteById(Long friendRequestId);

    /**
     * userid查询用户信息
     * @param userId
     * @return
     */
    ChatFriendVO selectByUserId(Long userId);

    /**
     * 查看请求者是否在接收者的好友列表中
     * @param id
     * @param userId
     * @return
     */
    ChatFriendVO doesItExist(Long id, Long userId);

    /**
     *
     * @param requestId 请求者id
     * @param nickName  请求者用户名
     * @param userId 接收者id
     * @param content 请求者对接收者的备注
     * @return
     */
    Long addNewFriend(Long requestId, String nickName, Long userId, String content);


    /**
     * 确认请求者请求
     * @param chatFriendVO 请求者，
     * @param receiverId 用户（接收者）id
     * @param remarks 接收者对请求者的备注（可以为空）
     * @param number 接收者确认状态 ）（1:同意请求；2:拒绝请求）
     * @return
     */
    Long comfirmRequest(ChatFriendVO chatFriendVO, Long receiverId, String remarks, Integer number);


    Object selectRequestByUserId(Long userId);
}