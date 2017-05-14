package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.Agree;
import org.springframework.stereotype.Repository;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Repository
public interface AgreeDao {

    boolean saveAgree(Agree agree);

    boolean deleteAgree(int worksId, int userId);

    boolean deleteAgreeById(int id);

    boolean getAgreeUserIdListByWorksId(int worksId);

}
