package cn.edu.nju.software.controller.manage;

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

/**
 * Created by xmc1993 on 2017/5/15.
 */

@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class ManageUploadController {

    private static final String ICON_ROOT = "/icons/";
    private static final String AUDIO_ROOT = "/audios/";

    @ApiOperation(value = "上传icon", notes = "")
    @RequestMapping(value = "/uploadIcon", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<UploadResVo> uploadIcon(
            @ApiParam("ICON") @RequestParam(value = "文件列表", required = true) MultipartFile icon,
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
        if (files != null){
            for (MultipartFile file : files) {
                if (!file.isEmpty()){
                    urls.add(uploadFile(file, ICON_ROOT));
                }
            }
        }

        return responseData;
    }

    @ApiOperation(value = "上传icon", notes = "")
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
}
