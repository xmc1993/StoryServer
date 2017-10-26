package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.controller.user.UploadFileController;
import cn.edu.nju.software.entity.InitImage;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryRoleAudio;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.InitImageService;
import cn.edu.nju.software.util.FileUtil;
import cn.edu.nju.software.util.UploadFileUtil;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zj
 */
@Api(value = "Admin", description = "开屏图管理接口")
@Controller
@RequestMapping("/manage/initImage")
public class ManageInitImageController {

    private static final Logger logger = LoggerFactory.getLogger(ManageInitImageController.class);

    @Autowired
    private InitImageService initImageService;

    @ApiOperation(value = "新增一张开屏图", notes = "新增一张开屏图")
    @RequestMapping(value = "addInitImage", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<InitImage> addInitImage(
            @ApiParam("开屏图url") @RequestParam("initImageUrl") String initImageUrl,
            @ApiParam("是否启用这张开屏图") @RequestParam(value ="isShow",required = false) Integer isShow){
        String imgName=FileUtil.getFileNameByUrl(initImageUrl);

        InitImage initImage=new InitImage();
        initImage.setImgName(imgName);
        initImage.setImgUrl(initImageUrl);
        if (isShow!=null){
            initImage.setIsShow(isShow);
        }
        initImage.setCreateTime(new Date());
        initImage.setValid(1);
        ResponseData<InitImage> responseData = new ResponseData<>();
        boolean result=initImageService.addInitImage(initImage);
        if (!result){
            responseData.jsonFill(2,"添加开屏图失败",null);
            return responseData;
        }
        responseData.jsonFill(1,null,initImage);
        return responseData;
    }

    @ApiOperation(value = "从文件服务器删除一张开屏图", notes = "")
    @RequestMapping(value = "deleteInitImageById", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> deleteInitImageById(@ApiParam("开屏图ID") @RequestParam("id") Integer id){
        ResponseData<Boolean> responseData = new ResponseData<>();

        //从服务器上的开屏图片
        InitImage initImage=initImageService.getInitImageById(id);
        String imgUrl=initImage.getImgUrl();
        boolean res = UploadFileUtil.deleteFileByUrl(imgUrl);
        if (!res){
            responseData.jsonFill(2,"删除服务器上的开屏图片失败",false);
            return responseData;
        }

        //从数据库删除开屏图
        boolean result=initImageService.deleteInitImageById(id);
        if (!result){
            responseData.jsonFill(2,"从数据库删除开屏图失败",false);
            return responseData;
        }

        //从服务器和数据库删除开屏图都成功。
        responseData.jsonFill(1, null, true);
        return responseData;
    }

    @ApiOperation(value = "更新开屏图信息", notes = "")
    @RequestMapping(value = "updateInitImage", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> updateInitImage(
            @ApiParam("图片ID") @RequestParam(value = "id") Integer id,
            @ApiParam("开屏图名") @RequestParam(value = "imgName",required = false) String imgName,
            @ApiParam("图片url") @RequestParam(value = "imgUrl",required = false) String imgUrl,
            @ApiParam("是否在用") @RequestParam(value = "isShow", required = false) Integer isShow,
            @ApiParam("是否有效") @RequestParam(value = "valid", required = false) Integer valid
    ){
        ResponseData<Boolean> responseData=new ResponseData<>();
        InitImage initImage=initImageService.getInitImageById(id);
        if(initImage==null){
            responseData.jsonFill(2, "无效的图片id", false);
            return responseData;
        }
        if (!"".equals(imgName)&&null!=imgName){
            initImage.setImgName(imgName);
        }
        if (!"".equals(imgUrl)&&null!=imgUrl){
            initImage.setImgUrl(imgUrl);
        }
        if (null!=isShow){
            initImage.setIsShow(isShow);
        }
        initImage.setUpdateTime(new Date());
        if (null!=valid){
            initImage.setValid(valid);
        }
        boolean result=initImageService.updateInitImage(initImage);
        responseData.jsonFill(result ? 1 : 2, null, result);
        return responseData;
    }

    @ApiOperation(value = "设置开屏图是否可展示", notes = "isShow=0表示暂时不用，isShow=1表示启用")
    @RequestMapping(value = "updateIsShow", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> updateIsShow(
            @ApiParam("开屏图ID") @RequestParam("id") Integer id,
            @ApiParam("是否可以展示") @RequestParam("isShow") Integer isShow){
        ResponseData<Boolean> responseData=new ResponseData<>();
        if (isShow==null){
            responseData.jsonFill(2, "isShow为空", false);
            return responseData;
        }
        System.err.println(isShow);
        boolean res=initImageService.updateIsShow(id,isShow);
        responseData.jsonFill(res ? 1 : 2, null, res);
        return responseData;
    }

    @ApiOperation(value = "随机获取可用开屏图", notes = "")
    @RequestMapping(value = "getInitImage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<InitImage> getInitImage(){
        InitImage initImage=initImageService.getInitImage();
        ResponseData<InitImage> responseData=new ResponseData<>();
        if (initImage==null){
            responseData.jsonFill(2, "没有可用的开屏图", null);
            return responseData;
        }
        responseData.jsonFill(1,null,initImage);
        return responseData;
    }

    @ApiOperation(value = "通过ID获取开屏图", notes = "")
    @RequestMapping(value = "getInitImageById", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<InitImage> getInitImageById(@ApiParam("开屏图ID") @RequestParam("id") Integer id){
        ResponseData<InitImage> responseData=new ResponseData<>();
        InitImage initImage=initImageService.getInitImageById(id);
        if (initImage==null){
            responseData.jsonFill(2, "这张图不存在或已被删除", null);
            return responseData;
        }
        responseData.jsonFill(1,null,initImage);
        return responseData;
    }

    @ApiOperation(value = "分页获取所有可用开屏图", notes = "")
    @RequestMapping(value = "getAllInitImageByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<InitImage>> getAllInitImageByPage(
            @ApiParam("当前页") @RequestParam("page") int page,
            @ApiParam("页数大小") @RequestParam("pageSize") int pageSize){
        ResponseData<List<InitImage>> responseData = new ResponseData<>();
        List<InitImage> initImageList = initImageService.getAllInitImageByPage(page, pageSize);
        responseData.jsonFill(1, null, initImageList);
        responseData.setCount(initImageList.size());
        return responseData;
    }

}
