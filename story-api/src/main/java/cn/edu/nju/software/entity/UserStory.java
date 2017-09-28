package cn.edu.nju.software.entity;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Data
@Api(value="用户故事")
public class UserStory {
	@ApiModelProperty(value="主键")
    private Integer id;
	@ApiModelProperty(value="标题")
    private String title;
    @ApiModelProperty(value="内容")
    private String content;
    @ApiModelProperty(value="作者")
    private String author;
    @ApiModelProperty(value="用户ID")
    private Integer userId;
	@ApiModelProperty(value="封面地址")
    private String coverUrl;
    @ApiModelProperty(value="故事类型 0:普通故事 1:故事集 2:用户故事")
    private Integer isSet = 2;
    @ApiModelProperty(value="用于软删除")
    private Integer valid = 1;//用于软删除
    @ApiModelProperty(value="被讲次数")
    private Integer tellCount = 0;//
    @ApiModelProperty(value="喜欢次数")
    private Integer likeCount = 0;
    @ApiModelProperty(value="可见性")
    private Integer visibility;
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

}
