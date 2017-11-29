package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.BabyReadPlan;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangsong on 2017/11/28.
 */

@Repository
public interface BabyReadPlanDao {
    boolean saveBabyReadPlan(BabyReadPlan babyReadPlan);
    boolean updateBabyReadPlan(BabyReadPlan babyReadPlan);
    BabyReadPlan getBabyReadPlanById(int id);
    BabyReadPlan getBabyReadPlanByBabyId(int babyId);
}
