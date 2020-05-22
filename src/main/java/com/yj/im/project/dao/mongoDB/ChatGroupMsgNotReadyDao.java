package com.yj.im.project.dao.mongoDB;

import com.yj.im.project.dao.mongoDB.baseDao.MongoDbDao;
import com.yj.im.project.entity.mongoEntity.ChatGroupMsgNotReady;
import org.springframework.stereotype.Repository;

@Repository
public class ChatGroupMsgNotReadyDao extends MongoDbDao<ChatGroupMsgNotReady> {

    @Override
    protected Class<ChatGroupMsgNotReady> getEntityClass() {
        return ChatGroupMsgNotReady.class;
    }
}
