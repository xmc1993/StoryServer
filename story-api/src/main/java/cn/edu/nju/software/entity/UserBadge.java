package cn.edu.nju.software.entity;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户徽章实体类
 *
 * @author liuyu
 * @create 2017-08-28 下午7:08
 */
@Data
@Api(value="故事实体类")
public class UserBadge {
    @ApiModelProperty(value="用户id")
    private Integer userId;
    @ApiModelProperty(value="徽章id")
    private Integer badgeId;

}
