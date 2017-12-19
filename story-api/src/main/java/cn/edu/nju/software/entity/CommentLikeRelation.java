package cn.edu.nju.software.entity;

import lombok.Data;

@Data
public class CommentLikeRelation {
    private Integer id;

    private Integer userId;

    private Integer commentId;

    private Boolean agree;

}