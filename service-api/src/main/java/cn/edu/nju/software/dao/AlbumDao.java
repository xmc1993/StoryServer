package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.Album;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface AlbumDao {

    boolean saveAlbum(Album album);

    boolean updateAlbum(Album album);

    boolean deleteAlbum(int id);

    Album getAlbumById(int id);

    List<Album> getAllAlbumByPage(int offset, int limit);

}
