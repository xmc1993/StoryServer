package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.ReviewDao;
import cn.edu.nju.software.dao.WorksDao;
import cn.edu.nju.software.entity.Review;
import cn.edu.nju.software.entity.Works;
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
        if(content==null||content.trim().equals("")) return false;
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
        if(content==null||content.trim().equals("")) return false;
        if(content==review.getContent()) return false;
        review.setContent(content);
        review.setUpdateTime(new Date());
        return reviewDao.updateById(review);
    }

    @Override
    public boolean deleteByWorkAuthor(int[] reviewIdList, int userId){
        for(int reviewId:reviewIdList) {
            Review review = reviewDao.getReviewById(reviewId);
            if (review == null) return false;
            if (review.getParentId() == 0) {
                boolean temp=this.deleteReviewByWorkAuthor(reviewId, userId);
                if(temp==false) return false;
            } else {
                boolean temp=this.deleteSubReviewByWorkAuthor(reviewId, userId);
                if(temp==false) return false;
            }
        }
        return true;
    }
    @Override
    public boolean deleteByUser(int[] reviewIdList,int userId){
        for(int reviewId:reviewIdList) {
            Review review = reviewDao.getReviewById(reviewId);
            if (review == null) return false;
            if (review.getParentId() == 0) {
                boolean temp = this.deleteReviewByUser(reviewId, userId);
                if(temp==false) return false;
            } else if (userId == review.getFromUserId()) {
                boolean temp = this.deleteSubReviewByUser(reviewId, userId);
                if(temp==false) return false;
            } else {
                boolean temp = this.deleteSubReviewByParentUser(reviewId, userId);
                if(temp==false) return false;
            }
        }
        return true;
    }
    @Override
    public boolean deleteReviewByWorkAuthor(int reviewId,int userId){
        Review review = reviewDao.getReviewById(reviewId);
        if(review==null) return false;
        Works work = worksDao.getWorksById(review.getWorkId());
        int workAuthorId = work.getUserId();
        if(workAuthorId!=userId) return false;
        List<Integer> reviewIdList = reviewDao.getReviewIdListByParentId(review.getWorkId(),reviewId);
        int delReviewCount=reviewIdList.size()+1;
        reviewDao.deleteByIdList(reviewIdList);
        reviewDao.deleteById(reviewId);
        return worksDao.setDelReviewCount(work.getId(),delReviewCount);
    }

    @Override
    public boolean deleteSubReviewByWorkAuthor(int reviewId, int userId){
        Review review = reviewDao.getReviewById(reviewId);
        if(review==null) return false;
        Works work = worksDao.getWorksById(review.getWorkId());
        Integer workAuthorId =work.getUserId();
        if(workAuthorId!=userId) return false;
        worksDao.delReviewCount(work.getId());
        return reviewDao.deleteById(reviewId);
    }


    @Override
    public boolean deleteReviewByUser(int reviewId,int userId){
        Review review = reviewDao.getReviewById(reviewId);
        if(review==null) return false;
        if(review.getFromUserId()!=userId) return false;
        List<Integer> reviewIdList = reviewDao.getReviewIdListByParentId(review.getWorkId(),reviewId);
        int delReviewCount=reviewIdList.size()+1;
        reviewDao.deleteByIdList(reviewIdList);
        worksDao.setDelReviewCount(review.getWorkId(),delReviewCount);
        return reviewDao.deleteById(reviewId);
    }
    @Override
    public boolean deleteSubReviewByParentUser(int reviewId, int userId){
        Review review = reviewDao.getReviewById(reviewId);
        if(review==null) return false;
        Review parentReview = reviewDao.getReviewById(review.getParentId());
        if(parentReview.getFromUserId()!=userId) return false;
        worksDao.delReviewCount(review.getWorkId());
        return reviewDao.deleteById(reviewId);
    }

    @Override
    public boolean deleteSubReviewByUser(int reviewId, int userId){
        Review review = reviewDao.getReviewById(reviewId);
        if(review==null) return false;
        if(review.getFromUserId()!=userId) return false;
        worksDao.delReviewCount(review.getWorkId());
        return reviewDao.deleteById(reviewId);
    }
}
