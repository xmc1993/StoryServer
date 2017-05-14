package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.UserBrowse;
import org.springframework.stereotype.Repository;

/**
 * Author: dalec
 * Created at: 16/8/17
 */
@Repository
public interface UserBrowseDao {

    boolean save(UserBrowse entity);
    boolean update(UserBrowse entity);

}
