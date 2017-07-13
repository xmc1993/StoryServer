package cn.edu.nju.software.service;

/**
 * Created by xmc1993 on 2017/7/13.
 */
public interface RecordStatisticService {

    void saveRecord(int userId);

    int getCurCount(int userId);

    int getHistoryMaxCount(int userId);
}
