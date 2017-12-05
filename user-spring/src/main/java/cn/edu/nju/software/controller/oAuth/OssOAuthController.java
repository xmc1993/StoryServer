package cn.edu.nju.software.controller.oAuth;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.OssService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Api("tag controller")
@Controller
@RequestMapping("/auth")
public class OssOAuthController {
    @Autowired
    private OssService ossService;

    @ApiOperation(value = "获取oss上传的signature")
    @RequestMapping(value = "/getOssSignature", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Map<String, String>> getJsSdkSignature(
            HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        ResponseData<Map<String, String>> responseData = new ResponseData();
        Map<String, String> signature = ossService.getToken();
        responseData.jsonFill(1, null, signature);
        return responseData;
    }

    @ApiOperation(value = "获取oss上传的signature")
    @RequestMapping(value = "/getAppOssToken", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Map<String, String>> getAppOssToken(
            HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        ResponseData<Map<String, String>> responseData = new ResponseData();
        Map<String, String> signature = ossService.getAppToken();
        responseData.jsonFill(1, null, signature);
        return responseData;
    }

}
