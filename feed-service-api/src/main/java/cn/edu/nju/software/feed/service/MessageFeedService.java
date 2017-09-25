package cn.edu.nju.software.feed.service;

import cn.edu.nju.software.entity.Feed;
import cn.edu.nju.software.entity.feed.MessageType;

import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/9/15.
 * 消息Feed的Service
 */
public interface MessageFeedService {


    List<Feed> getDisplayFeedsByPage(int userId, int page, int pageSize);

    /**
     * 从最新的feed开始分页查询
     * @param page
     * @param pageSize
     * @return
     */
    List<Feed> getFeedsByPage(int userId, int page, int pageSize, MessageType type);

    /**
     * 用于不断往下拉去消息的场景
     * @param lastPullTime
     * @param limit
     * @return
     */
    List<Feed> getFeedsByPageByTimestamp(Date lastPullTime, int userId, int limit, MessageType type);

    /**
     * feed
     * @param feed
     * @return
     */
    Feed feed(Feed feed);

    /**
     * 批量feed
     * @param feed
     * @param idList
     * @return
     */
    boolean feed(Feed feed, List<Integer> idList);

    /**
     * 删除feed
     * @param id
     * @return
     */
    boolean deleteFeed(int id);

    boolean feedFollowers(Feed feed, Integer sender, boolean includeSelf);

    boolean unfeedFollowers(Integer mid, Integer sender, boolean includeSelf);

}
