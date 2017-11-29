package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Baby;
import cn.edu.nju.software.entity.BabyReadPlan;

/**
 * Created by zhangsong on 2017/11/28.
 */

public interface BabyReadPlanService {
    boolean saveBabyReadPlan(Baby baby);
    boolean updateBabyReadPlan(BabyReadPlan babyReadPlan);
    BabyReadPlan getBabyReadPlanById(int id);
    void freshenBabyReadPlanTable();
    void initBabyReadPlan();
    BabyReadPlan getBabyReadPlanByBabyId(int babyId);
}
