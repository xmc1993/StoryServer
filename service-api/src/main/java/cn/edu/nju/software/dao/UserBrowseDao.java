package cn.edu.nju.software.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.edu.nju.software.entity.UserBrowse;
import cn.edu.nju.software.vo.request.StatQueryBean;
import cn.edu.nju.software.vo.request.UserBrowseQueryBean;

/**
 * Author: dalec
 * Created at: 16/8/17
 */
@Repository
public interface UserBrowseDao {

    boolean save(UserBrowse entity);
    boolean update(UserBrowse entity);

    UserBrowse findByTypeAndStatId(Integer businessId, String type, Integer statId);
    UserBrowse findByTypeAndUserId(Integer businessId, String type, Integer userId);
    UserBrowse findByTypeAndUserIdAndStatId(Integer businessId, String type, Integer userId,Integer statId);

    List<UserBrowse> findByTypeAndStatIdList(UserBrowseQueryBean bean);
	
	public List<UserBrowse> getActivityStatsByUserByActivityEverytime(StatQueryBean bean);
	public Integer getActivityStatsByUserByActivityEverytimeCount(StatQueryBean bean);
	public List<UserBrowse> getActivityStatsByUserToActivityEverytime(StatQueryBean bean);
	public Integer getActivityStatsByUserToActivityEverytimeCount(StatQueryBean bean);
	
	public List<UserBrowse> getActivityStatsByUserByArticleEverytime(StatQueryBean bean);
	public Integer getActivityStatsByUserByArticleEverytimeCount(StatQueryBean bean);
	public List<UserBrowse> getActivityStatsByUserToArticleEverytime(StatQueryBean bean);
	public Integer getActivityStatsByUserToArticleEverytimeCount(StatQueryBean bean);
	
	public List<UserBrowse> getActivityStatsByActivityToUserEverytime(StatQueryBean bean);
	public Integer getActivityStatsByActivityToUserEverytimeCount(StatQueryBean bean);
	
	public List<UserBrowse> getActivityStatsByArticleToUserEverytime(StatQueryBean bean);
	public Integer getActivityStatsByArticleToUserEverytimeCount(StatQueryBean bean);
	
	public UserBrowse getByUserAndType(Integer businessId, Integer userId, String type);
	public UserBrowse getByType(Integer businessId, String type);
	

	public List<UserBrowse> getActivityStatsByUserByActivityEverytimeExport(StatQueryBean bean);
	public List<UserBrowse> getActivityStatsByUserToActivityEverytimeExport(StatQueryBean bean);
	
	public List<UserBrowse> getActivityStatsByUserByArticleEverytimeExport(StatQueryBean bean);
	public List<UserBrowse> getActivityStatsByUserToArticleEverytimeExport(StatQueryBean bean);

	public List<UserBrowse> findByTypeAndStatIdSum(StatQueryBean bean);
	

}
