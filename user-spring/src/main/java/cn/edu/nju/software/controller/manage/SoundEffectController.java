package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.SoundEffect;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/15.
 */
@Api(value = "/story", description = "和故事有关的接口")
@Controller
public class SoundEffectController {

    @ApiOperation(value = "增加音效", notes = "")
    @RequestMapping(value = "/manage/publishSoundEffect", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> publishSoundEffect(
            @ApiParam("音效文件") @RequestParam("uploadFile") MultipartFile uploadFile,
            @ApiParam("音效描述") @RequestParam("description") String description,
            HttpServletRequest request, HttpServletResponse response) {
            
        return null;
    }

    @ApiOperation(value = "更新音效", notes = "")
    @RequestMapping(value = "/manage/updateSoundEffect", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> updateSoundEffect(
            @ApiParam("音效文件") @RequestParam("uploadFile") MultipartFile uploadFile,
            @ApiParam("音效描述") @RequestParam("description") String description,
            HttpServletRequest request, HttpServletResponse response) {
        //TODO 删除旧的音效文件
        return null;
    }

    @ApiOperation(value = "删除音效", notes = "")
    @RequestMapping(value = "/manage/deleteSoundEffect", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> deleteSoundEffect(
            @ApiParam("音效ID") @RequestParam("id") String id,
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @ApiOperation(value = "得到所有音效", notes = "")
    @RequestMapping(value = "/manage/getAllSoundEffects", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<SoundEffect>> getAllSoundEffects(
            HttpServletRequest request, HttpServletResponse response) {
        //todo 是否要分页
        return null;
    }

}
