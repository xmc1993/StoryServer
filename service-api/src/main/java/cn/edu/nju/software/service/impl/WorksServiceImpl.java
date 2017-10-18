package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.StoryDao;
import cn.edu.nju.software.dao.WorksDao;
import cn.edu.nju.software.dao.user.AppUserDao;
import cn.edu.nju.software.entity.TwoTuple;
import cn.edu.nju.software.entity.Works;
import cn.edu.nju.software.service.WorksService;
import cn.edu.nju.software.service.user.AppUserService;
import cn.edu.nju.software.util.Const;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class WorksServiceImpl implements WorksService {

	@Autowired
	private WorksDao worksDao;
	@Autowired
	private StoryDao storyDao;
	@Autowired
	private AppUserDao appUserDao;
	@Autowired
	private AppUserService appUserService;

	@Override
	public Works saveWorks(Works works) {
		boolean res = worksDao.saveWorks(works);
		if (res) {
			storyDao.newTell(works.getStoryId());
			appUserDao.newWork(works.getUserId());
		}
		return works;
	}

	@Override
	public boolean listenWorks(int worksId) {
		Works works = worksDao.getWorksById(worksId);
		if (works != null) {
			appUserDao.newListened(works.getUserId());
		}
		return worksDao.addListenCount(worksId);
	}

	@Override
	public boolean updateWorks(Works works) {
		works.setUpdateTime(new Date());
		return worksDao.updateWorks(works);
	}

	@Override
	public boolean deleteWorksById(int id) {
		boolean res = worksDao.deleteWorksById(id);
		if (res) {
			Works works = worksDao.getWorksByIdHard(id);
			storyDao.deleteTell(works.getStoryId());
			appUserService.decreaseWorkCountSafely(works.getUserId());
		}
		return res;
	}

	@Override
	public Works getWorksById(int id) {
		return worksDao.getWorksById(id);
	}

	@Override
	public boolean deleteWorks(int storyId, int userId) {
		return false;
	}

	@Override
	public List<Works> getWorksListByUserId(int userId, int offset, int limit) {
		offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
		limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
		return worksDao.getWorksListByUserId(userId, offset, limit);
	}
	@Override
	public List<Works> getWorksListByUserIdByPage(int userId, int page, int pageSize) {
		int offset = page * pageSize;
		int limit = pageSize;
		return worksDao.getWorksListByUserId(userId, offset, limit);
	}

	@Override
	public List<Works> getWorksListByStoryId(int storyId, int offset, int limit) {
		offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
		limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
		return worksDao.getWorksListByStoryId(storyId, offset, limit);
	}

	@Override
	public List<Works> getWorksListByStoryIdByPage(int storyId, int page, int pageSize) {
		int offset = page * pageSize;
		int limit = pageSize;
		return worksDao.getWorksListByStoryId(storyId, offset, limit);
	}

	@Override
	public List<Works> getWorksListByIdList(List<Integer> idList) {
		idList.add(-1);// 防止mybatis查询出错
		return worksDao.getWorksListByIdList(idList);
	}

	@Override
	public Integer getWorksCount() {
		return worksDao.getWorksCount();
	}

	@Override
	public Integer getWorksCountByUserId(Integer userId) {
		return worksDao.getWorksCountByUserId(userId);
	}

	@Override
	public Integer getWorksCountByStoryId(Integer storyId) {
		return worksDao.getWorksCountByStoryId(storyId);
	}

	@Override
	public Integer getWorksCountByIdList(List<Integer> idList) {
		idList.add(-1);// 防止mybatis查询出错
		return worksDao.getWorksCountByIdList(idList);
	}

	@Override
	public List<Works> getLatestWorksByPage(int page, int pageSize) {
		return worksDao.getLatestWorksByPage(page * pageSize, pageSize);
	}

	@Override
	public List<Works> getMostPopularByPage(int page, int pageSize) {
		return worksDao.getMostPopularByPage(page * pageSize, pageSize);
	}

	@Override
	public List<TwoTuple<Integer, String>> getFirstWorkByPlayIdList(List<Integer> playIdList) {
		return worksDao.getFirstWorkByPlayIdList(playIdList);
	}

	@Override
	public PageInfo<Works> getWorksListByStoryIdListByPage(List<Integer> storyIdList, int page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		// 防止mybatis查询出错
		storyIdList.add(-1);
		List<Works> workList = worksDao.getWorksListByStoryIdList(storyIdList);
		PageInfo<Works> pageInfo = new PageInfo<>(workList);
		return pageInfo;
	}

	@Override
	public Integer getUserIdByWorkId(int worksId) {
		Works works = worksDao.getWorksById(worksId);
		if (works != null) {
			return works.getUserId();
		}
		return -1;
	}

	@Override
	public Integer getWorksAfterSomeDate(Integer userId, String date) {
		return worksDao.getWorksAfterSomeDate(userId, date);
	}

	@Override
	public String getOriginSoundLength(File file) {
		Encoder encoder = new Encoder();
		MultimediaInfo m = null;
		try {
			m = encoder.getInfo(file);
			long length = m.getDuration() / 1000;
			int hours = (int) (length / 3600);
			int minutes = (int) ((length % 3600) / 60);
			int seconds = (int) (length % 60);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(hours == 0 ? "0" : String.valueOf(hours));
			stringBuilder.append(":");
			stringBuilder.append(minutes == 0 ? "0" : String.valueOf(minutes));
			stringBuilder.append(":");
			stringBuilder.append(seconds == 0 ? "0" : String.valueOf(seconds));
			return stringBuilder.toString();
		} catch (EncoderException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean getWorksByUserAndStory(Integer userId, Integer storyId) {
		if (worksDao.getWorksByUserAndStory(userId, storyId) == null)
			return false;
		return true;
	}

	@Override
	// 此方法是为了在用户更新头像时，使work表中的HeadImgUrl字段也联动更新
	public boolean updateHeadImg(int userId, String url) {
		return worksDao.updateHeadImg(userId, url);
	}
}
