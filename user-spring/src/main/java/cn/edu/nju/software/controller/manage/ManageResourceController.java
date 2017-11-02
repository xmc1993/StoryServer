package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.Resource;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.enums.ResourceType;
import cn.edu.nju.software.service.ResourceService;
import cn.edu.nju.software.util.UploadFileUtil;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/15.
 */
@Api(value = "Admin", description = "管理接口")
@Controller()
@RequestMapping("/manage")
public class ManageResourceController {
    private static final Logger logger = LoggerFactory.getLogger(ManageResourceController.class);
    private static final String LIBRARY_PATH = "/library/";

    @Autowired
    private ResourceService resourceService;

    @ApiOperation(value = "新增文件项")
    @RequestMapping(value = "/resources", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Resource publishResource(
            @ApiParam("文件描述") @RequestParam(value = "description", required = false) String description,
            @ApiParam("文件类型") @RequestParam ResourceType resourceType,
            @ApiParam("文件") @RequestParam MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("文件不能为空");
        }

        String url = UploadFileUtil.uploadFile(file, LIBRARY_PATH + resourceType.getName() + "/");
        if (url == null) {
            throw new RuntimeException("上传失败");
        }
        Resource resource = new Resource();
        resource.setCreateTime(new Date());
        resource.setUpdateTime(new Date());
        resource.setResourceType(resourceType);
        resource.setUrl(url);
        resource.setDescription(description);
        resource.setName(file.getOriginalFilename());
        resourceService.saveResource(resource);
        return resource;
    }

    @ApiOperation(value = "删除文件项")
    @RequestMapping(value = "/resources/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResource(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        boolean success = resourceService.deleteResource(id);
        if (!success) {
            throw new RuntimeException("删除失败");
        }
    }

    @ApiOperation(value = "根据ID获得文件项")
    @RequestMapping(value = "/resources/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public Resource getStoryById(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        Resource resource = resourceService.getResourceById(id);
        if (resource == null) {
            throw new RuntimeException("无效的ID");
        } else {
            return resource;
        }
    }

    @ApiOperation(value = "分页获得文件列表")
    @RequestMapping(value = "/getResourceListByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Resource>> getResourceListByPage(
            @ApiParam("PAGE") @RequestParam int page,
            @ApiParam("SIZE") @RequestParam int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<Resource>> responseData = new ResponseData<>();
        List<Resource> resourceList = resourceService.getAllResourceByPage(page, pageSize);
        responseData.jsonFill(1, null, resourceList);
        responseData.setCount(resourceService.getAllResourceCount());
        return responseData;
    }


}
