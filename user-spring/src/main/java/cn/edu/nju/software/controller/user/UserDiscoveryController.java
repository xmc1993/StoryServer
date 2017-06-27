package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.entity.Discovery;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.DiscoveryService;
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
 * Created by Kt on 2017/6/27.
 */
@Api("discovery controller")
@Controller
@RequestMapping("/user")
public class UserDiscoveryController {
    @Autowired
    private DiscoveryService discoveryService;

    @ApiOperation("分页获取发现列表 随机项目 excludeIdList防止访问到重复数据")
    @RequestMapping(value = "/discoveries",method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Discovery>> getDiscoveriesByPage(
            @ApiParam("排除已经获取过的Id 可以为空") @RequestParam(value = "excludeIdList",required = false) int[] excludeIdList,
            @ApiParam("offset") @RequestParam("offset") int offset,
            @ApiParam("limit") @RequestParam("limit") int limit,
            HttpServletRequest request,HttpServletResponse response){
        ResponseData<List<Discovery>> result = new ResponseData<>();
        for(int id:excludeIdList){
            System.out.println("测试"+id);
        }
        List<Discovery> discoveryList = discoveryService.getDiscoveryByRandomPage(offset,limit,excludeIdList);
        result.jsonFill(1,null,discoveryList);
        result.setCount(discoveryService.getDiscoveryCount());
        return result;
    }
    @ApiOperation("通过ID获取发现")
    @RequestMapping(value = "/discovery",method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Discovery> getDiscoveryById(
            @ApiParam("id") @RequestParam("id") int id,
            HttpServletRequest request,HttpServletResponse response){
        ResponseData<Discovery> result = new ResponseData<>();
        Discovery discovery = discoveryService.getDiscoveryById(id);
        if(discovery==null) throw new RuntimeException("错误的发现id");
        result.jsonFill(1,null,discovery);
        return result;
    }
}
