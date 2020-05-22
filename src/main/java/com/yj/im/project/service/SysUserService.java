package com.yj.im.project.service;

import com.yj.im.project.entity.SysUser;
import com.yj.im.project.entity.pojo.ChatFriendVO;
import com.yj.im.project.entity.pojo.DiaDistoryDo;
import com.yj.im.project.util.CommonResult;

import java.util.List;
import java.util.Map;

/**
 * 用户基础信息表(SysUser)表服务接口
 *
 * @author makejava
 * @since 2020-04-06 12:04:07
 */
public interface SysUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysUser queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysUser> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    SysUser insert(SysUser sysUser);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    SysUser update(SysUser sysUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 通过用户账号查找用户（账号）
     * @param account 查找的账号
     * @return
     */
    CommonResult selectByAccount(String account);


    /**
     * 通过手机号查询所有用户
     * @param list 手机号
     * @return
     */
    List<ChatFriendVO> selectByPhoneList(List<String> list);


    /**
     * 通过手机号和用户id查询所有用户指定id
     *
     * @return
     */
    Map selectByUserIdAndPhoneList(Long userId, List<DiaDistoryDo> diaDistoryDos);

    /**
     * 查询用户是否存在
     * @param id
     * @return
     */
    int selectById(Long id);
}