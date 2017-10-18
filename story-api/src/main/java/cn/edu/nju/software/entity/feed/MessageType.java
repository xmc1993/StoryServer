package cn.edu.nju.software.entity.feed;

/**
 * Created by xmc1993 on 2017/9/15.
 */
public enum MessageType {
    NEW_INVITE,//录制邀请
    NEW_DAILY,//关注的人日志更新
    NEW_WORKS,//关注的人作品更新
    NEW_FOLLOWER,//新的粉丝
    NEW_COMPLETE,//完成作品的回调通知
    SYSTEM_NOTICE,//系统通知
    NEW_BABYREAD,//关注的人有宝宝读
    NEW_FRIEND_STORY,//关注的人有新的原创故事
}
