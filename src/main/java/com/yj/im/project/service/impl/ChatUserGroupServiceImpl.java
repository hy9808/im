package com.yj.im.project.service.impl;

import com.yj.im.project.dao.ChatGroupDao;
import com.yj.im.project.dao.ChatUserGroupDao;
import com.yj.im.project.entity.ChatGroup;
import com.yj.im.project.entity.ChatUserGroup;
import com.yj.im.project.service.ChatUserGroupService;
import com.yj.im.project.util.CommonResult;
import com.yj.im.project.util.RedisOperator;
import com.yj.im.project.util.contants.RedisKeyConstants;
import com.yj.im.project.util.contants.ResultConstantsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 用户和群关联表(ChatUserGroup)表服务实现类
 *
 * @author makejava
 * @since 2020-04-13 18:03:07
 */
@Service("chatUserGroupService")
public class ChatUserGroupServiceImpl implements ChatUserGroupService {
    @Resource
    private ChatUserGroupDao chatUserGroupDao;

    @Autowired
    private ChatGroupDao chatGroupDao;

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ChatUserGroup queryById(Long id) {
        return this.chatUserGroupDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ChatUserGroup> queryAllByLimit(int offset, int limit) {
        return this.chatUserGroupDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param userId  用户id
     * @param groupId 群组id
     * @return 对象列表
     */
    @Override
    public CommonResult queryAllUser(Long userId, Long groupId) {
        if (chatUserGroupDao.queryAllUser(userId, groupId).size() > 0) {
            List<ChatUserGroup> userList = chatUserGroupDao.queryAllUser(userId, groupId);
            ChatGroup chatGroup = chatGroupDao.queryById(groupId);
            boolean flag = chatGroup.getGroupCreateUserId().equals(userId);
            Map<String, Object> map = new HashMap<>();
            map.put("userList",userList);
            map.put("chatGroup", chatGroup);
            map.put("flag", flag);
            return new CommonResult(ResultConstantsEnum.RESULT_OK, "查询成功", map);
        }
        return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE, "未查询到");
    }

    /**
     * 新增数据
     *
     * @param chatUserGroup 实例对象
     * @return 实例对象
     */
    @Override
    public ChatUserGroup insert(ChatUserGroup chatUserGroup) {
        this.chatUserGroupDao.insert(chatUserGroup);
        return chatUserGroup;
    }

    /**
     * 修改数据
     *
     * @param chatUserGroup 实例对象
     * @return 实例对象
     */
    @Override
    public ChatUserGroup update(ChatUserGroup chatUserGroup) {
        this.chatUserGroupDao.update(chatUserGroup);
        return this.queryById(chatUserGroup.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.chatUserGroupDao.deleteById(id) > 0;
    }

    /**
     * 通过群组id和用户id退出群聊
     *
     * @param userId  用户id
     * @param groupId 群组id
     * @return 影响行数
     */
    @Override
    public Object updateStatus(Long userId, Long groupId) {
        if (this.chatUserGroupDao.updateStatus(userId, groupId, new Date(), 0) > 0) {
            Set set = (Set) redisOperator.get(RedisKeyConstants.IM_GROUP_ID + groupId);
            if (set != null) {
                set.remove(userId);
                redisOperator.set(RedisKeyConstants.IM_GROUP_ID + groupId, set);
            }
            return new CommonResult(ResultConstantsEnum.RESULT_OK, "修改成功");
        }
        return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE, "错误");
    }

    /**
     * 通过扫描二维码进群
     *
     * @param chatUserGroup 实例对象
     * @return 影响行数
     */
    @Override
    public Object insertByCrCode(ChatUserGroup chatUserGroup) {
        chatUserGroup.setCreateTime(new Date());
        chatUserGroup.setUpdateTime(new Date());
        chatUserGroup.setStatus(1);
        //判断用户是否有过记录
        if (this.chatUserGroupDao.check(chatUserGroup.getUserId(), chatUserGroup.getGroupId()) != null) {
            //如果有录则判断用记户是否群成员
            if (this.chatUserGroupDao.check(chatUserGroup.getUserId(), chatUserGroup.getGroupId()).getStatus() == 1) {
                //用户是该群成员 返回群id
                return new CommonResult(ResultConstantsEnum.RESULT_OK, "该用户已是群成员", chatUserGroup.getGroupId());
            } else {
                if (this.chatUserGroupDao.updateStatus(chatUserGroup.getUserId(), chatUserGroup.getGroupId(), new Date(), chatUserGroup.getStatus()) > 0) {
                    return new CommonResult(ResultConstantsEnum.RESULT_OK, "重新加入群", chatUserGroup.getGroupId());
                } else {
                    return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE, "添加失败");
                }
            }
        } else {
            //用户无记录 不是该群成员
            if (this.chatUserGroupDao.insertByCrCode(chatUserGroup) > 0) {
                Set set = (Set) redisOperator.get(RedisKeyConstants.IM_GROUP_ID + chatUserGroup.getGroupId());
                if (set != null) {
                    set.add(chatUserGroup.getUserId());
                    redisOperator.set(RedisKeyConstants.IM_GROUP_ID + chatUserGroup.getGroupId(), set);
                }
                return new CommonResult(ResultConstantsEnum.RESULT_OK, "新加入群", chatUserGroup.getGroupId());
            }
            return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE, "添加失败");
        }
    }

    /**
     * 通过群组id和用户id判断用户是否群员
     *
     * @param userId  用户id
     * @param groupId 群组id
     * @return 状态
     */
    @Override
    public Object check(Long userId, Long groupId) {
        if (this.chatUserGroupDao.check(userId, groupId) != null) {
            return new CommonResult(ResultConstantsEnum.RESULT_OK, "是该群成员");
        }
        return new CommonResult(ResultConstantsEnum.ERROR_VERIFY_CODE, "不是该群成员");
    }

    /**
     * 修改群的个人备注
     *
     * @param groupId 群id
     * @param userId  用户id
     * @param remarks 备注
     * @return
     */
    @Override
    public Long rewriteRemarksById(Long groupId, Long userId, String remarks) {
        ChatUserGroup chatUserGroup = new ChatUserGroup()
                .setUserId(userId)
                .setUserGroupRemarks(remarks)
                .setGroupId(groupId)
                .setStatus(1)
                .setUpdateTime(new Date());
        Long count = chatUserGroupDao.updateRemarks(chatUserGroup);
        return count;
    }

    /**
     * 群主踢人
     *
     * @param userId  群主的id
     * @param id      被踢的用户id
     * @param groupId 群组id
     * @return
     */
    @Override
    public CommonResult kickManById(Long userId, Long id, Long groupId) {
        ChatUserGroup chatUserGroup = new ChatUserGroup()
                .setUserId(id)
                .setGroupId(groupId)
                .setStatus(0)
                .setUpdateTime(new Date());
        Map<String, Object> map = new HashMap<>();
        map.put("chatUserGroup", chatUserGroup);
        map.put("userId", userId);
        Long count = chatUserGroupDao.deleteManById(map);
        if (count > 0) {
            Set set = (Set) redisOperator.get(RedisKeyConstants.IM_GROUP_ID + groupId);
            if (set != null) {
                set.remove(id);
                redisOperator.set(RedisKeyConstants.IM_GROUP_ID + groupId, set);
            }
            return new CommonResult(ResultConstantsEnum.RESULT_OK, "操作成功", chatUserGroup.getGroupId());
        }
        return new CommonResult(ResultConstantsEnum.ERROR_NO_DATA, "页面繁忙，请稍后重试");
    }


}