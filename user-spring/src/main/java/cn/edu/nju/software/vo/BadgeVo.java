package cn.edu.nju.software.vo;

import cn.edu.nju.software.entity.Badge;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 徽章扩展类
 *
 * @author liuyu
 * @create 2017-08-29 上午11:02
 */
@Data
public class BadgeVo extends Badge {
    @ApiModelProperty(value="用户是否已获得徽章")
    private boolean hasGet = false;
}
