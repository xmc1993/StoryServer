package cn.edu.nju.software.entity;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Data
@Api(value="故事集实体类")
public class StorySet {
	@ApiModelProperty(value="主键")
    private Integer id;
	@ApiModelProperty(value="标题")
    private String title;
	@ApiModelProperty(value="故事说明")
    private String guide;//故事说明
	@ApiModelProperty(value="封面地址")
    private String coverUrl;
    @ApiModelProperty(value="用于软删除")
    private Integer valid = 1;//用于软删除
    @ApiModelProperty(value="是否被推荐 0 否 1 是")
    private Integer recommend = 0;//是否被推荐 0 否 1 是
    @ApiModelProperty(value="被讲次数")
    private Integer tellCount = 0;//
    @ApiModelProperty(value="喜欢次数")
    private Integer likeCount;
    @ApiModelProperty(value="是否是集合")
    private Integer isSet = 1;
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

}
