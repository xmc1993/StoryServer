package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.Baby;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface BabyDao {

    boolean saveBaby(Baby baby);

    boolean updateBaby(Baby baby);

    boolean deleteBaby(int id);

    Baby getBabyById(int id);

    List<Baby> getBabyListByParentId(int parentId);

    List<Baby> getAllBabies();

}
