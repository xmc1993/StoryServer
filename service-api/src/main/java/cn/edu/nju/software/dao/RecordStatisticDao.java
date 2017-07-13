package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.RecordStatistic;
import org.springframework.stereotype.Repository;


@Repository
public interface RecordStatisticDao {

    boolean saveRecordStatistic(RecordStatistic recordStatistic);

    boolean updateRecordStatistic(RecordStatistic recordStatistic);

    RecordStatistic getRecordStatisticByUserId(int userId);

}
