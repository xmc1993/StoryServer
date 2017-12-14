package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.CommentLikeRelationMapper;
import cn.edu.nju.software.dao.CommentMapper;
import cn.edu.nju.software.entity.Comment;
import cn.edu.nju.software.entity.CommentLikeRelation;
import cn.edu.nju.software.entity.CommentLikeRelationExample;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public boolean addCream(int id) {
        return commentMapper.addCream(id);
    }

    @Override
    public boolean deleteCream(int id) {
        return commentMapper.deleteCream(id);
    }

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

    @Override
    public ResponseData<List<Comment>> getCommentsByAmbitusId(int ambitusId,int page, int pageSize) {
        ResponseData<List<Comment>> responseData=new ResponseData<>();
        int offset = page * pageSize;
        int limit = pageSize;
        responseData.jsonFill(1,null,commentMapper.getCommentsByAmbitusId(ambitusId,offset, limit));
        return responseData;
    }

    @Override
    public ResponseData<List<Comment>> getCommentsWithSensitiveByAmbitusId(int ambitusId, int page, int pageSize) {
        ResponseData<List<Comment>> responseData=new ResponseData<>();
        int offset = page * pageSize;
        int limit = pageSize;
        responseData.jsonFill(1,null,commentMapper.getCommentsWithSensitiveByAmbitusId(ambitusId,offset, limit));
        return responseData;
    }

    @Override
    public List<Integer> getAllLikeByUserId(Integer userId) {
        return commentLikeRelationMapper.getAllLikeByUserId(userId);
    }

    @Override
    public Boolean releaseComment(int id) {
        return commentMapper.releaseComment(id);
    }
}
