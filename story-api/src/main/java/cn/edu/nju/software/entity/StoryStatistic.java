package cn.edu.nju.software.entity;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Data
@Api(value = "故事统计类")
public class StoryStatistic {
    @ApiModelProperty(value = "主键")
    private Integer id;
    @ApiModelProperty(value = "story的外键")
    private Integer storyId;
    @ApiModelProperty(value = "被讲次数")
    private Integer tellCount = 0;
    @ApiModelProperty(value = "被喜欢次数")
    private Integer likeCount = 0;
    @ApiModelProperty(value = "真实被讲次数")
    private Integer realTellCount = 0;
    @ApiModelProperty(value = "浏览次数")
    private Integer viewCount = 0;
    @ApiModelProperty(value = "评论数")
    private Integer commentCount = 0;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
