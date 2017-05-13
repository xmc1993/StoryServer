package cn.edu.nju.software.dao.user;

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
public interface AppUserBrowseDao {

    boolean save(UserBrowse entity);
    boolean update(UserBrowse entity);

}
