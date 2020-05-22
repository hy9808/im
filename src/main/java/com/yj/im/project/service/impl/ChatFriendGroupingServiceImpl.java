package com.yj.im.project.service.impl;

import com.yj.im.project.entity.ChatFriendGrouping;
import com.yj.im.project.dao.ChatFriendGroupingDao;
import com.yj.im.project.service.ChatFriendGroupingService;
import com.yj.im.project.util.CommonResult;
import com.yj.im.project.util.SortUtil;
import com.yj.im.project.util.contants.ResultConstantsEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (ChatFriendGrouping)表服务实现类
 *
 * @author makejava
 * @since 2020-04-07 09:38:50
 */
@Service
public class ChatFriendGroupingServiceImpl implements ChatFriendGroupingService {
    @Resource
    private ChatFriendGroupingDao chatFriendGroupingDao;

    /**
     * 通过ID查询单条数据
     *
     * @param friendGroupId 主键
     * @return 实例对象
     */
    @Override
    public ChatFriendGrouping queryById(Long friendGroupId) {
        return this.chatFriendGroupingDao.queryById(friendGroupId);
    }

    /**
     * 通过用户ID查询所有分组
     *
     * @param userId 用户id
     * @return 对象集合
     */
    @Override
    public List<ChatFriendGrouping> queryAllById(Long userId) {
        return SortUtil.sortGroup(this.chatFriendGroupingDao.queryAllById(userId));
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ChatFriendGrouping> queryAllByLimit(int offset, int limit) {
        return this.chatFriendGroupingDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param chatFriendGrouping 实例对象
     * @return 实例对象
     */
    @Override
    public ChatFriendGrouping insert(ChatFriendGrouping chatFriendGrouping) {
        this.chatFriendGroupingDao.insert(chatFriendGrouping);
        return chatFriendGrouping;
    }

    /**
     * 新增用户分组
     *
     * @param userId    用户id
     * @param groupName 分组名
     * @return 实例对象
     */
    @Override
    public Object addGroup(Long userId, String groupName) {
        if(this.chatFriendGroupingDao.addGroup(userId,groupName,new Date(),new Date(),1)>0){
            return new CommonResult(ResultConstantsEnum.RESULT_OK,"添加分组成功");
        }
        return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE,"添加失败");
    }

    /**
     * 新增用户分组
     *
     * @param userId 用户id
     * @param groupName 分组名
     * @return 实例对象
    @Override
    public Object addGroup() {
        if(this.chatFriendGroupingDao.addGroup()>0){
            return new CommonResult(ResultConstantsEnum.RESULT_OK,"添加分组成功");
        }
        return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE,"添加失败");
    }*/

    /**
     * 修改数据
     *
     * @param chatFriendGrouping 实例对象
     * @return 实例对象
     */
    @Override
    public ChatFriendGrouping update(ChatFriendGrouping chatFriendGrouping) {
        this.chatFriendGroupingDao.update(chatFriendGrouping);
        return this.queryById(chatFriendGrouping.getFriendGroupId());
    }

    /**
     * 修改分组名
     *
     * @param friendGroupId 分组id
     * @param groupName     分组名
     * @return 影响数
     */
    @Override
    public Object updateGroupName(int friendGroupId, String groupName) {
        if(this.chatFriendGroupingDao.updateGroupName(groupName,friendGroupId,new Date())>0){
            return new CommonResult(ResultConstantsEnum.RESULT_OK,"修改成功");
        }
        return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE,"失败");
    }

    /**
     * 删除分组
     *
     * @param friendGroupId 分组id
     * @return 影响数
     */
    @Override
    public Object deleteGroup(int friendGroupId) {
       if(this.chatFriendGroupingDao.deleteGroup(friendGroupId,new Date())>0){
           return  new CommonResult(ResultConstantsEnum.RESULT_OK,"成功");
       }
        return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE,"失败");
    }

    /**
     * 通过主键删除数据
     *
     * @param friendGroupId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long friendGroupId) {
        return this.chatFriendGroupingDao.deleteById(friendGroupId) > 0;
    }
}