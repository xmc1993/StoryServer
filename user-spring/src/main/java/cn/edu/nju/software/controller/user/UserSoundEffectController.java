package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.SoundEffect;
import cn.edu.nju.software.service.SoundEffectService;
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

@Api(value = "/soundEffect", description = "和音效有关的接口")
@Controller
@RequestMapping("/user")
public class UserSoundEffectController extends BaseController {

    @Autowired
    private SoundEffectService soundEffectService;


    @ApiOperation(value = "获得音效列表", notes = "")
    @RequestMapping(value = "/getSoundEffectListByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<SoundEffect>> getSoundEffectListByPage(
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<SoundEffect>> responseData = new ResponseData();
        List<SoundEffect> soundEffectList = soundEffectService.getSoundEffectListByPage(offset, limit);
        responseData.jsonFill(1, null, soundEffectList);
        return responseData;
    }




}
