package com.yj.im.project.service.impl;

import com.yj.im.project.entity.UserDatingMessage;
import com.yj.im.project.dao.UserDatingMessageDao;
import com.yj.im.project.service.UserDatingMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 交友信息表(UserDatingMessage)表服务实现类
 *
 * @author makejava
 * @since 2020-04-07 10:42:59
 */
@Service
public class UserDatingMessageServiceImpl implements UserDatingMessageService {
    @Resource
    private UserDatingMessageDao userDatingMessageDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserDatingMessage queryById(Long id) {
        return this.userDatingMessageDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<UserDatingMessage> queryAllByLimit(int offset, int limit) {
        return this.userDatingMessageDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param userDatingMessage 实例对象
     * @return 实例对象
     */
    @Override
    public UserDatingMessage insert(UserDatingMessage userDatingMessage) {
        this.userDatingMessageDao.insert(userDatingMessage);
        return userDatingMessage;
    }

    /**
     * 修改数据
     *
     * @param userDatingMessage 实例对象
     * @return 实例对象
     */
    @Override
    public UserDatingMessage update(UserDatingMessage userDatingMessage) {
        this.userDatingMessageDao.update(userDatingMessage);
        return this.queryById(userDatingMessage.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.userDatingMessageDao.deleteById(id) > 0;
    }
}