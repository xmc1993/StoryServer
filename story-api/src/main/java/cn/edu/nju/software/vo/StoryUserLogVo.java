package cn.edu.nju.software.vo;

import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.StoryUserLog;
import cn.edu.nju.software.entity.User;
import lombok.Data;

/**
 * Created by Kt on 2017/6/27.
 */
@Data
public class StoryUserLogVo {
    private Integer id;
    private String storyName;
    private Integer storyId;
    private Integer userId;
}
