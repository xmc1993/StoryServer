package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.annotation.RequiredPermissions;
import cn.edu.nju.software.entity.App;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.AppService;
import cn.edu.nju.software.service.wxpay.util.RandCharsUtils;
import cn.edu.nju.software.util.DownloadUtil;
import cn.edu.nju.software.util.UploadFileUtil;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Kt on 2017/6/25.
 */
@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class ManageAppController {
    @Autowired
    private AppService appService;
    private static String APP_ROOT = "/apps/";

    @RequiredPermissions({1, 10})
    @ApiOperation(value = "发布App", notes = "")
    @RequestMapping(value = "/publishApp", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> publishApp(
            @ApiParam("版本") @RequestParam("version") String version,
            @ApiParam("更新信息") @RequestParam("updateHint") String updateHint,
            @ApiParam("App") @RequestParam(value = "appFile") MultipartFile appFile,
            @ApiParam("ifUpdate(为1强制更新，为0不强制更新)") @RequestParam(value = "ifUpdate", required = false) Integer ifUpdate) {
        ResponseData<Boolean> result = new ResponseData<>();
        if (appFile.isEmpty()) {
            result.jsonFill(2, "请上传文件", false);
            return result;
        }
        if (version.trim().equals("") || updateHint.trim().equals("")) {
            result.jsonFill(2, "上传信息错误", false);
            return result;
        }
        App app = new App();
        app.setUpdateHint(updateHint.trim());
        app.setVersion(version.trim());
        Date date = new Date();
        app.setCreateTime(date);
        app.setUpdateTime(date);
        app.setUrl(uploadFile(appFile));
        double size = (double) appFile.getSize() / (1024 * 1024);
        DecimalFormat df = new DecimalFormat("#0.00");
        String appSize = df.format(size);
        app.setFileSize(appSize);
        app.setIfUpdate(ifUpdate);
        if (appService.saveApp(app) == false) {
            result.jsonFill(2, "保存失败", false);
        }
        result.jsonFill(1, null, true);
        return result;
    }

    @RequiredPermissions({1, 10})
    @ApiOperation(value = "发布App")
    @RequestMapping(value = "/v3/publishApp", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> publishAppV3(
            @ApiParam("版本") @RequestParam("version") String version,
            @ApiParam("更新信息") @RequestParam("updateHint") String updateHint,
            @ApiParam("App") @RequestParam(value = "appFile") String appFile, HttpServletRequest request,
            @ApiParam("ifUpdate(为1强制更新，为0不强制更新)") @RequestParam(value = "ifUpdate", required = false) Integer ifUpdate) {
        ResponseData<Boolean> result = new ResponseData<>();
        if (appFile.isEmpty()) {
            result.jsonFill(2, "请上传文件", false);
            return result;
        }
        if (version.trim().equals("") || updateHint.trim().equals("")) {
            result.jsonFill(2, "上传信息错误", false);
            return result;
        }
        App app = new App();
        app.setUpdateHint(updateHint.trim());
        app.setVersion(version.trim());
        Date date = new Date();
        app.setCreateTime(date);
        app.setUpdateTime(date);
        app.setUrl(appFile);
        String tempFilePath = DownloadUtil.getTempFile(appFile);
        File tmpFile = new File(tempFilePath);
        double size = (double) tmpFile.length() / (1024 * 1024);
        tmpFile.delete();
        DecimalFormat df = new DecimalFormat("#0.00");
        String appSize = df.format(size);
        app.setFileSize(appSize);
        app.setIfUpdate(ifUpdate);
        if (appService.saveApp(app) == false) {
            result.jsonFill(2, "保存失败", false);
        }
        result.jsonFill(1, null, true);
        return result;
    }

    @RequiredPermissions({3, 10})
    @ApiOperation(value = "修改已发布的App", notes = "除appId外都可为空")
    @RequestMapping(value = "/v3/updateApp", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> updateAppV3(
            @ApiParam("appId") @RequestParam(value = "appId") int appId,
            @ApiParam("版本") @RequestParam(value = "version", required = false) String version,
            @ApiParam("更新信息") @RequestParam(value = "updateHint", required = false) String updateHint,
            @ApiParam("App") @RequestParam(value = "appFile", required = false) String appFile,
            @ApiParam("ifUpdate(为1强制更新，为0不强制更新)") @RequestParam(value = "ifUpdate", required = false) Integer ifUpdate,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> result = new ResponseData<>();
        App app = appService.getAppById(appId);
        if (app == null) {
            result.jsonFill(2, "appId错误", false);
            return result;
        }
        if (!appFile.isEmpty()) {
            String tempFilePath = DownloadUtil.getTempFile(appFile);
            File tmpFile = new File(tempFilePath);
            double size = (double) tmpFile.length() / (1024 * 1024);
            tmpFile.delete();
            DecimalFormat df = new DecimalFormat("#0.00");
            String appSize = df.format(size);
            app.setFileSize(appSize);
            app.setUrl(appFile);
        }
        if (version != null) {
            if (version.trim().equals("")) {
                result.jsonFill(2, "上传信息错误", false);
                return result;
            }
            app.setVersion(version.trim());
        }
        if (updateHint != null) {
            if (updateHint.trim().equals("")) {
                result.jsonFill(2, "上传信息错误", false);
                return result;
            }
            app.setUpdateHint(updateHint.trim());
        }
        app.setUpdateTime(new Date());

        if (ifUpdate != null) {
            app.setIfUpdate(ifUpdate);
        }

        if (appService.updateApp(app) == false) {
            result.jsonFill(2, "保存失败", false);
        }
        result.jsonFill(1, null, true);
        return result;
    }

    @RequiredPermissions({3, 10})
    @ApiOperation(value = "修改已发布的App", notes = "除appId外都可为空")
    @RequestMapping(value = "/updateApp", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> updateApp(
            @ApiParam("appId") @RequestParam(value = "appId") int appId,
            @ApiParam("版本") @RequestParam(value = "version", required = false) String version,
            @ApiParam("更新信息") @RequestParam(value = "updateHint", required = false) String updateHint,
            @ApiParam("App") @RequestParam(value = "appFile", required = false) MultipartFile appFile,
            @ApiParam("ifUpdate(为1强制更新，为0不强制更新)") @RequestParam(value = "ifUpdate", required = false) Integer ifUpdate) {
        ResponseData<Boolean> result = new ResponseData<>();
        App app = appService.getAppById(appId);
        if (app == null) {
            result.jsonFill(2, "appId错误", false);
            return result;
        }
        if (!appFile.isEmpty()) {
            UploadFileUtil.deleteFile(app.getUrl());
            double size = (double) appFile.getSize() / (1024 * 1024);
            DecimalFormat df = new DecimalFormat("#0.00");
            String appSize = df.format(size);
            app.setFileSize(appSize);
            app.setUrl(uploadFile(appFile));
        }
        if (version != null) {
            if (version.trim().equals("")) {
                result.jsonFill(2, "上传信息错误", false);
                return result;
            }
            app.setVersion(version.trim());
        }
        if (updateHint != null) {
            if (updateHint.trim().equals("")) {
                result.jsonFill(2, "上传信息错误", false);
                return result;
            }
            app.setUpdateHint(updateHint.trim());
        }
        app.setUpdateTime(new Date());

        if (ifUpdate != null) {
            app.setIfUpdate(ifUpdate);
        }

        if (appService.updateApp(app) == false) {
            result.jsonFill(2, "保存失败", false);
        }
        result.jsonFill(1, null, true);
        return result;
    }

    @RequiredPermissions({2, 10})
    @ApiOperation(value = "删除已发布的App", notes = "")
    @RequestMapping(value = "/deleteApp", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> deleteApp(@ApiParam("appId") @RequestParam(value = "appId") int appId,
                                           HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> result = new ResponseData<>();
        App app = appService.getAppById(appId);
        if (app == null) {
            result.jsonFill(2, "appId错误", false);
            return result;
        }
        appService.deleteApp(appId);
        UploadFileUtil.deleteFileByUrl(app.getUrl());
        result.jsonFill(1, null, true);
        return result;
    }

    @RequiredPermissions({2, 10})
    @ApiOperation(value = "批量删除已发布的App", notes = "")
    @RequestMapping(value = "/deleteApps", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> deleteApp(@ApiParam("appIds") @RequestParam(value = "appIds") int[] appIds,
                                           HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> result = new ResponseData<>();
        for (int i = 0; i < appIds.length; i++) {
            App app = appService.getAppById(appIds[i]);
            if (app == null) {
                result.jsonFill(2, "appId错误", false);
                return result;
            }
            appService.deleteApp(appIds[i]);
            UploadFileUtil.deleteFileByUrl(app.getUrl());
        }
        result.jsonFill(1, null, true);
        return result;
    }

    @RequiredPermissions({4, 10})
    @ApiOperation(value = "APP分页查询按创建时间倒序", notes = "")
    @RequestMapping(value = "/getApps", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<App>> getApps(@ApiParam("offset") @RequestParam(value = "offset") int offset,
                                           @ApiParam("limit") @RequestParam(value = "limit") int limit, HttpServletRequest request,
                                           HttpServletResponse response) {
        List<App> appList = appService.getAppListByPageDesc(offset, limit);
        ResponseData<List<App>> result = new ResponseData<>();
        result.jsonFill(1, null, appList);
        result.setCount(appService.getAppCount());
        return result;
    }

    private String uploadFile(MultipartFile file) {
        String realPath = UploadFileUtil.getBaseUrl() + APP_ROOT;
        String fileName = RandCharsUtils.getRandomString(16) + "."
                + UploadFileUtil.getSuffix(file.getOriginalFilename());
        boolean success = UploadFileUtil.mvFile(file, realPath, fileName);
        if (!success) {
            throw new RuntimeException("文件上传失败");
        }
        String url = UploadFileUtil.SOURCE_BASE_URL + APP_ROOT + fileName;
        return url;
    }

    @RequiredPermissions({4, 10})
    @ApiOperation(value = "根据appId获取app详情", notes = "")
    @RequestMapping(value = "/getAppDetails", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<App> getAppDetails(@ApiParam("id") @RequestParam(value = "id") Integer id,
                                           HttpServletRequest request, HttpServletResponse response) {
        ResponseData<App> result = new ResponseData<>();
        App app = appService.getAppById(id);
        if (app == null) {
            result.jsonFill(2, "appId错误", null);
            return result;
        }
        result.jsonFill(1, null, app);
        return result;
    }

    @ApiOperation(value = "获取最新app的url")
    @RequestMapping(value = "/getLastApp", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<String> getLastApp() {
        ResponseData<String> result = new ResponseData<>();
        result.jsonFill(1, null, appService.getLastApp().getUrl());
        return result;
    }
}
