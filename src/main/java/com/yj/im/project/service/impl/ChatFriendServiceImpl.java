package com.yj.im.project.service.impl;

import com.yj.im.project.entity.ChatFriend;
import com.yj.im.project.dao.ChatFriendDao;
import com.yj.im.project.service.ChatFriendService;
import com.yj.im.project.util.CommonResult;
import com.yj.im.project.util.SortUtil;
import com.yj.im.project.util.contants.ResultConstantsEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (ChatFriend)表服务实现类
 *
 * @author makejava
 * @since 2020-04-07 09:38:24
 */
@Service
public class ChatFriendServiceImpl implements ChatFriendService {
    @Resource
    private ChatFriendDao chatFriendDao;

    /**
     * 通过ID查询单条数据
     *
     * @param friendId 主键
     * @return 实例对象
     */
    @Override
    public ChatFriend queryById(Long friendId) {
        return this.chatFriendDao.queryById(friendId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ChatFriend> queryAllByLimit(int offset, int limit) {
        return this.chatFriendDao.queryAllByLimit(offset, limit);
    }
    /*
    * 搜索该用户所有好友
    * @param userId 根据用户id
    * */
    @Override
    public List<ChatFriend> queryAllById(Long userId) {
        return SortUtil.sort(this.chatFriendDao.queryAllById(userId));
    }
    /*
     * 搜索该用户分组下的好友
     * @param userId 根据用户id
     * @param groupName 分组名
     * @return 对象集合
     * */
    @Override
    public List<ChatFriend> queryByIdAndGroupId(Long userId, int friendGroupId) {
        return SortUtil.sort(this.chatFriendDao.queryByIdAndGroupId(userId,friendGroupId));
    }

    /**
     * 通过用户id查询所有分组下的好友
     *
     * @param userId 用户id
     * @return 对象集合
     */
    @Override
    public Object queryAllGroupFriend(Long userId) {
        return this.chatFriendDao.queryAllGroupFriend(userId);
    }

    /*
    * 搜索好友
    * @param name 查询条件
     * @param userId 查询用户id
    * */
    @Override
    public List<ChatFriend> queryAllLike(String name, Long userId) {
        return SortUtil.sort(this.chatFriendDao.queryAllLike(name,userId));
    }

    /**
     * 新增数据
     *
     * @param chatFriend 实例对象
     * @return 实例对象
     */
    @Override
    public ChatFriend insert(ChatFriend chatFriend) {
        this.chatFriendDao.insert(chatFriend);
        return chatFriend;
    }

    /**
     * 修改数据
     *
     * @param chatFriend 实例对象
     * @return 实例对象
     */
    @Override
    public ChatFriend update(ChatFriend chatFriend) {
        this.chatFriendDao.update(chatFriend);
        return this.queryById(chatFriend.getFriendId());
    }

    /**
     * 修改好友分组
     *
     * @param userId        用户id
     * @param userFriendId  好友id
     * @param friendGroupId 好友分组id
     * @return 影响行数
     */
    @Override
    public Object updateGroup(Long userId, Long userFriendId, int friendGroupId) {
        if(this.chatFriendDao.updateGroup(userId,userFriendId,friendGroupId,new Date())>0){
            return new CommonResult(ResultConstantsEnum.RESULT_OK,"修改成功");
        }
        return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE,"删除失败");
    }

    /**
     * 删除好友
     * @param userId 用户id
     * @param userFriendId 好友id
     * @return 影响行数
     * */
    @Override
    public Object updateStatus(Long userId, Long userFriendId) {
        if(this.chatFriendDao.updateStatus(userId,userFriendId,new Date())>0){
            return new CommonResult(ResultConstantsEnum.RESULT_OK,"删除成功");
        }
        return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE,"删除失败");
    }

    /**
     * 通过主键删除数据
     *
     * @param friendId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long friendId) {
        return this.chatFriendDao.deleteById(friendId) > 0;
    }

    @Override
    public Long updateFriendRemarks(Long id, Long userId, String remarks) {
        return chatFriendDao.updateByRemarks(id,userId,remarks,new Date());

    }
}