package cn.edu.nju.software.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Created by xmc1993 on 2017/5/16.
 */
@Data
@Api(value="故事角色关联类")
public class StoryRole implements Serializable{
	@ApiModelProperty(value="角色主键")
    private Integer id;
	@ApiModelProperty(value="故事ID")
    private Integer storyId;//故事ID
	@ApiModelProperty(value="角色名字")
    private String name;
	@ApiModelProperty(value="图标")
    private String icon;//图标
	@ApiModelProperty(value="音频")
    private String audio;//音频
	@ApiModelProperty(value="其余信息值")
    private String extra;//其余信息值
	@ApiModelProperty(value="创建时间")
    private Date createTime;
	@ApiModelProperty(value="更新时间")
    private Date updateTime;
	@ApiModelProperty(value="角色阅读指导")
    private String roleReadGuide;
    @ApiModelProperty(value="角色阅读时长")
    private String readTime;
}
