package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.Agree;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Repository
public interface AgreeDao {

    boolean saveAgree(Agree agree);

    boolean deleteAgree(int worksId, int userId);

    boolean deleteAgreeById(int id);

    List<Integer> getAgreeUserIdListByWorksId(int worksId);

    List<Integer> getAgreeWorksListByUserId(int userId, int offset, int limit);

}
