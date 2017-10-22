package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.InitImageDao;
import cn.edu.nju.software.entity.InitImage;
import cn.edu.nju.software.service.InitImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zj
 */
@Service
public class InitImageServiceImpl implements InitImageService{
    @Autowired
    private InitImageDao initImageDao;

    @Override
    public InitImage addInitImage(InitImage initImage) {
        return initImageDao.addInitImage(initImage);
    }

    @Override
    public boolean deleteInitImageById(Integer id) {
        return initImageDao.deleteInitImageById(id);
    }

    @Override
    public boolean updateInitImage(InitImage initImage) {
        return initImageDao.updateInitImage(initImage);
    }

    @Override
    public boolean updateIsShow(Integer id, Integer isShow) {
        return initImageDao.updateIsShow(id,isShow);
    }

    @Override
    public InitImage getInitImage() {
        List initImageList=initImageDao.getInitImageList();
        int i=(int)(Math.random()*(initImageList.size()-1));
        InitImage initImage=(InitImage) initImageList.get(i);
        return initImage;
    }

    @Override
    public InitImage getInitImageById(Integer id) {
        return initImageDao.getInitImageById(id);
    }

    @Override
    public List<InitImage> getAllInitImageByPage(int page, int pageSize) {
        int offset = page * pageSize;
        int limit = pageSize;
        return initImageDao.getAllInitImageByPage(offset,limit);
    }
}
