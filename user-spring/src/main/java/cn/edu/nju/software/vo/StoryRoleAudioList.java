package cn.edu.nju.software.vo;

import java.util.List;

/**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年9月22日 
*/

public class StoryRoleAudioList {
	private Integer storyId;
	private List<StoryRoleAudioVo> roleMsg;
	
	public Integer getStoryId() {
		return storyId;
	}
	public void setStoryId(Integer storyId) {
		this.storyId = storyId;
	}
	public List<StoryRoleAudioVo> getRoleMsg() {
		return roleMsg;
	}
	public void setRoleMsg(List<StoryRoleAudioVo> roleMsg) {
		this.roleMsg = roleMsg;
	}

	
}
