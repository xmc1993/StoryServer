package cn.edu.nju.software.vo;

import java.util.List;

import cn.edu.nju.software.entity.Story;
import lombok.Data;

@Data
public class StoryWithAlbum extends Story{
	private List<Integer> albumIdList;	
}
