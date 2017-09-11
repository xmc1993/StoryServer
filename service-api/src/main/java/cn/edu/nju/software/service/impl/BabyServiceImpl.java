package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.BabyDao;
import cn.edu.nju.software.entity.Baby;
import cn.edu.nju.software.service.BabyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class BabyServiceImpl implements BabyService {

    @Autowired
    private BabyDao babyDao;


    @Override
    public Baby saveBaby(Baby baby) {
        if (babyDao.saveBaby(baby)) {
            return baby;
        }
        return null;
    }

    @Override
    public boolean updateBaby(Baby baby) {
        return babyDao.updateBaby(baby);
    }

    @Override
    public boolean deleteBaby(int id) {
        return babyDao.deleteBaby(id);
    }

    @Override
    public Baby getBabyById(int id) {
        return babyDao.getBabyById(id);
    }

    @Override
    public List<Baby> getBabyListByParentId(int parentId) {
        return babyDao.getBabyListByParentId(parentId);
    }

    @Override
    public List<Baby> getAllBabies() {
        return babyDao.getAllBabies();
    }

	@Override
	public Baby getSelectedBady(int parentId) {		
		return babyDao.getSelectedBady(parentId);
	}

	@Override
	public boolean selectBady(int parentId,int id) {
		boolean res=babyDao.unSelectAllBady(parentId);
		if (res== false) {
			return false;
		}else {
			boolean sucess=babyDao.selectBady(id);
			return sucess;
		}
	}
}
