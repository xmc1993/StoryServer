package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.user.AppUserService;
import cn.edu.nju.software.util.UploadFileUtil;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Api("upload file controller")
@Controller
public class UploadFileController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UploadFileController.class);

    private static final String SUFFIX = ".jpg";
    private static final String HEAD_ROOT = "/head/"; //头像的基础路径
    private static final List<String> VALID_SUFFIX = new ArrayList<>();
    //初始化支持的头像类型
    static {
        VALID_SUFFIX.add("jpg");
        VALID_SUFFIX.add("jpeg");
        VALID_SUFFIX.add("bmp");
        VALID_SUFFIX.add("gif");
    }

    @Autowired
    private AppUserService userService;


    @ApiOperation(value = "上传头像", notes = "上传头像")
    @RequestMapping(value = "/user/uploadFile", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData uploadFile(MultipartFile uploadFile,
                                   @ApiParam("手机号") @RequestParam("mobile") String mobile,
                                   @ApiParam("时间戳") @RequestParam("timestamp") String timestamp,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.info("开始上传头像!");
        ResponseData responseData = new ResponseData();

        //如果头像超过2M
        if (uploadFile.getSize() > 2048L) {
            responseData.jsonFill(2, "头像不允许超过2M", null);
            return responseData;
        }
        //如果头像的格式不符合要求
        if (!VALID_SUFFIX.contains(UploadFileUtil.getSuffix(uploadFile.getOriginalFilename()))) {
            responseData.jsonFill(2, "头像类型只支持jpg,jpeg,bmp,gif。", null);
            return responseData;
        }

        String realPath = UploadFileUtil.getBaseUrl() + HEAD_ROOT;


        String fileName = mobile + "_" + timestamp + SUFFIX;

        if (uploadFile == null) {
            responseData.jsonFill(0, "请选择图片上传", null);
            return responseData;
        }


        boolean success = UploadFileUtil.mvFile(uploadFile, realPath, fileName);
        if (!success) {
            logger.error("上传头像失败!");
            responseData.jsonFill(2, "上传头像失败", null);
            return responseData;
        }


        //TODO 删除老头像

        responseData.jsonFill(1, null, "success");
        return responseData;
    }



}
