package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Comment;
import cn.edu.nju.software.entity.ResponseData;

import java.util.List;

/**
 * Created by zhangsong on 2017/12/11.
 */
public interface CommentService {
    Integer saveComment(Comment comment);
    Integer deleteComment(int id);
    Boolean newLike(int id,int userId);
    Boolean deleteLike(int id,int userId);
    ResponseData<List<Comment>> getCommentsByAmbitusId(int ambitusId,int page, int pageSize);
}
