package com.yj.im.project.dao;

import com.yj.im.project.entity.SysUser;
import com.yj.im.project.entity.pojo.ChatFriendVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户基础信息表(SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-06 12:04:07
 */
public interface SysUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysUser queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysUser 实例对象
     * @return 对象列表
     */
    List<SysUser> queryAll(SysUser sysUser);

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 影响行数
     */
    int insert(SysUser sysUser);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 影响行数
     */
    int update(SysUser sysUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 通过用户账号（手机、亿家号）查找用户
     *
     * @param account 手机、亿家号
     * @return
     */
    Long queryByAccount(String account);


    /**
     * 通过手机号查询所有用户
     * @param phones
     * @return
     */
    List<ChatFriendVO> selectByPhoneList(@Param("array") List<String> phones);

    /**
     * 通过手机号和用户id查询所有用户指定id
     * @param map
     * @return
     */
    List<Long> selectByUserIdAndPhoneList(Map map);

    /**
     * 查询用户id 及昵称
     * @param ids
     * @return
     */
    List<SysUser> queryChooseUserDetail(@Param("list") List<Long> ids);

    /**
     * 查询当前用户是否存在
     * @param id
     * @return
     */
    int queryExistUserById(Long id);
}