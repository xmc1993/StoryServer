package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Baby;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface BabyService {

    Baby saveBaby(Baby baby);

    boolean updateBaby(Baby baby);

    boolean deleteBaby(int id);

    Baby getBabyById(int id);

    List<Baby> getBabyListByParentId(int parentId);

    List<Baby> getAllBabies();
    
    Baby getSelectedBaby(int parentId);
    
    boolean selectBaby(int parentId,int id);

    //获得用户的一个宝宝（先拿选中的，没有的话拿他第一个宝宝）
    Baby getUserOneBaby(int userId);

}
