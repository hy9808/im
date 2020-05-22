package com.yj.im.project.service;

import com.yj.im.project.entity.UserDatingMessage;
import java.util.List;

/**
 * 交友信息表(UserDatingMessage)表服务接口
 *
 * @author makejava
 * @since 2020-04-07 10:42:59
 */
public interface UserDatingMessageService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserDatingMessage queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserDatingMessage> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param userDatingMessage 实例对象
     * @return 实例对象
     */
    UserDatingMessage insert(UserDatingMessage userDatingMessage);

    /**
     * 修改数据
     *
     * @param userDatingMessage 实例对象
     * @return 实例对象
     */
    UserDatingMessage update(UserDatingMessage userDatingMessage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}