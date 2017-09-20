package cn.edu.nju.software.feed.service.impl;

import cn.edu.nju.software.dao.FeedDao;
import cn.edu.nju.software.entity.feed.Feed;
import cn.edu.nju.software.entity.feed.MessageType;
import cn.edu.nju.software.feed.service.MessageFeedService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/9/19.
 */
public class MessageFeedServiceImpl implements MessageFeedService{
    @Autowired
    private FeedDao feedDao;

    @Override
    public List<Feed> getFeedsByPage(int userId, int page, int pageSize, MessageType type) {
        int offset = page*pageSize;
        int limit = pageSize;
        return feedDao.getFeedsByPage(userId, offset, limit, type);
    }

    @Override
    public List<Feed> getFeedsByPageByTimestamp(Date lastPullTime, int userId, int limit, MessageType type) {
        return feedDao.getFeedsByPageByTimestamp(lastPullTime, userId, limit, type);
    }

    @Override
    public Feed feed(Feed feed) {
        if (feedDao.saveFeed(feed)) {
            return feed;
        }
        return null;
    }

    @Override
    public boolean feed(Feed feed, List<Integer> idList) {
        //TODO 转NoSql异步处理
        for (Integer tid : idList) {
            feed.setTid(tid);
            feed.setTid(tid);
            feedDao.saveFeed(feed);
        }
        return true;
    }

    @Override
    public boolean deleteFeed(int id) {
        return feedDao.deleteFeed(id);
    }
}
