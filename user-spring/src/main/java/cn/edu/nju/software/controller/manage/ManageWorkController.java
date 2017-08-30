package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.Works;
import cn.edu.nju.software.service.WorksService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理作品接口
 *
 * @author liuyu
 * @create 2017-08-30 上午10:43
 */
@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class ManageWorkController {
    private static final Logger logger = LoggerFactory.getLogger(ManageStoryController.class);
    @Autowired
    private WorksService worksService;

    @ApiOperation(value = "根据id获取故事", notes = "")
    @RequestMapping(value = "/getWorkById", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Works> getWorkById(
            @ApiParam("故事标题") @RequestParam(value = "id") Integer id,
            HttpServletRequest request, HttpServletResponse response){
        ResponseData<Works> responseData = new ResponseData<>();
        Works works = worksService.getWorksById(id);
        String errorMessage = null;
        if (works == null) {
            errorMessage = "专辑不存在";
        }
        responseData.jsonFill(1,errorMessage,works);
        return responseData;
    }
}
