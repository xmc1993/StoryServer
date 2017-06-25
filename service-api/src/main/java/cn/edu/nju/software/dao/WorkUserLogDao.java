package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.WorkUserLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kt on 2017/6/25.
 */
@Repository
public interface WorkUserLogDao {
    public boolean insertWorkUserLog(WorkUserLog workUserLog);
    public Integer deleteWorkUserLogByIdList(@Param("idList") List<Integer> idList);
    public List<Integer> getUserIdListByWorkId(@Param("workId") int workId, @Param("offset") int offset, @Param("limit") int limit);
    public List<Integer> getWorkIdListByUserId(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);
}
