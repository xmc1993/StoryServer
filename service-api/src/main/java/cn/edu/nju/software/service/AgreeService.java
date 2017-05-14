package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Agree;
import cn.edu.nju.software.entity.UserBase;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/14.
 */
public interface AgreeService {

    boolean addAgree(Agree agree);

    boolean deleteAgree(int worksId, int userId);

    boolean deleteAgreeById(int id);

    List<UserBase> getAgreeUserIdListByWorksId(int worksId);
}
