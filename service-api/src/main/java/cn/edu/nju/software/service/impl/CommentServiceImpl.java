package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.CommentLikeRelationMapper;
import cn.edu.nju.software.dao.CommentMapper;
import cn.edu.nju.software.entity.Comment;
import cn.edu.nju.software.entity.CommentLikeRelation;
import cn.edu.nju.software.entity.CommentLikeRelationExample;
import cn.edu.nju.software.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangsong on 2017/12/11.
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentLikeRelationMapper commentLikeRelationMapper;

    @Override
    public Integer saveComment(Comment comment) {
        return commentMapper.insert(comment);
    }

    @Override
    public Integer deleteComment(int id) {
        return commentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Boolean newLike(int id, int userId) {
        //先进行点赞记录
        CommentLikeRelation commentLikeRelation = new CommentLikeRelation();
        commentLikeRelation.setCommentId(id);
        commentLikeRelation.setUserId(userId);
        commentLikeRelation.setLike(true);
        int res = commentLikeRelationMapper.insert(commentLikeRelation);
        if (res == 1) {
            return commentMapper.newLike(id);
        }
        return false;
    }


    @Override
    public Boolean deleteLike(int id, int userId) {
        CommentLikeRelationExample commentLikeRelationExample = new CommentLikeRelationExample();
        //通过criteria构造查询条件
        CommentLikeRelationExample.Criteria criteria = commentLikeRelationExample.createCriteria();
        criteria.andUseridEqualTo(userId);
        criteria.andCommentidEqualTo(id);
        int res = commentLikeRelationMapper.deleteByExample(commentLikeRelationExample);
        if (res == 1) {
            return commentMapper.deleteLike(id);
        }
        return false;
    }


}
