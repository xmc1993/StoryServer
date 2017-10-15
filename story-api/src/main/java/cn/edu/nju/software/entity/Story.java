package cn.edu.nju.software.entity;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Data
@Api(value="故事实体类")
public class Story {
	@ApiModelProperty(value="主键")
    private Integer id;
	@ApiModelProperty(value="标题")
    private String title;
	@ApiModelProperty(value="作者")
    private String author;
	@ApiModelProperty(value="内容")
    private String content;
	@ApiModelProperty(value="出版社")
    private String press;//出版社
	@ApiModelProperty(value="故事说明")
    private String guide;//故事说明
	@ApiModelProperty(value="封面地址")
    private String coverUrl;
    private String preCoverUrl;
    @ApiModelProperty(value="录制背景图url")
    private String backgroundUrl;//录制背景图url
    @ApiModelProperty(value="原始音效url")
    private String originSoundUrl;//原始音效url
    @ApiModelProperty(value="阅读指导")
    private String readGuide; //阅读指导
    @ApiModelProperty(value="价格")
    private String price;
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    @ApiModelProperty(value="更新时间")
    private Date updateTime;
    @ApiModelProperty(value="用于软删除")
    private Integer valid = 1;//用于软删除
    private String duration;
    @ApiModelProperty(value="是否被推荐 0 否 1 是")
    private Integer recommend = 0;//是否被推荐 0 否 1 是
    @ApiModelProperty(value="被讲次数")
    private Integer tellCount = 0;//
    @ApiModelProperty(value="默认背景音乐id")
    private Integer defaultBackGroundMusicId;//
    @ApiModelProperty(value="喜欢次数")
    private Integer likeCount;
    private Integer draft = 0;
    @ApiModelProperty(value="建议阅读时长 单位是秒")
    private Integer suggestedReadingDuration;//建议阅读时长 单位是秒
    @ApiModelProperty(value="专辑id列表")
    private List<Integer> albumIdList;
    @ApiModelProperty(value="是否是故事集 1 是 0 不是")
    private Integer isSet = 0;
    @ApiModelProperty(value="所属故事集ID")
    private Integer setId = 0;
    @ApiModelProperty(value="阅读时长")
    private String readTime;
    @ApiModelProperty(value="推荐时间")
    private Date recommendTime;
}
