package com.yj.im.project.util.RSA;

import org.joda.time.LocalDateTime;

import java.util.Date;


public class TimeUtils {

    public static Date addTime(LocalDateTime date, int seconds) {
        seconds = seconds * 60 * 1000;
        date.plusSeconds(seconds);
        return date.toDate();
    }
}
