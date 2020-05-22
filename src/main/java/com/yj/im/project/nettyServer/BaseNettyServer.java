package com.yj.im.project.nettyServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseNettyServer {

    private final static Logger log = LoggerFactory.getLogger(HearBeatHandler.class);

    public static Logger nettyLog(){
        return log;
    }

}
