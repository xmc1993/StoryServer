package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.InitImage;

import java.util.List;

/**
 * @author zj
 */
public interface InitImageService {
    InitImage addInitImage(InitImage initImage);
    boolean deleteInitImageById(Integer id);
    boolean updateInitImage(InitImage initImage);
    boolean updateIsShow(Integer id,Integer isShow);
    InitImage getInitImage();
    InitImage getInitImageById(Integer id);
    List<InitImage> getAllInitImageByPage(int page, int pageSize);
}
