package com.yj.im.project.service.impl;

import com.yj.im.project.dao.ChatGroupDao;
import com.yj.im.project.dao.ChatUserGroupDao;
import com.yj.im.project.dao.SysUserDao;
import com.yj.im.project.entity.ChatGroup;
import com.yj.im.project.entity.ChatUserGroup;
import com.yj.im.project.entity.SysUser;
import com.yj.im.project.service.ChatGroupService;
import com.yj.im.project.util.CommonResult;
import com.yj.im.project.util.RedisOperator;
import com.yj.im.project.util.contants.RedisKeyConstants;
import com.yj.im.project.util.contants.ResultConstantsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 群组基础表(ChatGroup)表服务实现类
 *
 * @author makejava
 * @since 2020-04-13 18:02:42
 */
@Service("chatGroupService")
public class ChatGroupServiceImpl implements ChatGroupService {
    @Autowired
    private ChatGroupDao chatGroupDao;

    @Autowired
    private ChatUserGroupDao chatUserGroupDao;

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ChatGroup queryById(Long id) {
        return this.chatGroupDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ChatGroup> queryAllByLimit(int offset, int limit) {
        return this.chatGroupDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过用户ID查询所有群
     *
     * @param userId 用户id
     * @return 对象列表
     */
    @Override
    public Object queryAllGroup(Long userId) {
        if (this.chatGroupDao.queryAllGroup(userId).size() > 0) {
            return new CommonResult(ResultConstantsEnum.RESULT_OK, "查询成功", this.chatGroupDao.queryAllGroup(userId));
        }
        return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE, "未查询到");
    }

    /**
     * 新增数据
     * @param chatGroup 实例对象
     * @return 实例对象
     */
    @Override
    public ChatGroup insert(ChatGroup chatGroup) {
        this.chatGroupDao.insert(chatGroup);
        return chatGroup;
    }

    /**
     * 修改数据
     * @param chatGroup 实例对象
     * @return 实例对象
     */
    @Override
    public ChatGroup update(ChatGroup chatGroup) {
        this.chatGroupDao.update(chatGroup);
        return this.queryById(chatGroup.getId());
    }

    /**
     * 通过主键删除数据
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.chatGroupDao.deleteById(id) > 0;
    }

    /**
     * 通过群id修改群名
     * @param groupName   群名称
     * @param id          群组id
     * @return 影响行数
     */
    @Override
    public Object updateGroupName(String groupName, Long id) {
        if (this.chatGroupDao.updateGroupName(groupName, id) > 0) {
            return new CommonResult(ResultConstantsEnum.RESULT_OK, "修改成功");
        }
        return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE, "修改失败");
    }

    /**
     * 判断用户是否为群主
     * @param id                群id
     * @param groupCreateUserId 用户id
     * @return 影响行数
     */
    @Override
    public boolean check(Long id, Long groupCreateUserId) {
        if (this.chatGroupDao.checkUser(id, groupCreateUserId) != null) {
            return true;
        }
        return false;
    }

    /**
     * 群主选择好友建群
     * @param ids 群主选择的好友们的id
     * @param createUserId 群主id
     * @param nickName 群主的昵称
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult createGroupChatByIds(List<Long> ids, Long createUserId, String nickName) {
        ChatGroup chatGroup = new ChatGroup()
                .setGroupCreateUserId(createUserId)
                .setCreateTime(new Date())
                .setGroupName(nickName + "的群")
                .setStatus(1);
        //创建群，插入群对象
        Long num = chatGroupDao.insertNewOne(chatGroup);

        //待插入的对象集合
        ArrayList<ChatUserGroup> userGroupsList = new ArrayList<>();
        //将创建人id加入到用户id集合中
        ids.add(createUserId);
        List<SysUser> sysUserList = sysUserDao.queryChooseUserDetail(ids);
        for (SysUser sysUser : sysUserList) {
            ChatUserGroup chatUserGroup = new ChatUserGroup()
                    .setUserId(sysUser.getId())
                    .setGroupId(chatGroup.getId())
                    .setCreateTime(new Date())
                    .setStatus(1)
                    .setUserGroupRemarks(sysUser.getName());
                userGroupsList.add(chatUserGroup);
        }
        int count = chatUserGroupDao.batchInsert(userGroupsList);
        //用set存放用户id
        Set set = new HashSet(ids);
        //群id为key，群成员id集合为value，放入redis
        Map o = (Map) redisOperator.get(RedisKeyConstants.IM_GROUP_ID + chatGroup.getId());
        if (count > 0 && num > 0) {
            if (o == null) {
                redisOperator.set(RedisKeyConstants.IM_GROUP_ID + chatGroup.getId(), set);
            }
        }
        if (num > 0) {
            return new CommonResult(ResultConstantsEnum.RESULT_OK, "操作成功", chatGroup.getId());
        }
        return new CommonResult(ResultConstantsEnum.ERROR_NO_DATA, "error");
    }
}