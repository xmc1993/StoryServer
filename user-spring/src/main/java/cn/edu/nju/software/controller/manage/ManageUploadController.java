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

/**
 * Created by xmc1993 on 2017/5/15.
 */

@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class ManageUploadController {

    private static final String ICON_ROOT = "/icons/";

    @ApiOperation(value = "上传icon", notes = "")
    @RequestMapping(value = "/uploadIcon", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<UploadResVo> logout(
            @ApiParam("ICON") @RequestParam(value = "icon", required = true) MultipartFile icon,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<UploadResVo> responseData = new ResponseData<>();
        String url = uploadFile(icon);
        if (url == null){
            responseData.jsonFill(2, "上传失败", null);
        }else {
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
    private String uploadFile(MultipartFile file) {
        String realPath = UploadFileUtil.getBaseUrl() + ICON_ROOT;
        String fileName = RandCharsUtils.getRandomString(16) + "." + UploadFileUtil.getSuffix(file.getOriginalFilename());
        boolean success = UploadFileUtil.mvFile(file, realPath, fileName);
        if (!success) {
            throw new RuntimeException("文件上传失败");
        }
        String url = UploadFileUtil.SOURCE_BASE_URL + ICON_ROOT + fileName;
        return url;
    }
}
