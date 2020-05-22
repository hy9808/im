package com.yj.im.project.util;

import java.util.Calendar;

public class RedisSessionUtil {
    public  static int SESSION_THE_MIN=3; //3秒
    public  static int SESSION_MIN=10; //10秒

    public static int SESSION_IM_CHAT_RECORD = 60;    //1分钟

    public static int SESSION_EXPIRE_SECONDS = 10800;   // 30 分钟

    //短信时间
    public static int SESSION_SMS_EXPIRE_SECONDS = 300; // 5 分钟

    public static int SESSION_SMS_WEEK = 259200;        // 15天

    //用户登录信息过期时间（60*60*24*30）
    public static int SESSION_USER_EXPIRE_SECONDS = 2592000;// 30天

    //在线过期时间
    public static int ONLINE_USER_SECONDS = 900;

    //下载页下载链接过期时间，7天
    public static int SESSION_URL_EXPIRE_SECONDS = 604800;

    //下载页广告过期时间，7天
    public static int SESSION_ADVERT_EXPIRE_SECONDS = 604800;

    public static int SESSION_IP_EXPIRE_SECONDS = 604800;//7天

    //距离当天凌晨的秒
    public static int leftDayTime() {
        Calendar c = Calendar.getInstance();
        long now = c.getTimeInMillis();
        c.add(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 0);
        long millis = c.getTimeInMillis() - now;
        int leftDayTime = (int) millis / 1000;
        return leftDayTime;
    }

}
