package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.wxpay.util.RandCharsUtils;
import cn.edu.nju.software.util.UploadFileUtil;
import cn.edu.nju.software.vo.UEditorVo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by zhangsong on 2017/11/9.
 */

@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class ManageUEditorController {

    //UEditor放的文件的基础路径
    private static final String ROOT = "/UEditor/";

    @ApiOperation(value = "上传UEditor的图片")
    @RequestMapping(value = "/uploadUEditorImage", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<UEditorVo> uploadUEditorImage(@ApiParam("图片文件") @RequestParam MultipartFile file
    ) {
        ResponseData<UEditorVo> responseData = new ResponseData<>();

        UEditorVo uEditor = new UEditorVo();

        if (file == null) {
            responseData.jsonFill(2, "请选择音频文件上传。", null);
            return responseData;
        }
        String url = uploadFile(file, "Image");
        if (url == null) {
            responseData.jsonFill(2, "文件上传失败", null);
            return responseData;
        } else {
            uEditor.setState("SUCCESS");
            uEditor.setUrl(url);
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            uEditor.setTitle(fileName);
            uEditor.setOriginal(fileName);
            responseData.jsonFill(1, null, uEditor);
            return responseData;
        }
    }

    @ApiOperation(value = "上传UEditor的视频")
    @RequestMapping(value = "/uploadUEditorVideo", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<UEditorVo> uploadUEditorVideo(@ApiParam("视频文件") @RequestParam MultipartFile file
    ) {
        ResponseData<UEditorVo> responseData = new ResponseData<>();

        UEditorVo uEditor = new UEditorVo();

        if (file == null) {
            responseData.jsonFill(2, "请选择视频文件上传。", null);
            return responseData;
        }
        String url = uploadFile(file, "Video");
        if (url == null) {
            responseData.jsonFill(2, "视频文件上传失败", null);
            return responseData;
        } else {
            uEditor.setState("SUCCESS");
            uEditor.setUrl(url);
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            uEditor.setTitle(fileName);
            uEditor.setOriginal(fileName);
            responseData.jsonFill(1, null, uEditor);
            return responseData;
        }
    }


    /**
     * UEditor文件上传
     *
     * @param file
     * @return
     */
    private String uploadFile(MultipartFile file, String type) {
        switch (type) {
            case "Image":
                String realPath = UploadFileUtil.getBaseUrl() + ROOT + "Image" + "/";
                String fileName = RandCharsUtils.getRandomString(16) + "."
                        + UploadFileUtil.getSuffix(file.getOriginalFilename());
                boolean success = UploadFileUtil.mvFile(file, realPath, fileName);
                if (!success) {
                    throw new RuntimeException("文件上传失败");
                }
                String url = UploadFileUtil.SOURCE_BASE_URL + ROOT + "Image" + "/" + fileName;
                return url;

            case "Video":
                String realPath2 = UploadFileUtil.getBaseUrl() + ROOT + "Video" + "/";
                String fileName2 = RandCharsUtils.getRandomString(16) + "."
                        + UploadFileUtil.getSuffix(file.getOriginalFilename());
                boolean success2 = UploadFileUtil.mvFile(file, realPath2, fileName2);
                if (!success2) {
                    throw new RuntimeException("文件上传失败");
                }
                String url2 = UploadFileUtil.SOURCE_BASE_URL + ROOT + "Video" + "/" + fileName2;
                return url2;
        }

    return null;
}


}
