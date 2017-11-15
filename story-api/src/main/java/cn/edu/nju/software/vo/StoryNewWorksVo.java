package cn.edu.nju.software.vo;

import java.util.List;

import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.StoryTag;
import lombok.Data;

/**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年9月27日 
*/

@Data
public class StoryNewWorksVo extends Story{
    private List<StoryTag> tagList;

    //改用户是否录制过该故事。
	private boolean isWorks=false;
}
