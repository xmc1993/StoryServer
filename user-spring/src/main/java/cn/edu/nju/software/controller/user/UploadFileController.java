package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.WorksService;
import cn.edu.nju.software.service.user.AppUserService;
import cn.edu.nju.software.service.wxpay.util.RandCharsUtils;
import cn.edu.nju.software.util.OSSUtil;
import cn.edu.nju.software.util.TokenConfig;
import cn.edu.nju.software.util.UploadFileUtil;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api("upload controller")
@Controller
@RequestMapping("/user")
public class UploadFileController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UploadFileController.class);
    private static final String SUFFIX = ".jpg";
    private static final String HEAD_ROOT = "/head/"; // 头像的基础路径
    private static final List<String> VALID_SUFFIX = new ArrayList<>();
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 初始化支持的头像类型
    static {
        VALID_SUFFIX.add("jpg");
        VALID_SUFFIX.add("jpeg");
        VALID_SUFFIX.add("bmp");
        VALID_SUFFIX.add("gif");
    }

    @Autowired
    private AppUserService userService;
    @Autowired
    private WorksService worksService;

    @ApiOperation(value = "上传头像", notes = "上传头像")
    @RequestMapping(value = "/updateHeadImg", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData uploadFile(MultipartFile uploadFile,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResponseData responseData = new ResponseData();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "用户尚未登录。", false);
            return responseData;
        }

        if (uploadFile == null) {
            responseData.jsonFill(2, "请选择文件上传。", false);
            return responseData;
        }
        logger.info("开始上传头像!");
        //如果头像超过2M,getSize获取得是字节数,2M=2*1024*1024byte=2097152byte
        if (uploadFile.getSize() > 2097152L) {
            responseData.jsonFill(2, "头像不允许超过2M", null);
            return responseData;
        }
        //如果头像的格式不符合要求
        if (!VALID_SUFFIX.contains(UploadFileUtil.getSuffix(uploadFile.getOriginalFilename()))) {
            responseData.jsonFill(2, "头像类型只支持jpg,jpeg,bmp,gif。", null);
            return responseData;
        }
        String realPath = UploadFileUtil.getBaseUrl() + HEAD_ROOT;
        String fileName = RandCharsUtils.getRandomString(16) + SUFFIX;

        if (uploadFile == null) {
            responseData.jsonFill(0, "请选择图片上传", null);
            return responseData;
        }

        String url;
        boolean success = UploadFileUtil.mvFile(uploadFile, realPath, fileName);
        if (!success) {
            logger.error("头像处理失败,采用默认头像");
            url = "http://www.warmtale.com/source/head/default_avatar.jpg";
        } else {
            url = OSSUtil.localPathToOss(realPath + fileName);
        }
        if (url == null) {
            logger.error("上传头像失败,采用默认头像");
            url = "http://www.warmtale.com/source/head/default_avatar.jpg";
        }

        User userInDB = userService.getUserByMobileOrId(String.valueOf(user.getId()));
        userInDB.setUpdateTime(new Date());
        userInDB.setHeadImgUrl(url);
        User result = userService.addOrUpdateUser(userInDB);
        if (result == null) {
            responseData.jsonFill(2, "更新失败", null);
            return responseData;
        }

        //更新work数据库表中的HeadImgUrl字段
        worksService.updateHeadImg(user.getId(), url);

        //更新Session中的用户信息
        String AccessToken = request.getHeader(TokenConfig.DEFAULT_ACCESS_TOKEN_HEADER_NAME);
        redisTemplate.opsForValue().set(AccessToken, userInDB);
        responseData.jsonFill(1, null, "success");
        return responseData;
    }

}
