package cn.edu.nju.software.entity;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/14.
 */
@Data
@Api(value="作品实体类")
public class Works {
	@ApiModelProperty(value="主键")
    private Integer id;
	@ApiModelProperty(value="故事")
    private Integer storyId;
	@ApiModelProperty(value="故事标题（字段冗余）")
    private String storyTitle;//字段冗余
	@ApiModelProperty(value="用户id")
    private Integer userId;
	@ApiModelProperty(value="用户名字（字段冗余）")
    private String username;//字段冗余
	@ApiModelProperty(value="音频的url")
    private String url;//音频的url
	@ApiModelProperty(value="点赞数")
    private Integer likeCount = 0;//点赞数
	@ApiModelProperty(value="创建时间")
    private Date createTime;
	@ApiModelProperty(value="更新时间")
    private Date updateTime;
	@ApiModelProperty(value="用于软删除")
    private Integer valid = 1;//用于软删除
	@ApiModelProperty(value="故事标题图")
    private String headImgUrl;
	@ApiModelProperty(value="封面图")
    private String coverUrl;
    private String duration;
    @ApiModelProperty(value="浏览次数")
    private Integer reviewCount;
    @ApiModelProperty(value="收听数")
    private Integer listenCount = 0;//收听数
    @ApiModelProperty(value="排序时间")
    private Integer orderTime;
    @ApiModelProperty(value="故事集Id")
    private Integer storySetId;
}
