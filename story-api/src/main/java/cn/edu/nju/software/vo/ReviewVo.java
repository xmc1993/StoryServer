package cn.edu.nju.software.vo;

import cn.edu.nju.software.entity.Review;
import cn.edu.nju.software.entity.User;
import lombok.Data;

/**
 * Created by Kt on 2017/7/2.
 */
@Data
public class ReviewVo {
    private Review review;
    private User fromUser;
    private User toUser;
}
