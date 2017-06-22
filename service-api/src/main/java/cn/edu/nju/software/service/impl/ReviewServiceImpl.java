package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.ReviewDao;
import cn.edu.nju.software.dao.WorksDao;
import cn.edu.nju.software.entity.Review;
import cn.edu.nju.software.service.ReviewService;
import cn.edu.nju.software.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Kt on 2017/6/22.
 */
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewDao reviewDao;
    @Autowired
    private WorksDao worksDao;
    @Override
    public boolean insertReview(int workId, Integer parentId, int fromUserId,
                                Integer toUserId, String content){
        Review review= new Review(workId,fromUserId,content);
        if(parentId!=null) {
            reviewDao.addSubCommentCount(workId,parentId);
        }
        review.setParentId(parentId);
        review.setToUserId(toUserId);
        Date date = new Date();
        review.setCreateTime(date);
        review.setUpdateTime(date);
        if(reviewDao.saveReview(review)) return false;
        if(worksDao.addReviewCount(workId)) return false;
        return true;
    }
    @Override
    public List<Review> getReviewListByWorkId(int workId, int offset, int limit){
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        return reviewDao.getReviewListByWorkId(workId,offset,limit);
    }

    @Override
    public boolean updateReview(int reviewId, int fromUserId, String content){
        Review review = reviewDao.getReviewById(reviewId);
        if(review.getFromUserId()==fromUserId) return false;
        if(content==null||content.trim()=="") return false;
        if(content==review.getContent()) return false;
        review.setContent(content);
        review.setUpdateTime(new Date());
        return reviewDao.updateById(review);
    }

    @Override
    public boolean deleteReviewByWorkAuthor(int reviewId, int userId){
        Review review = reviewDao.getReviewById(reviewId);
        if(review==null) return false;
        Integer workAuthorId = worksDao.getWorksById(review.getWorkId()).getUserId();
        if(workAuthorId!=userId) return false;
        return reviewDao.deleteById(reviewId);
    }

    //TODO 用户删除自己的一级评论
    @Override
    public boolean deleteReviewByUser(int reviewId,int userId){
        Review review = reviewDao.getReviewById(reviewId);
        if(review.getFromUserId()!=userId) return false;
        List<Review> reviewList = reviewDao.getReviewListByParentId(review.getWorkId(),reviewId);
        for(Review temp:reviewList){
            reviewDao.deleteById(temp.getId());
        }
        return reviewDao.deleteById(reviewId);
    }
    //TODO 用户删除自己的二级评论
    //TODO 用户删除自己的一级评论下的二级评论
}
