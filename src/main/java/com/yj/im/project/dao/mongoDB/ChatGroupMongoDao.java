package com.yj.im.project.dao.mongoDB;

import com.yj.im.project.dao.mongoDB.baseDao.MongoDbDao;
import com.yj.im.project.entity.mongoEntity.ChatGroupMessage;
import org.springframework.stereotype.Repository;

@Repository
public class ChatGroupMongoDao extends MongoDbDao<ChatGroupMessage> {

    @Override
    protected Class<ChatGroupMessage> getEntityClass() {
        return ChatGroupMessage.class;
    }
}
