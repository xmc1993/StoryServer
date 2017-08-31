package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.user.AppUserService;
import cn.edu.nju.software.service.wxpay.util.RandCharsUtils;
import cn.edu.nju.software.util.JedisUtil;
import cn.edu.nju.software.util.ObjectAndByte;
import cn.edu.nju.software.util.TokenConfig;
import cn.edu.nju.software.util.UploadFileUtil;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

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
    private final String default_avatar = "default_avatar.jpg";
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
        //如果头像超过2M
//        if (uploadFile.getSize() > 2048L) {
//            responseData.jsonFill(2, "头像不允许超过2M", null);
//            return responseData;
//        }
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

        boolean success = UploadFileUtil.mvFile(uploadFile, realPath, fileName);
        if (!success) {
            logger.error("上传头像失败!");
            responseData.jsonFill(2, "上传头像失败", null);
            return responseData;
        }

        String url = UploadFileUtil.SOURCE_BASE_URL + HEAD_ROOT + fileName;
        User userInDB = userService.getUserByMobileOrId(String.valueOf(user.getId()));
        userInDB.setUpdateTime(new Date());
        String oldHeadImgUrl = userInDB.getHeadImgUrl();
        userInDB.setHeadImgUrl(url);
        User result = userService.addOrUpdateUser(userInDB);
        if (result == null) {
            responseData.jsonFill(2, "更新失败", null);
            return responseData;
        }

        //删除旧的头像
        UploadFileUtil.deleteFileByUrl(oldHeadImgUrl);
        //更新Session中的用户信息
        String AccessToken = request.getHeader(TokenConfig.DEFAULT_ACCESS_TOKEN_HEADER_NAME);
        Jedis jedis = JedisUtil.getJedis();
        jedis.set(AccessToken.getBytes(), ObjectAndByte.toByteArray(userInDB));
        jedis.close();
        responseData.jsonFill(1, null, "success");
        return responseData;
    }


}
