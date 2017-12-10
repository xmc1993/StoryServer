package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.WorkTag;
import cn.edu.nju.software.service.user.WorkTagService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author zj
 */
@Api(value = "admin", description = "后台管理作品标签")
@Controller
@RequestMapping("/manage/workTag")
public class ManageWorkTagController {
    private static final Logger logger = LoggerFactory.getLogger(ManageOperationLogController.class);
    @Autowired
    private WorkTagService workTagService;



    @ApiOperation(value = "添加作品标签", notes = "")
    @RequestMapping(value = "insertWorkTag", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<WorkTag> insertWorkTag(
            @ApiParam("标签中的内容") @RequestParam("content") String content) {

        Integer authorId=0;//后台用户上传的标签，作者id设为0
        return workTagService.insertWorkTag(content,authorId);
    }

    @ApiOperation(value = "获取所有的作品标签", notes = "")
    @RequestMapping(value = "selectAllWorkTag", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<WorkTag>> selectAllWorkTag() {
        List<WorkTag> workTagList = workTagService.selectAll();
        ResponseData<List<WorkTag>> responseData = new ResponseData<>();
        responseData.jsonFill(1, null, workTagList);
        responseData.setCount(workTagList.size());
        return responseData;
    }

    @ApiOperation(value = "通过ID获取作品标签", notes = "")
    @RequestMapping(value = "selectWorkTagById", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<WorkTag> selectWorkTagById(@ApiParam("ID") @RequestParam("id") Integer id) {
        return workTagService.selectWorkTagById(id);
    }

    @ApiOperation(value = "更新作品标签", notes = "")
    @RequestMapping(value = "update", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> update(
            @ApiParam("作品标签ID") @RequestParam("id") Integer id,
            @ApiParam("标签中的内容") @RequestParam(value = "content",required = false) String content,
            @ApiParam("是否可用，用于软删除") @RequestParam(value = "valid",required = false) Integer valid) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        WorkTag workTag = new WorkTag();
        workTag.setId(id);
        workTag.setContent(content);
        workTag.setUpdateTime(new Date());
        workTag.setValid(valid);

        WorkTag workTagInDb = workTagService.selectByContent(content);
        if (workTagInDb!=null) {
            responseData.jsonFill(2, "已存在相同内容的作品标签", false);
            return responseData;
        }
        int res=workTagService.update(workTag);
        if (res == 0) {
            responseData.jsonFill(2, "更新失败", false);
            return responseData;
        }
        responseData.jsonFill(1, null, true);
        return responseData;
    }

    @ApiOperation(value = "通过id删除作品标签", notes = "")
    @RequestMapping(value = "deleteWorkTag/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    public ResponseData<Boolean> deleteWorkTag(@ApiParam("ID") @PathVariable Integer id) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        int res = workTagService.deleteWorkTag(id);
        if (res == 0) {
            responseData.jsonFill(2, "删除失败", false);
            return responseData;
        }
        responseData.jsonFill(1, "删除成功", true);
        return responseData;
    }

}
