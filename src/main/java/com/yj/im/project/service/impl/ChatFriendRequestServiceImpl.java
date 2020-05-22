package com.yj.im.project.service.impl;

import com.yj.im.project.dao.ChatFriendDao;
import com.yj.im.project.dao.ChatFriendRequestDao;
import com.yj.im.project.dao.UserDatingMessageDao;
import com.yj.im.project.entity.ChatFriend;
import com.yj.im.project.entity.ChatFriendRequest;
import com.yj.im.project.entity.pojo.ChatFriendVO;
import com.yj.im.project.service.ChatFriendRequestService;
import com.yj.im.project.util.CommonResult;
import com.yj.im.project.util.RedisOperator;
import com.yj.im.project.util.contants.ResultConstantsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * (ChatFriendRequest)表服务实现类
 *
 * @author makejava
 * @since 2020-04-07 09:39:03
 */
@Service
public class ChatFriendRequestServiceImpl implements ChatFriendRequestService {

    @Autowired
    private ChatFriendRequestDao chatFriendRequestDao;

    @Autowired
    private UserDatingMessageDao userDatingMessageDao;

    @Autowired
    private ChatFriendDao chatFriendDao;

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 通过ID查询单条数据
     *
     * @param friendRequestId 主键
     * @return 实例对象
     */
    @Override
    public ChatFriendRequest queryById(Long friendRequestId) {
        return this.chatFriendRequestDao.queryById(friendRequestId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ChatFriendRequest> queryAllByLimit(int offset, int limit) {
        return this.chatFriendRequestDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param chatFriendRequest 实例对象
     * @return 实例对象
     */
    @Override
    public ChatFriendRequest insert(ChatFriendRequest chatFriendRequest) {
        this.chatFriendRequestDao.insert(chatFriendRequest);
        return chatFriendRequest;
    }

    /**
     * 修改数据
     *
     * @param chatFriendRequest 实例对象
     * @return 实例对象
     */
    @Override
    public ChatFriendRequest update(ChatFriendRequest chatFriendRequest) {
        this.chatFriendRequestDao.update(chatFriendRequest);
        return this.queryById(chatFriendRequest.getFriendRequestId());
    }

    /**
     * 通过主键删除数据
     *
     * @param friendRequestId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long friendRequestId) {
        return this.chatFriendRequestDao.deleteById(friendRequestId) > 0;
    }

    /**
     * 前端传来userId，返回一个对象前端生成二维码
     *
     * @param userId userId
     * @return
     */
    @Override
    public ChatFriendVO selectByUserId(Long userId) {
        return userDatingMessageDao.queryByUserId(userId);
    }

    /**
     * 接收者userId查看是否有id这个好友
     *
     * @param id     请求者
     * @param userId 接收者
     * @return
     */
    @Override
    public ChatFriendVO doesItExist(Long id, Long userId) {
        return chatFriendDao.isExist(id, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addNewFriend(Long requestId, String nickName, Long userId, String content) {
        //向请求者的好友表中插入一条数据，但这条数据是无效数据，待接收者确认请求后更新好友状态及创建时间
        ChatFriend chatFriend = new ChatFriend()
                .setUserFriendId(userId)
                .setFriendName(nickName)
                .setUserId(requestId)
                .setRemarks(content == null ? nickName : content)
                .setFriendGroupId((long) 0)
                .setCreateTime(new Date())
                .setStatus(0);
        chatFriendDao.insert(chatFriend);
        ChatFriendRequest chatFriendRequest = new ChatFriendRequest()
                .setUserId(userId)
                .setRequestContent(content == null ? "" : content)
                .setCreateTime(new Date())
                .setRequestStatus(3)
                .setRequesterId(requestId)
                .setStatus(1);
        return chatFriendRequestDao.insertByUserId(chatFriendRequest);

    }


//    public ChatFriendVO selectRequestByUserId(Long userId) {
//        return chatFriendRequestDao.queryRequestByUserId(userId);
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long comfirmRequest(ChatFriendVO chatFriendVO, Long receiverId, String remarks, Integer number) {
        Long returnId = null;
        ChatFriendRequest chatFriendRequest;
        switch (number) {
            //确认请求
            case 1:
                //1   接收者同意请求者的好友请求
                //在接收者的好友表里插入请求者的信息
                ChatFriend chatFriend = new ChatFriend()
                        .setUserFriendId(chatFriendVO.getUserId())
                        .setFriendName(chatFriendVO.getNickName())
                        .setUserId(receiverId)
                        .setRemarks(remarks == null ? chatFriendVO.getNickName() : remarks)
                        .setCreateTime(new Date())
                        .setFriendGroupId((long) 0)
                        .setStatus(1);
                returnId = chatFriendDao.insert(chatFriend);
                //更新请求表中请求者的请求状态及创建时间
                chatFriendRequest = new ChatFriendRequest()
                        .setRequesterId(chatFriendVO.getUserId())
                        .setUserId(receiverId)
                        .setUpdateTime(new Date())
                        .setRequestStatus(number);
                chatFriendRequestDao.updateRequestStatusByUserId(chatFriendRequest);
                //更新请求者的好友列表，设置好友状态为 1
                ChatFriend chatFriend2 = new ChatFriend()
                        .setUserFriendId(receiverId)
                        .setUserId(chatFriendVO.getUserId())
                        .setCreateTime(new Date())
                        .setStatus(1);
                chatFriendDao.updateStatusByUserId(chatFriend2);

                break;
            case 2:
                // 2   接收者拒绝请求者的好友请求
                //更新请求状态及更新时间
                chatFriendRequest = new ChatFriendRequest()
                        .setRequesterId(chatFriendVO.getUserId())
                        .setUserId(receiverId)
                        .setUpdateTime(new Date())
                        .setRequestStatus(number);
                returnId = chatFriendRequestDao.updateRequestStatusByUserId(chatFriendRequest);
                break;
        }
        return returnId;
    }

    @Override
    public CommonResult selectRequestByUserId(Long userId) {
        List<ChatFriendVO> voList = chatFriendRequestDao.queryRequestByUserId(userId);
        if (voList.size() > 0){
            return new CommonResult(ResultConstantsEnum.RESULT_OK, "success", voList);
        }
        return new CommonResult(ResultConstantsEnum.ERROR_NO_DATA, "error");
    }


}