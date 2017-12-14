package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.wxpay.util.RandCharsUtils;
import cn.edu.nju.software.util.UploadFileUtil;
import cn.edu.nju.software.vo.UploadResVo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
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

/**
 * Created by xmc1993 on 2017/5/15.
 */

@Api(value = "upload", description = "管理接口")
@Controller
@RequestMapping("/user/upload")
public class UserUploadController {

    private static final String ICON_ROOT = "/icons/";
    private static final String AUDIO_ROOT = "/audios/";
    //意见反馈的图片
    private static final String OPINION_ROOT = "/opinion/";
    //评论的图片
    private static final String COMMENT_ROOT="/comment/";

    @ApiOperation(value = "上传icon", notes = "")
    @RequestMapping(value = "/uploadIcon", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<UploadResVo> uploadIcon(
            @ApiParam("ICON") @RequestParam(value = "icon", required = true) MultipartFile icon,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<UploadResVo> responseData = new ResponseData<>();
        String url = uploadFile(icon, ICON_ROOT);
        if (url == null) {
            responseData.jsonFill(2, "上传失败", null);
        } else {
            UploadResVo uploadResVo = new UploadResVo();
            uploadResVo.setUrl(url);
            responseData.jsonFill(1, null, uploadResVo);
        }
        return responseData;
    }

    @ApiOperation(value = "上传多个icon", notes = "")
    @RequestMapping(value = "/uploadIcons", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<UploadResVo> uploadIcons(
            @ApiParam("ICON") @RequestParam(value = "icons") MultipartFile[] icons) {
        ResponseData<UploadResVo> responseData = new ResponseData<>();
        List<String> list = new ArrayList<>();
        for (MultipartFile icon : icons) {
            String url = uploadFile(icon, ICON_ROOT);
            if (url == null) {
                responseData.jsonFill(2, "上传失败", null);
                return responseData;
            }
            list.add(url);
        }
        UploadResVo uploadResVo=new UploadResVo();
        uploadResVo.setMultiUrls(list);
        responseData.jsonFill(1, null, uploadResVo);
        return responseData;
    }

    @ApiOperation(value = "上传多个意见反馈的图片", notes = "")
    @RequestMapping(value = "/uploadOpinionPics", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<UploadResVo> uploadOpinionPics(
            @ApiParam("opinionPics") @RequestParam(value = "opinionPics") MultipartFile[] opinionPics) {
        ResponseData<UploadResVo> responseData = new ResponseData<>();
        List<String> list = new ArrayList<>();
        for (MultipartFile opinionPic : opinionPics) {
            String url = uploadFile(opinionPic, OPINION_ROOT);
            if (url == null) {
                responseData.jsonFill(2, "上传失败", null);
                return responseData;
            }
            list.add(url);
        }
        UploadResVo uploadResVo = new UploadResVo();
        uploadResVo.setMultiUrls(list);
        responseData.jsonFill(1, null, uploadResVo);
        return responseData;
    }

    @ApiOperation(value = "上传多个评论的图片", notes = "")
    @RequestMapping(value = "/uploadCommentPics", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<UploadResVo> uploadCommentPics(
            @ApiParam("commentPics") @RequestParam(value = "commentPics") MultipartFile[] commentPics) {
        ResponseData<UploadResVo> responseData = new ResponseData<>();
        List<String> list = new ArrayList<>();
        for (MultipartFile commentPic : commentPics) {
            String url = uploadFile(commentPic, OPINION_ROOT);
            if (url == null) {
                responseData.jsonFill(2, "上传失败", null);
                return responseData;
            }
            list.add(url);
        }
        UploadResVo uploadResVo = new UploadResVo();
        uploadResVo.setMultiUrls(list);
        responseData.jsonFill(1, null, uploadResVo);
        return responseData;
    }


    @ApiOperation(value = "批量上传文件", notes = "")
    @RequestMapping(value = "/uploadMulti", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<UploadResVo> uploadMulti(
            @ApiParam("多文件") @RequestParam(value = "files", required = true) MultipartFile[] files,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<UploadResVo> responseData = new ResponseData<>();
        UploadResVo uploadResVo = new UploadResVo();
        ArrayList<String> urls = new ArrayList<>();
        uploadResVo.setMultiUrls(urls);
        responseData.jsonFill(1, null, uploadResVo);
        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    urls.add(uploadFile(file, ICON_ROOT));
                }
            }
        }

        return responseData;
    }


    @ApiOperation(value = "上传音频", notes = "")
    @RequestMapping(value = "/uploadAudio", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<UploadResVo> uploadAudio(
            @ApiParam("ICON") @RequestParam(value = "audio", required = true) MultipartFile audio,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<UploadResVo> responseData = new ResponseData<>();
        String url = uploadFile(audio, AUDIO_ROOT);
        if (url == null) {
            responseData.jsonFill(2, "上传失败", null);
        } else {
            UploadResVo uploadResVo = new UploadResVo();
            uploadResVo.setUrl(url);
            responseData.jsonFill(1, null, uploadResVo);
        }
        return responseData;
    }


    /**
     * 上传ICON文件
     *
     * @param file
     * @return
     */
    private String uploadFile(MultipartFile file, String root) {
        String realPath = UploadFileUtil.getBaseUrl() + root;
        String fileName = RandCharsUtils.getRandomString(16) + "." + UploadFileUtil.getSuffix(file.getOriginalFilename());
        boolean success = UploadFileUtil.mvFile(file, realPath, fileName);
        if (!success) {
            throw new RuntimeException("文件上传失败");
        }
        String url = UploadFileUtil.SOURCE_BASE_URL + root + fileName;
        return url;
    }

    private String uploadFile(MultipartFile file, String root, String suffix) {
        String realPath = UploadFileUtil.getBaseUrl() + root;
        String fileName = RandCharsUtils.getRandomString(16) + "." + suffix;
        boolean success = UploadFileUtil.mvFile(file, realPath, fileName);
        if (!success) {
            throw new RuntimeException("文件上传失败");
        }
        String url = UploadFileUtil.SOURCE_BASE_URL + root + fileName;
        return url;
    }

}
