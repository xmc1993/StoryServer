package cn.edu.nju.software.feed.service.impl;

import cn.edu.nju.software.dao.FeedDao;
import cn.edu.nju.software.dto.MsgVo;
import cn.edu.nju.software.entity.Daily;
import cn.edu.nju.software.entity.Feed;
import cn.edu.nju.software.entity.SystemNotice;
import cn.edu.nju.software.entity.Works;
import cn.edu.nju.software.entity.feed.MessageType;
import cn.edu.nju.software.feed.service.MessageFeedService;
import cn.edu.nju.software.service.DailyService;
import cn.edu.nju.software.service.FollowService;
import cn.edu.nju.software.service.SystemNoticeService;
import cn.edu.nju.software.service.WorksService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/9/19.
 */
@Service
public class MessageFeedServiceImpl implements MessageFeedService{
    @Autowired
    private FeedDao feedDao;
    @Autowired
    private FollowService followService;
    @Autowired
    private DailyService dailyService;
    @Autowired
    private WorksService worksService;
    @Autowired
    private SystemNoticeService systemNoticeService;

    @Override
    public List<Feed> getDisplayFeedsByPage(int userId, int page, int pageSize) {
        List<Feed> feeds = getFeedsByPage(userId, page, pageSize, null);
        transformFeedList(feeds);
        return feeds;
    }

    private void transformFeedList(List<Feed> feedList){
        for (Feed feed : feedList) {
            transformFeed(feed);
        }
    }

    private Feed transformFeed(Feed feed){
        Gson gson = new Gson();
        MsgVo msgVo = gson.fromJson(feed.getContent(), MsgVo.class);
        switch (feed.getType()){
            case NEW_DAILY:
                Daily daily = dailyService.getDailyById(feed.getMid());
                msgVo.setData(daily);
                break;
            case NEW_WORKS:
                Works works = worksService.getWorksById(feed.getMid());
                msgVo.setData(works);
                break;
            case SYSTEM_NOTICE:
                SystemNotice systemNotice = systemNoticeService.getSystemNoticeById(feed.getMid());
                msgVo.setData(systemNotice);
                break;
            default:
                return feed;
        }
        feed.setContent(gson.toJson(msgVo));
        return feed;
    }

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
            feedDao.saveFeed(feed);
        }
        return true;
    }

    @Override
    public boolean unfeed(int mid, List<Integer> idList) {
        return feedDao.deleteFeedByPatch(mid, idList);
    }

    @Override
    public boolean deleteFeed(int id) {
        return feedDao.deleteFeed(id);
    }

    @Override
    public boolean feedFollowers(Feed feed, Integer sender, boolean includeSelf) {
        List<Integer> userFollowerList = followService.getUserFollowerList(sender, 0, 9999999);
        //把自己添加进去
        if (includeSelf) {
            userFollowerList.add(sender);
        }
        for (Integer tid : userFollowerList) {
            feed.setId(null);
            feed.setTid(tid);
            feed.setTid(tid);
            feedDao.saveFeed(feed);
        }
        return true;
    }

    @Override
    public boolean unfeedFollowers(Integer mid, Integer sender, boolean includeSelf) {
        List<Integer> userFollowerList = followService.getUserFollowerList(sender, 0, 9999999);
        //把自己添加进去
        if (includeSelf) {
            userFollowerList.add(sender);
        }
        return feedDao.deleteFeedByPatch(mid, userFollowerList);
    }

}