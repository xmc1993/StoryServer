package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Review;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Kt on 2017/6/22.
 */
public interface ReviewService {
    boolean insertReview(int workId, Integer parentId, int fromUserId,
                         Integer toUserId, String content);

    List<Review> getReviewListByWorkId(int workId, int offset, int limit);

    boolean updateReview(int reviewId, int fromUserId, String content);

    boolean deleteReviewByWorkAuthor(int reviewId, int userId);

    //TODO 用户删除自己的一级评论
    boolean deleteReviewByUser(int reviewId, int userId);
}
