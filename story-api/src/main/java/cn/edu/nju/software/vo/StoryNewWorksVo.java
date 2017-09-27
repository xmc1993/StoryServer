package cn.edu.nju.software.vo;

import java.util.List;

import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.StoryTag;

/**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年9月27日 
*/

public class StoryNewWorksVo extends Story{
    private List<StoryTag> tagList;
	private boolean isWorks=false;
	public List<StoryTag> getTagList() {
		return tagList;
	}
	public void setTagList(List<StoryTag> tagList) {
		this.tagList = tagList;
	}
	public boolean getisWorks() {
		return isWorks;
	}
	public void setisWorks(boolean isWorks) {
		this.isWorks = isWorks;
	}

}
