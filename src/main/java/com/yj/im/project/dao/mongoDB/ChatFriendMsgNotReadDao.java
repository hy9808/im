package com.yj.im.project.dao.mongoDB;

import com.yj.im.project.dao.mongoDB.baseDao.MongoDbDao;
import com.yj.im.project.entity.mongoEntity.ChatMyFriendMsgNotRead;
import org.springframework.stereotype.Repository;

@Repository
public class ChatFriendMsgNotReadDao extends MongoDbDao<ChatMyFriendMsgNotRead> {

    @Override
    protected Class<ChatMyFriendMsgNotRead> getEntityClass() {
        return ChatMyFriendMsgNotRead.class;
    }
}
