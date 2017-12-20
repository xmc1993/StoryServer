package cn.edu.nju.software.dto;

import cn.edu.nju.software.entity.Badge;
import cn.edu.nju.software.entity.StoryTag;
import cn.edu.nju.software.entity.WorkTag;
import cn.edu.nju.software.entity.Works;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/14.
 */
@Data
public class WorksVo extends Works{
    private boolean like = false;
    private Badge badge;
    private List<StoryTag> tagList;
    private List<WorkTag> workTagList;
}
