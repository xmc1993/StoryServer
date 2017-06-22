package cn.edu.nju.software.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by Kt on 2017/6/22.
 */
@Data
public class Review {
    private Integer id;

    private Integer workId;

    private Integer parentId;

    private Integer fromUserId;

    private Integer toUserId;

    private Integer subCommentCount;

    private Date createTime;

    private Date updateTime;

    private String content;

    public Review() {
    }

    public Review(Integer workId, Integer fromUserId, String content) {
        this.workId = workId;
        this.fromUserId = fromUserId;
        this.content = content;
    }
}
