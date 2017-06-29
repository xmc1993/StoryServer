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


    boolean deleteByWorkAuthor(int[] reviewIdList, int userId);

    boolean deleteByUser(int[] reviewIdList, int userId);

    boolean deleteReviewByWorkAuthor(int reviewId, int userId);

    boolean deleteSubReviewByWorkAuthor(int reviewId, int userId);


    boolean deleteReviewByUser(int reviewId, int userId);

    boolean deleteSubReviewByParentUser(int reviewId, int userId);

    boolean deleteSubReviewByUser(int reviewId, int userId);


    Review getReviewById(Integer id);

    Integer getReviewCountByWorkId(int workId);
}
