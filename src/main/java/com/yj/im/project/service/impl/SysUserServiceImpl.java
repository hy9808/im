package com.yj.im.project.service.impl;

import com.yj.im.project.dao.ChatFriendDao;
import com.yj.im.project.dao.SysUserDao;
import com.yj.im.project.entity.SysUser;
import com.yj.im.project.entity.pojo.ChatFriendVO;
import com.yj.im.project.entity.pojo.DiaDistoryDo;
import com.yj.im.project.service.SysUserService;
import com.yj.im.project.util.CommonResult;
import com.yj.im.project.util.contants.ResultConstantsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 用户基础信息表(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2020-04-06 12:04:07
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserDao sysUserDao;

    @Autowired
    private ChatFriendDao chatFriendDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysUser queryById(Long id) {
        return this.sysUserDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SysUser> queryAllByLimit(int offset, int limit) {
        return this.sysUserDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public SysUser insert(SysUser sysUser) {
        this.sysUserDao.insert(sysUser);
        return sysUser;
    }

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public SysUser update(SysUser sysUser) {
        this.sysUserDao.update(sysUser);
        return this.queryById(sysUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.sysUserDao.deleteById(id) > 0;
    }

    @Override
    public CommonResult selectByAccount(String account) {
        Map<String, Object> map = new HashMap<>();
        Long userId = sysUserDao.queryByAccount(account);
        if (userId > 0) {
            return new CommonResult(ResultConstantsEnum.RESULT_OK, "success", userId);
        }
        return new CommonResult(ResultConstantsEnum.ERROR_NO_DATA, "error");
    }

    @Override
    public List<ChatFriendVO> selectByPhoneList(List<String> list) {
        return this.sysUserDao.selectByPhoneList(list);
    }

    @Override
    public Map selectByUserIdAndPhoneList(Long userId, List<DiaDistoryDo> list) {
        HashMap<Object, Object> map = new HashMap<>();
        List<String> phone = new ArrayList<>();
        for (DiaDistoryDo aDo : list) {
           phone.add( aDo.getPhone());
        }
        map.put("userId", userId);
        map.put("phones", phone);

        List<Long> aLong = this.sysUserDao.selectByUserIdAndPhoneList(map);
        List<ChatFriendVO> cf = selectByPhoneList(phone);
        Iterator<DiaDistoryDo> iterator = list.iterator();

        map.clear();
        for (ChatFriendVO c : cf) {
            for (int i = 0; i < aLong.size(); i++) {
                if (c.getUserId().equals(aLong.get(i))) {
                    //存在好友
                    c.setFlag(1);
                } else {
                    //没有好友,设置备注名为手机联系人备注
                    while (iterator.hasNext()) {
                        c.setRemarks(iterator.next().getName());
                    }
                }
            }
        }
        map.put("friendList", cf);
        return map;
    }

    /**
     * 查询用户是否存在
     * @param id
     * @return
     */
    @Override
    public int selectById(Long id) {
        return sysUserDao.queryExistUserById(id);
    }
}
