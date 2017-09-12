package cn.edu.nju.software.service;
/**
* @author zs
* @version 创建时间：2017年9月12日 上午11:38:43
*/
public interface UsageStatisticService {
	 	void saveUsage(int userId);

	    int getCurCount(int userId);

	    int getHistoryMaxCount(int userId);
}
