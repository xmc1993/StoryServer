package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.Review;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReviewDao {
    int deleteById(Integer id);

    int insert(Review record);

    Review selectById(Integer id);

    List<Review> selectReviewListByWorkId(@Param("workId") Integer workId);

    List<Review> selectReviewListByParentId(@Param("workId") Integer workId, @Param("parentId") Integer parentId);

    int updateById(Review record);
}