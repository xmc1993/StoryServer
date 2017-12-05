package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.annotation.RequiredPermissions;
import cn.edu.nju.software.entity.Discovery;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.DiscoveryService;
import cn.edu.nju.software.util.AntZipUtil;
import cn.edu.nju.software.util.FileUtil;
import cn.edu.nju.software.util.UploadFileUtil;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kt on 2017/6/26.
 */
@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class ManageDiscoveryController {
    @Autowired
    private DiscoveryService discoveryService;
    private static String DISCOVERY_PICTURE_ROOT = "/discovery/picture/";
    private static String DISCOVERY_ZIP_ROOT = "/discovery/";

    @RequiredPermissions({1,11})
    @ApiOperation(value = "新增发现", notes = "")
    @RequestMapping(value = "/saveDiscovery", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> saveDiscovery(@ApiParam("标题") @RequestParam(value = "title",required = false) String title,
                                      @ApiParam("描述") @RequestParam(value = "description",required = false) String description,
                                      @ApiParam("图片") @RequestParam(value = "picture") MultipartFile pictureFile,
                                      @ApiParam("压缩包") @RequestParam(value = "zip") MultipartFile zipFile,
                                      HttpServletRequest request, HttpServletResponse response){
        ResponseData<Boolean> result = new ResponseData<>();
        if(zipFile.isEmpty()||pictureFile.isEmpty()){
            result.jsonFill(2,"请上传文件",false);
        }
        /*if(zipFile.getContentTypeByExtension()!="application/x-zip-compressed") {
            result.jsonFill(2,"错误的压缩格式",false);
            return result;
        }*/
        Discovery discovery = new Discovery();
        String pictureUrl = UploadFileUtil.uploadFile(pictureFile,DISCOVERY_PICTURE_ROOT);
        discovery.setPictureUrl(pictureUrl);
        result=this.uploadDiscoveryZip(zipFile,discovery);
        if(result.getStatus()==2) return result;
        discovery.setTitle(title);
        discovery.setDescription(description);
        Date date = new Date();
        discovery.setCreateTime(date);
        discovery.setUpdateTime(date);
        discoveryService.saveDiscovery(discovery);
        result.jsonFill(1,null,true);
        return result;
    }

    @RequiredPermissions({3,11})
    @ApiOperation(value = "修改发现", notes = "")
    @RequestMapping(value = "/updateDiscovery", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> updateDiscovery( @ApiParam("发现") @RequestParam(value = "id") int id,
            @ApiParam("标题") @RequestParam(value = "title",required = false) String title,
                                               @ApiParam("描述") @RequestParam(value = "description",required = false) String description,
                                               @ApiParam("图片") @RequestParam(value = "picture",required = false) MultipartFile pictureFile,
                                               @ApiParam("压缩包") @RequestParam(value = "zip",required = false) MultipartFile zipFile,
                                               HttpServletRequest request, HttpServletResponse response){
        ResponseData<Boolean> result = new ResponseData<>();
        Discovery discovery = discoveryService.getDiscoveryById(id);
        if(discovery==null){
            result.jsonFill(2,"错误的id编号",false);
            return result;
        }
        if(!pictureFile.isEmpty()){
            UploadFileUtil.deleteFileByUrl(discovery.getPictureUrl());
        String pictureUrl = UploadFileUtil.uploadFile(pictureFile,DISCOVERY_PICTURE_ROOT);
        discovery.setPictureUrl(pictureUrl);
        }
        if(!zipFile.isEmpty()) {
            /*if(zipFile.getContentTypeByExtension()!="application/x-zip-compressed") {
                result.jsonFill(2,"错误的压缩格式",false);
                return result;
            }*/
            FileUtil.deleteFile(UploadFileUtil.getRealPathFromUrl(discovery.getWebUrl().substring(0,discovery.getWebUrl().lastIndexOf("/"))));
            result = this.uploadDiscoveryZip(zipFile, discovery);
            if(result.getStatus()==2) return result;
        }
        if(title!=null)discovery.setTitle(title);
        if(description!=null)discovery.setDescription(description);
        discovery.setUpdateTime(new Date());
        discoveryService.updateDiscovery(discovery);
        result.jsonFill(1,null,true);
        return result;
    }

    @RequiredPermissions({2,11})
    @ApiOperation(value = "删除发现", notes = "")
    @RequestMapping(value = "/deleteDiscovery", method = {RequestMethod.POST})
    @ResponseBody
    public  ResponseData<Boolean>  deleteDiscovery(@ApiParam("id") @RequestParam(value = "id") int id,
                                                   HttpServletRequest request, HttpServletResponse response){
        ResponseData<Boolean> result = new ResponseData<>();
        Discovery discovery = discoveryService.getDiscoveryById(id);
        if(discovery==null){
            result.jsonFill(2,"错误的id编号",false);
            return result;
        }
        UploadFileUtil.deleteFileByUrl(discovery.getPictureUrl());
        FileUtil.deleteFile(UploadFileUtil.getRealPathFromUrl(discovery.getWebUrl().substring(0,discovery.getWebUrl().lastIndexOf("/"))));
        boolean temp=discoveryService.deleteDiscovery(id);
        if(temp==true) {
            result.jsonFill(1,null,true);
            return result;
        }else{
            result.jsonFill(2,"删除失败",false);
            return result;
        }
    }

    @RequiredPermissions({2,11})
    @ApiOperation(value = "批量删除发现", notes = "")
    @RequestMapping(value = "/deleteDiscoveryList", method = {RequestMethod.POST})
    @ResponseBody
    public  ResponseData<Boolean>  deleteDiscovery(@ApiParam("idList") @RequestParam(value = "idList") int[] idList,
                                                   HttpServletRequest request, HttpServletResponse response){
        ResponseData<Boolean> result = new ResponseData<>();
        for(int id:idList) {
            Discovery discovery = discoveryService.getDiscoveryById(id);
            if (discovery == null) {
                result.jsonFill(2, "错误的id编号", false);
                return result;
            }
            FileUtil.deleteFile(UploadFileUtil.getRealPathFromUrl(discovery.getWebUrl().substring(0, discovery.getWebUrl().lastIndexOf("/"))));
            UploadFileUtil.deleteFileByUrl(discovery.getPictureUrl());
            boolean temp = discoveryService.deleteDiscovery(id);
            if (temp == false) {
                result.jsonFill(2, "删除失败", false);
                return result;
            }
        }
        result.jsonFill(1,null,true);
        return result;
    }

    @RequiredPermissions({4,11})
    @ApiOperation(value = "分页获取发现时间排序", notes = "")
    @RequestMapping(value = "/Discoveries", method = {RequestMethod.GET})
    @ResponseBody
    public  ResponseData<List<Discovery>>  getDiscoveryByPage(@ApiParam("offset") @RequestParam(value = "offset") int offset,
                                                   @ApiParam("limit") @RequestParam(value = "limit") int limit,HttpServletRequest request, HttpServletResponse response){
        ResponseData<List<Discovery>> result = new ResponseData<>();
        List<Discovery> discoveryList = discoveryService.getDiscoveryByCreateTimeDescPage(offset,limit);
        int count = discoveryService.getDiscoveryCount();
        result.jsonFill(1,null,discoveryList);
        result.setCount(count);
        return result;
    }
    @ApiOperation(value = "获取发现", notes = "")
    @RequestMapping(value = "/Discovery", method = {RequestMethod.GET})
    @ResponseBody
    public  ResponseData<Discovery>  getDiscovery(@ApiParam("id") @RequestParam(value = "id") int id,
                                                           HttpServletRequest request, HttpServletResponse response){
        ResponseData<Discovery> result = new ResponseData<>();
        Discovery discovery = discoveryService.getDiscoveryById(id);
        int count = discoveryService.getDiscoveryCount();
        result.jsonFill(1,null,discovery);
        result.setCount(count);
        return result;
    }
    @RequestMapping(value = "/testDiscovery", method = {RequestMethod.GET})
    @ResponseBody
    public boolean test(@ApiParam("id") @RequestParam(value = "id") int id,
                        HttpServletRequest request, HttpServletResponse response){
        Discovery discovery= discoveryService.getDiscoveryById(id);
        String temp = UploadFileUtil.getRealPathFromUrl(discovery.getWebUrl().substring(0, discovery.getWebUrl().lastIndexOf("/")));
        FileUtil.deleteFile(UploadFileUtil.getRealPathFromUrl(discovery.getWebUrl().substring(0, discovery.getWebUrl().lastIndexOf("/"))));
        return true;
    }
    private ResponseData<Boolean> uploadDiscoveryZip(MultipartFile zipFile,Discovery discovery){
        ResponseData<Boolean> result= new ResponseData<>();
        String zipUrl = UploadFileUtil.uploadFile(zipFile,DISCOVERY_ZIP_ROOT);
        String zipPath =UploadFileUtil.getRealPathFromUrl(zipUrl.substring(0,zipUrl.lastIndexOf("/")));
        String upZipPath =UploadFileUtil.getRealPathFromUrl(zipUrl.substring(0,zipUrl.lastIndexOf(".")));
        String upZipName = zipUrl.substring(zipUrl.lastIndexOf("/")+1);

        System.out.println("------------" + upZipPath + "------------");
        try {
            AntZipUtil.unZip(zipPath+"/"+upZipName,upZipPath);
        } catch (Exception e) {
            e.printStackTrace();
            result.jsonFill(2,"解压失败，请检查压缩文件",false);
            FileUtil.deleteFile(upZipPath);
            return result;
        }
        List<File> htmlList = new ArrayList();
        FileUtil.findFiles(upZipPath,"*.html",htmlList);
        if(htmlList.size()>=1){
            String localPath= htmlList.get(0).getAbsolutePath();
            String webUrl = UploadFileUtil.getURLFromPath(localPath);
            discovery.setWebUrl(webUrl);
        }else {
            result.jsonFill(2,"文件不包含html或含有一个以上html请检查压缩文件",false);
//            FileUtil.deleteFile(upZipPath);
            return result;
        }
        UploadFileUtil.deleteFileByUrl(zipUrl);
        result.jsonFill(1,null,true);
        return result;
    }

}
