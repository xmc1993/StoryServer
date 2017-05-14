package cn.edu.nju.software.controller;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryTag;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Api("tag controller")
@Controller
public class TagController extends BaseController {


    @ApiOperation(value = "获得所有一级标签", notes = "获得所有一级标签")
    @RequestMapping(value = "/user/getAllOneLevelTags", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<StoryTag>> getAllOneLevelTags(
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @ApiOperation(value = "根据父标签ID（目前是根据一级标签id或者二级标签）获得下一级标签列表", notes = "")
    @RequestMapping(value = "/user/getTagListByParentId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<StoryTag>> getTagListByParentId(
            @ApiParam("父标签ID") @RequestParam("parentId") String parentId,
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }


}
