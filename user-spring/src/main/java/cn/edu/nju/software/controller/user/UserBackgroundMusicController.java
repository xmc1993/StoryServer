package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.BackgroundMusic;
import cn.edu.nju.software.service.BackgroundMusicService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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

@Api(value = "/backgroundMusic", description = "和背景音乐有关的接口")
@Controller
@RequestMapping("/user")
public class UserBackgroundMusicController extends BaseController {

    @Autowired
    private BackgroundMusicService backgroundMusicService;

    @ApiOperation(value = "获得背景音乐列表", notes = "")
    @RequestMapping(value = "/getBackgroundMusicListByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<BackgroundMusic>> getBackgroundMusicListByPage(
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<BackgroundMusic>> responseData = new ResponseData();
        List<BackgroundMusic> backgroundMusicList = backgroundMusicService.getBackgroundMusicListByPage(offset, limit);
        responseData.jsonFill(1, null, backgroundMusicList);
        return responseData;
    }

    @ApiOperation(value = "通过ID获得背景音", notes = "")
    @RequestMapping(value = "/getBackgroundMusicById", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<BackgroundMusic> getBackgroundMusicById(
            @ApiParam("ID") @RequestParam int id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<BackgroundMusic> responseData = new ResponseData();
        BackgroundMusic backgroundMusic = backgroundMusicService.getBackgroundMusicById(id);
        if (backgroundMusic == null) {
            responseData.jsonFill(2, "背景音不存在", null);
            return responseData;
        }
        responseData.jsonFill(1, null, backgroundMusic);
        return responseData;
    }


}
