package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.*;
import cn.edu.nju.software.service.*;
import cn.edu.nju.software.util.TokenConfig;
import cn.edu.nju.software.vo.StoryNewVo;
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

@Api("tag controller")
@Controller
@RequestMapping("/user")
public class UserAlbumController extends BaseController {
    @Autowired
    private AlbumRelationService albumRelationService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private StoryService storyService;
    @Autowired
    private StoryComplexService complexService;

    @ApiOperation(value = "获得故事所属的所有专辑", notes = "")
    @RequestMapping(value = "/getAlbumListOfStory", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Album>> getAlbumListOfStory(
            @ApiParam("故事ID") @RequestParam("storyId") int storyId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<Album>> responseData = new ResponseData<>();
        List<Integer> idList = albumRelationService.getAlbumIdListByStoryId(storyId);
        List<Album> storyAlbumList = albumService.getAlbumListByIdList(idList);
        responseData.jsonFill(1, null, storyAlbumList);
        responseData.setCount(storyAlbumList.size());
        return responseData;
    }

    @ApiOperation(value = "获得专辑下的所有故事", notes = "")
    @RequestMapping(value = "/getStoryListOfAlbum", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<StoryNewVo>> getStoryListOfAlbum(
            @ApiParam("专辑ID") @RequestParam("albumId") int albumId,
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<StoryNewVo>> responseData = new ResponseData();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            user = new User();
            user.setId(-1);
        }
        List<Integer> idList = albumRelationService.getStoryIdListByAlbumId(albumId);
        List<Story> storyList = storyService.getStoryListByIdList(idList, offset, limit);
        responseData.jsonFill(1, null, complexService.getStoryNewVoListByStoryList(storyList, user.getId()));
        responseData.setCount(storyService.getStoryCountByIdList(idList));
        return responseData;
    }

}
