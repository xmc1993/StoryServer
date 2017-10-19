package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.manage.ManageInitImageController;
import cn.edu.nju.software.entity.InitImage;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.InitImageService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zj
 */
@Api(value = "User", description = "获取开屏图接口")
@Controller
@RequestMapping("/user/initImage")
public class UserInitImageController {
    private static final Logger logger = LoggerFactory.getLogger(ManageInitImageController.class);

    @Autowired
    private InitImageService initImageService;

    @ApiOperation(value = "获取开屏图", notes = "")
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

}
