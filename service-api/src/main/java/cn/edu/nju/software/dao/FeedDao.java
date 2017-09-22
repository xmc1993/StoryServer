package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.feed.Feed;
import cn.edu.nju.software.entity.feed.MessageType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface FeedDao {

    boolean saveFeed(Feed feed);

    boolean deleteFeed(int id);

    Feed getFeedById(int id);

    boolean deleteFeedByPatch(int mid, @Param("idList")List<Integer> idList);

    List<Feed> getFeedsByPageByTimestamp(@Param("lastPullTime")Date lastPullTime, @Param("userId")int userId, @Param("limit")int limit, @Param("type")MessageType type);

    List<Feed> getFeedsByPage(@Param("userId")int userId, @Param("offset")int offset, @Param("limit")int limit, @Param("type")MessageType type);

}
