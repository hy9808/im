package com.yj.im.project.util;

import com.yj.im.project.entity.ChatFriend;
import com.yj.im.project.entity.ChatFriendGrouping;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
//对集合按字母进行排序
public class SortUtil {
        //对搜索出来的用户进行排序
        public static List<ChatFriend> sort(List<ChatFriend> lists) {
            Comparator<Object> com= Collator.getInstance(java.util.Locale.CHINA);
            Collections.sort(lists,(e1, e2)->{
                return com.compare(e1.getRemarks(), e2.getRemarks());
            });
            return lists;
        }
        //对用户分组进行排序
        public static List<ChatFriendGrouping> sortGroup(List<ChatFriendGrouping> lists) {
            Comparator<Object> com= Collator.getInstance(java.util.Locale.CHINA);
            Collections.sort(lists,(e1, e2)->{
                return com.compare(e1.getGroupName(), e2.getGroupName());
            });
            return lists;
        }
}
