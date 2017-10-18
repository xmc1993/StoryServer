package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.annotation.RequiredPermissions;
import cn.edu.nju.software.entity.Album;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.AlbumService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/15.
 */
@Api(value = "Admin", description = "管理接口")
@Controller()
@RequestMapping("/manage")
public class ManageAlbumController {
    private static final Logger logger = LoggerFactory.getLogger(ManageAlbumController.class);
    @Autowired
    private AlbumService albumService;
    
    @ApiOperation(value = "新增专辑", notes = "")
    @RequestMapping(value = "/albums", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Album publishAlbum(
            @ApiParam("专辑") @RequestBody Album album,
            HttpServletRequest request, HttpServletResponse response) {
        album.setCreateTime(new Date());
        album.setUpdateTime(new Date());
        albumService.saveAlbum(album);
        return album;
    }

    @ApiOperation(value = "更新专辑", notes = "")
    @RequestMapping(value = "/albums/{id}", method = {RequestMethod.PUT})
    @ResponseBody
    public Album updateAlbum(
            @ApiParam("ID") @PathVariable int id,
            @ApiParam("") @RequestBody Album album,
            HttpServletRequest request, HttpServletResponse response) {
        album.setId(id);
        album.setUpdateTime(new Date());
        return albumService.updateAlbum(album) ? album : null;

    }

    @ApiOperation(value = "删除专辑", notes = "")
    @RequestMapping(value = "/albums/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        boolean success = albumService.deleteAlbum(id);
        if (!success) {
            throw new RuntimeException("删除失败");
        }
    }


    @ApiOperation(value = "根据ID获得专辑", notes = "")
    @RequestMapping(value = "/albums/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public Album getStoryById(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        Album album = albumService.getAlbumById(id);
        if (album == null) {
            throw new RuntimeException("无效的ID");
        } else {
            return album;
        }
    }

    @ApiOperation(value = "分页获得专辑", notes = "")
    @RequestMapping(value = "/getAlbumListByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Album>> getBadgeTypeListByPage(
            @ApiParam("PAGE") @RequestParam int page,
            @ApiParam("SIZE") @RequestParam int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<Album>> responseData = new ResponseData<>();
        List<Album> albumTypeList = albumService.getAllAlbumByPage(page, pageSize);
        responseData.jsonFill(1, null, albumTypeList);
        return responseData;
    }


}
