package cn.edu.nju.software.action.user;

import cn.edu.nju.software.action.BaseController;
import cn.edu.nju.software.entity.ResponseData;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Api("download file controller")
@Controller
public class DownloadFileController extends BaseController {

    private static final long serialVersionUID = 4475596889474804743L;
    private static final Logger logger = LoggerFactory.getLogger(DownloadFileController.class);

    @ApiOperation(value = "下载图片", notes = "下载图片")
    @ResponseBody
    @RequestMapping(value = "/user/downloadFile", method={RequestMethod.POST})
    public ResponseData<Integer> downloadFile(@ApiParam("AppId") @RequestParam("appId") String appId,
                                     @ApiParam("手机号") @RequestParam("mobile") String mobile,
                                     @ApiParam("路径") @RequestParam("filePath") String filePath,
                                     HttpServletRequest request,HttpServletResponse response) throws Exception {
        logger.info("Download File");
        ResponseData responseData = new ResponseData();
        String realPath = request.getServletContext().getRealPath("/");
        File destFile = new File(new File(realPath), filePath);
        if (destFile.exists()) {
            response.setContentType("image/png");
            OutputStream outputStream = response.getOutputStream();
            InputStream inputStream = request.getServletContext().getResourceAsStream(filePath);
            int ch = 0;
            try {
                while ((ch = inputStream.read()) != -1) {
                    outputStream.write(ch);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        responseData.setErrorMes("不存在图片");
        responseData.setStatus(0);
        return responseData;
    }

}
