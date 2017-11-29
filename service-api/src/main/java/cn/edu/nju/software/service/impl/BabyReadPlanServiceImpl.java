package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.BabyDao;
import cn.edu.nju.software.dao.BabyReadPlanDao;
import cn.edu.nju.software.entity.Baby;
import cn.edu.nju.software.entity.BabyReadPlan;
import cn.edu.nju.software.entity.ReadingPlan;
import cn.edu.nju.software.service.BabyReadPlanService;
import cn.edu.nju.software.service.ReadPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangsong on 2017/11/29.
 */
@Service
public class BabyReadPlanServiceImpl implements BabyReadPlanService {
    @Autowired
    ReadPlanService readPlanService;
    @Autowired
    BabyDao babyDao;
    @Autowired
    BabyReadPlanDao babyReadPlanDao;

    @Override
    public boolean saveBabyReadPlan(Baby baby) {
        if(baby.getBirthday()!=null){
            List<ReadingPlan> list=getBabyReadPlan(baby);
            BabyReadPlan babyReadPlan=new BabyReadPlan();
            babyReadPlan.setBabyId(baby.getId());
            babyReadPlan.setReadPlanId(list.get(0).getId());
            return babyReadPlanDao.saveBabyReadPlan(babyReadPlan);
        }
        return false;
    }

    @Override
    public boolean updateBabyReadPlan(BabyReadPlan babyReadPlan) {
        return babyReadPlanDao.updateBabyReadPlan(babyReadPlan);
    }

    @Override
    public BabyReadPlan getBabyReadPlanById(int id) {
        return babyReadPlanDao.getBabyReadPlanById(id);
    }

    //这个方法是每个月刷新新的阅读计划
    //每个月的第一天凌晨1点，更新阅读计划
    //"0 0 1 1 * ?"
    @Scheduled(cron="0 0 1 1 * ?")
    @Override
    public void freshenBabyReadPlanTable() {
        BabyReadPlan babyReadPlan=new BabyReadPlan();
        List<Baby> list=babyDao.getAllBabies();
        for (Baby baby : list) {
            List<ReadingPlan> readingPlanList=getBabyReadPlan(baby);
            if (readingPlanList!=null){
                babyReadPlan.setReadPlanId(readingPlanList.get(0).getId());
                babyReadPlan.setBabyId(baby.getId());
                babyReadPlanDao.updateBabyReadPlan(babyReadPlan);
            }
        }
    }

    @Override
    public void initBabyReadPlan() {
        BabyReadPlan babyReadPlan=new BabyReadPlan();
        //获取所有宝宝的id
        List<Baby> list=babyDao.getAllBabies();
        for (Baby baby : list) {
            List<ReadingPlan> readingPlanList=getBabyReadPlan(baby);
            if(readingPlanList!=null){
                babyReadPlan.setReadPlanId(readingPlanList.get(0).getId());
                babyReadPlan.setBabyId(baby.getId());
                babyReadPlanDao.saveBabyReadPlan(babyReadPlan);
            }
        }
    }

    @Override
    public BabyReadPlan getBabyReadPlanByBabyId(int babyId) {
        return babyReadPlanDao.getBabyReadPlanByBabyId(babyId);
    }


    // 方法抽取获取 获取俩段时间的天数 date1为开始时间
    private static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        java.util.Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return Math.abs(day);
    }



    // 方法抽取：根据baby生日获取宝宝的的阅读计划
    // 3个月：91天
    // 3-6个月：182天
    // 6-12个月：365天
    // 12-18个月：547天
    // 18-24个月：730天
    // 2-3岁：1095天
    // 3-4岁：1460天
    // 4-5岁：1825天
    // 5-6岁：2190天
    // 当宝宝年龄大于6岁时，小于7岁时，阅读计划给他返回 5-6岁的阅读计划
    // 还未出生的宝宝给他返回0-2岁的阅读计划
    private List<ReadingPlan> getBabyReadPlan(Baby baby) {
        // 获取当前时间
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        String babyBirthday = formatter.format(baby.getBirthday());

        // 获取当前年份和月份
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy/MM");
        String timePoint = formatter2.format(currentTime);
        // 计算天数
        long days = 0;
        days = getDays(babyBirthday, dateString);
        if (days <= 730) {
            List<ReadingPlan> list = readPlanService.getReadingPlanByTime("0-2岁", timePoint);
            return list;
        } else if (days <= 1095 && days > 730) {
            List<ReadingPlan> list = readPlanService.getReadingPlanByTime("2-3岁", timePoint);
            return list;
        } else if (days <= 1460 && days > 1095) {
            List<ReadingPlan> list = readPlanService.getReadingPlanByTime("3-4岁", timePoint);
            return list;
        } else if (days <= 1825 && days > 1460) {
            List<ReadingPlan> list = readPlanService.getReadingPlanByTime("4-5岁", timePoint);
            return list;
        } else if (days <= 2190 && days > 1825) {
            List<ReadingPlan> list = readPlanService.getReadingPlanByTime("5-6岁", timePoint);
            return list;
        } else if (days <= 2555 && days > 2190) {
            List<ReadingPlan> list = readPlanService.getReadingPlanByTime("5-6岁", timePoint);
            return list;
        }
        return null;
    }


}
