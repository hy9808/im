package com.yj.im.project.service.impl;

import com.yj.im.project.entity.ChatSysMsg;
import com.yj.im.project.dao.ChatSysMsgDao;
import com.yj.im.project.service.ChatSysMsgService;
import com.yj.im.project.util.RedisOperator;
import com.yj.im.project.util.RedisSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统级消息记录(ChatSysMsg)表服务实现类
 *
 * @author makejava
 * @since 2020-04-11 11:16:17
 */
@Service("chatSysMsgService")
public class ChatSysMsgServiceImpl implements ChatSysMsgService {
    @Resource
    private ChatSysMsgDao chatSysMsgDao;

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ChatSysMsg queryById(Integer id) {
        return this.chatSysMsgDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ChatSysMsg> queryAllByLimit(int offset, int limit) {
        return this.chatSysMsgDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param chatSysMsg 实例对象
     * @return 实例对象
     */
    @Override
    public ChatSysMsg insert(ChatSysMsg chatSysMsg) {
        this.chatSysMsgDao.insert(chatSysMsg);
        return chatSysMsg;
    }

    /**
     * 修改数据
     *
     * @param chatSysMsg 实例对象
     * @return 实例对象
     */
    @Override
    public ChatSysMsg update(ChatSysMsg chatSysMsg) {
        this.chatSysMsgDao.update(chatSysMsg);
        return this.queryById(chatSysMsg.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.chatSysMsgDao.deleteById(id) > 0;
    }


    @Override
    public List<ChatSysMsg> queryGroupBySysType() {
        return this.chatSysMsgDao.queryGroupBySysType();
    }

    @Override
    public List<ChatSysMsg> queryGroupByMsg() {
        List<ChatSysMsg> cst = queryGroupBySysType();
        List<ChatSysMsg> csm = this.chatSysMsgDao.queryGroupByMsg();
        for (ChatSysMsg ct : cst) {
            for (ChatSysMsg cm : csm) {
                if (ct.getSysMsgType().equals(cm.getSysMsgType())) {
                    ct.setMessage(cm.getMessage());
                }
                ct.setCreateTime(cm.getCreateTime());
            }
        }
        return cst;
    }

    @Override
    public int selectByTypeCount(Integer smt) {
        Object smtCount = redisOperator.get("smtCount");
        if (smtCount == null) {
            int i = this.chatSysMsgDao.selectByTypeCount(smt);
            redisOperator.setForTimeMS("smtCount", i, RedisSessionUtil.SESSION_EXPIRE_SECONDS);
            return i;
        }
        return (int) smtCount;
    }
}