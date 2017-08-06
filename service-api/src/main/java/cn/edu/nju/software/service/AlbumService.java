package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Album;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface AlbumService {

    Album saveAlbum(Album album);

    boolean updateAlbum(Album album);

    boolean deleteAlbum(int id);

    Album getAlbumById(int id);

    List<Album> getAllAlbumByPage(int page, int pageSize);

    List<Album> getAlbumListByIdList(List<Integer> idList);

}
