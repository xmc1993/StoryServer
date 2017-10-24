package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.InitImage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zj
 */
@Repository
public interface InitImageDao {
    boolean addInitImage(InitImage initImage);
    boolean deleteInitImageById(Integer id);
    boolean updateInitImage(InitImage initImage);
    boolean updateIsShow(Integer id,Integer isShow);
    List<InitImage> getValidInitImageList();
    InitImage getInitImageById(Integer id);
    List<InitImage> getAllInitImageByPage(int offset, int limit);
}
