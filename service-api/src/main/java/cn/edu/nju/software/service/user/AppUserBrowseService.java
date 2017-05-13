package cn.edu.nju.software.service.user;

import cn.edu.nju.software.entity.UserBrowse;
import cn.edu.nju.software.vo.request.StatQueryBean;
import cn.edu.nju.software.vo.response.ActivityStatsResponseBean;
import cn.edu.nju.software.vo.response.ArticleStatsResponseBean;
import cn.edu.nju.software.vo.response.PageResponseBean;

import java.util.List;
import java.util.Map;

/**
 * Author: dalec
 * Created at: 16/8/23
 */
public interface AppUserBrowseService {

    public void save(UserBrowse entity);

}
