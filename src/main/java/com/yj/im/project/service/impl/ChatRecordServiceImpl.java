package com.yj.im.project.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yj.im.project.dao.ChatSysReadDao;
import com.yj.im.project.dao.mongoDB.ChatFriendMsgNotReadDao;
import com.yj.im.project.dao.mongoDB.ChatGroupMsgNotReadyDao;
import com.yj.im.project.entity.ChatRecord;
import com.yj.im.project.dao.ChatRecordDao;
import com.yj.im.project.entity.ChatSysMsg;
import com.yj.im.project.entity.ChatSysRead;
import com.yj.im.project.entity.mongoEntity.ChatGroupMsgNotReady;
import com.yj.im.project.entity.mongoEntity.ChatMyFriendMsgNotRead;
import com.yj.im.project.entity.pojo.ChatRecordMsgListVo;
import com.yj.im.project.service.ChatRecordService;
import com.yj.im.project.service.ChatSysMsgService;
import com.yj.im.project.service.ChatSysReadService;
import com.yj.im.project.util.RedisOperator;
import com.yj.im.project.util.RedisSessionUtil;
import com.yj.im.project.util.contants.RedisKeyConstants;
import com.yj.im.project.util.contants.ResultConstants;
import com.yj.im.project.util.contants.ResultConstantsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聊天记录表(ChatRecord)表服务实现类
 *
 * @author makejava
 * @since 2020-04-07 16:14:52
 */
@Service
public class ChatRecordServiceImpl implements ChatRecordService {

    private final static Logger log = LoggerFactory.getLogger(ChatRecordService.class);

    @Resource
    private ChatRecordDao chatRecordDao;

    @Autowired
    private Environment env;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private ChatSysMsgService chatSysMsgService;

    @Autowired
    private ChatSysReadService chatSysReadService;

    @Autowired
    private ChatSysReadDao chatSysReadDao;

    @Autowired
    private ChatFriendMsgNotReadDao chatFriendMsgNotReadDao;

    @Autowired
    private ChatGroupMsgNotReadyDao chatGroupMsgNotReadyDao;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ChatRecord queryById(Long id) {
        return this.chatRecordDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ChatRecord> queryAllByLimit(int offset, int limit) {
        return this.chatRecordDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param chatRecord 实例对象
     * @return 实例对象
     */
    @Override
    public ChatRecord insert(ChatRecord chatRecord) {
        this.chatRecordDao.insert(chatRecord);
        return chatRecord;
    }

    /**
     * 修改数据
     *
     * @param chatRecord 实例对象
     * @return 实例对象
     */
    @Override
    public ChatRecord update(ChatRecord chatRecord) {
        this.chatRecordDao.update(chatRecord);
        return this.queryById(chatRecord.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.chatRecordDao.deleteById(id) > 0;
    }


    /**
     * 通过主键删除数据
     *
     * @param chatRecord 主键
     * @return 聊天列表
     */
    @Override
    public Object queryAllByEntity(ChatRecord chatRecord) {
        return this.chatRecordDao.queryAll(chatRecord);
    }

    @Override
    public List<ChatRecord> selectByUserIdAndRId(Long userId, Long recipientId) {
        return this.chatRecordDao.selectByUserIdAndRId(userId, recipientId);
    }

    /**
     * 通过用户id和好友id修改数据
     *
     * @return
     */
    @Override
    public int updateByUserAndRecordId(ChatRecord chatRecord) {
        return this.chatRecordDao.updateByUserAndRecordId(chatRecord);
    }


    @Override
    public List<ChatRecordMsgListVo> selectByIdGetNotReadCount(List ids, Long userId) {
        return this.chatRecordDao.selectByIdGetNotReadCount(ids, userId);
    }

    @Override
    public Map selectByIdGetNotReadMsg(Long userId) {
//        List<ChatRecordMsgListVo> counts = selectByIdGetNotReadCount(ids, userId);
//        List<ChatRecordMsgListVo> ms = this.chatRecordDao.selectByIdGetNotReadMsg(ids, userId);
        HashMap<Object, Object> map = new HashMap<>();
//        //好友消息
//        for (ChatRecordMsgListVo c : counts) {
//            for (ChatRecordMsgListVo m : ms) {
//                if (m.getUserId().equals(c.getUserId())) {
//                    c.setMessage(m.getMessage());
//                }
//            }
//        }

        //系统消息
        List<ChatSysMsg> sysMsg = chatSysMsgService.queryGroupByMsg();
        //已读消息
        List<ChatSysRead> fMsg = chatSysReadService.queryByIdGroupReadType(userId);
        if (fMsg.size() == 0) {
            //用户没有数据
            for (ChatSysMsg sm : sysMsg) {
                this.chatSysReadDao.insert(new ChatSysRead()
                        .setUserId(userId)
                        .setReadType(sm.getSysMsgType())
                        .setReadCount(0));
            }
            selectByIdGetNotReadMsg(userId);
        }
        for (ChatSysMsg sm : sysMsg) {
            for (ChatSysRead fm : fMsg) {
                if (fm.getReadType().equals(sm.getSysMsgType())) {
                    sm.setCount(sm.getCount() - fm.getReadCount());
                    sm.setId(Integer.valueOf(fm.getId().toString()));
                }
            }
        }
        map.put("sys", sysMsg);
//        map.put("friend", counts);
        return map;
    }


    /**
     * 通过Mq异步储存消息，降低数据库压力
     *
     * @param chatRecord
     */
    @Override
    public void insertToMq(ChatRecord chatRecord) {
        try {

            //消息队列
            Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(chatRecord)).build();
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.convertAndSend(env.getProperty("mq.second.queue.exchange"), env.getProperty("mq.second.queue.routingKey"), message);


            String redisKey = RedisKeyConstants.IM_CHAT_RECORD + chatRecord.getUserId() + chatRecord.getRecipientId();
            Object o = redisOperator.get(redisKey);
            List<ChatRecord> chatRecords = null;
            if (o != null) {
                chatRecords = (List<ChatRecord>) o;
                chatRecords.add(chatRecord);
                redisOperator.setForTimeMS(redisKey, chatRecords, RedisSessionUtil.SESSION_IM_CHAT_RECORD);
            } else {
                //无数据
                chatRecords = new ArrayList<>();
                chatRecords.add(chatRecord);
                redisOperator.setForTimeMS(redisKey, chatRecords, RedisSessionUtil.SESSION_IM_CHAT_RECORD);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("IM聊天Service类出现异常 异常消息 {}", e.getMessage());
        }
    }

    @Override
    public void chatGroupInsertMq(ChatRecord ChatRecord) {
        try {
            Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(ChatRecord)).build();
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.convertAndSend(env.getProperty("mq.customer.group.exchange"), env.getProperty("mq.customer.group.routingKey"), message);
            log.info("测试群Mq消息-----------> 进来了！！！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object updateSysMsgType(String id, Long readCount, Integer type) {
        if (type == 0 || type == 1) {
            //系统消息
            chatSysReadDao.updateSysMsgType(Long.valueOf(id.toString()), readCount);
        } else if (type == 2) {
            //好友消息
            ChatMyFriendMsgNotRead chatMyFriendMsgNotRead = chatFriendMsgNotReadDao.queryOne(new ChatMyFriendMsgNotRead().setId(id.toString()));
            chatFriendMsgNotReadDao.updateFirst(new ChatMyFriendMsgNotRead().setId(id.toString()), chatMyFriendMsgNotRead.setMsgNotReadyCount(0));

        } else {
            //群消息
            ChatGroupMsgNotReady chatGroupMsgNotReady = chatGroupMsgNotReadyDao.queryOne(new ChatGroupMsgNotReady().setId(id.toString()));
            chatGroupMsgNotReadyDao.updateFirst(new ChatGroupMsgNotReady().setId(id.toString()), chatGroupMsgNotReady.setMsgNotReadyCount(0));
        }
        return ResultConstantsEnum.RESULT_OK;
    }
}