package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.AlbumDao;
import cn.edu.nju.software.entity.Album;
import cn.edu.nju.software.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumDao albumDao;


    @Override
    public Album saveAlbum(Album album) {
        if (albumDao.saveAlbum(album)) {
            return album;
        }
        return null;
    }

    @Override
    public boolean updateAlbum(Album album) {
        return albumDao.updateAlbum(album);
    }

    @Override
    public boolean deleteAlbum(int id) {
        return albumDao.deleteAlbum(id);
    }

    @Override
    public Album getAlbumById(int id) {
        return albumDao.getAlbumById(id);
    }


    @Override
    public List<Album> getAllAlbumByPage(int page, int pageSize) {
        int offset = page*pageSize;
        int limit = pageSize;
        return albumDao.getAllAlbumByPage(offset, limit);
    }
}
