package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.PowerCodeDao;
import cn.edu.nju.software.entity.PowerCode;
import cn.edu.nju.software.service.PowerCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class PowerCodeServiceImpl implements PowerCodeService {

    @Autowired
    private PowerCodeDao powerCodeDao;


    @Override
    public PowerCode savePowerCode(PowerCode powerCode) {
        if (powerCodeDao.savePowerCode(powerCode)) {
            return powerCode;
        }
        return null;
    }

    @Override
    public boolean updatePowerCode(PowerCode powerCode) {
        return powerCodeDao.updatePowerCode(powerCode);
    }

    @Override
    public boolean deletePowerCode(int id) {
        return powerCodeDao.deletePowerCode(id);
    }

    @Override
    public PowerCode getPowerCodeById(int id) {
        return powerCodeDao.getPowerCodeById(id);
    }


    @Override
    public List<PowerCode> getPowerCodeListByPage(int page, int pageSize) {
        int offset = page*pageSize;
        int limit = pageSize;
        return powerCodeDao.getPowerCodeListByPage(offset, limit);
    }

	@Override
	public Integer getPowerCodeCount() {
		return powerCodeDao.getPowerCodeCount();
	}
}
