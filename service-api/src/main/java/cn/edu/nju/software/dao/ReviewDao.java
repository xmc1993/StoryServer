package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.Review;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReviewDao {
    boolean deleteById(Integer id);

    boolean deleteByIdList(@Param("idList") List<Integer> idList);

    boolean saveReview(Review record);

    Review getReviewById(Integer id);

    List<Review> getReviewListByWorkId(@Param("workId") Integer workId, @Param("offset") int offset, @Param("limit") int limit);

    List<Review> getReviewListByParentId(@Param("workId") Integer workId, @Param("parentId") Integer parentId);

    List<Integer> getReviewIdListByParentId(@Param("workId") Integer workId, @Param("parentId") Integer parentId);

    boolean updateById(Review record);

    boolean addSubCommentCount(@Param("workId") Integer workId, @Param("id") Integer id);

    boolean delSubCommentCount(@Param("workId") Integer workId, @Param("id") Integer id);

    List<Integer> canDeleteByWorkAuthor(List<Integer> idList, Integer userId);
}