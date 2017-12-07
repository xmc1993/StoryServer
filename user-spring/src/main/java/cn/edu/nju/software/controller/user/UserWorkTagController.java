package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.manage.ManageOperationLogController;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.entity.WorkTag;
import cn.edu.nju.software.service.user.WorkTagService;
import cn.edu.nju.software.util.TokenConfig;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author zj
 */
@Api(value = "User", description = "用户端作品标签相关接口")
@Controller
@RequestMapping("/user/workTag")
public class UserWorkTagController {
    private static final Logger logger = LoggerFactory.getLogger(UserWorkTagController.class);
    @Autowired
    private WorkTagService workTagService;

    @ApiOperation(value = "添加用户自定义作品标签", notes = "")
    @RequestMapping(value = "insertWorkTagUserDefined", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<WorkTag> insertWorkTagUserDefined(
            @ApiParam("标签中的内容") @RequestParam("content") String content, HttpServletRequest request) {

        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        Integer authorId=user.getId();
        return workTagService.insertWorkTag(content,authorId);
    }

    @ApiOperation(value = "获取所有推荐标签和该用户使用过的标签", notes = "")
    @RequestMapping(value = "selectTagsRecommendedAndCustomized", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<WorkTag>> selectTagsRecommendedAndCustomized(HttpServletRequest request) {
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        List<WorkTag> workTagList = workTagService.selectTagsRecommendedAndCustomized(user.getId());
        ResponseData<List<WorkTag>> responseData = new ResponseData<>();
        responseData.jsonFill(1, null, workTagList);
        responseData.setCount(workTagList.size());
        return responseData;
    }

    @ApiOperation(value = "通过ID获取标签", notes = "")
    @RequestMapping(value = "selectById", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<WorkTag> selectById(@ApiParam("ID") @RequestParam("id") Integer id) {
        return workTagService.selectWorkTagById(id);
    }

    @ApiOperation(value = "删除当前用户自定义的所有标签", notes = "")
    @RequestMapping(value = "deleteWorkTagUserDefined", method = {RequestMethod.DELETE})
    @ResponseBody
    public ResponseData<Boolean> deleteWorkTagUserDefined(HttpServletRequest request) {
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        int res = workTagService.deleteByAuthorId(user.getId());
        ResponseData<Boolean> responseData = new ResponseData<>();

        if (res == 0) {
            responseData.jsonFill(2, "删除失败", false);
            return responseData;
        }
        responseData.jsonFill(1, "删除成功", true);
        return responseData;
    }

}
