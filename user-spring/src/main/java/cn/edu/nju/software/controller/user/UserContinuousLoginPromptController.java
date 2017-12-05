package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.user.ContinuousLoginPromptService;
import cn.edu.nju.software.util.JedisUtil;
import cn.edu.nju.software.util.TokenConfig;
import cn.edu.nju.software.vo.ContinuousLoginPromptVo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zj
 */
@Api("user controller")
@Controller
@RequestMapping("/user/continuousLoginPrompt")
public class UserContinuousLoginPromptController {
    private static final Logger logger = LoggerFactory.getLogger(UserContinuousLoginPromptController.class);
    @Autowired
    private ContinuousLoginPromptService continuousLoginPromptService;

    @ApiOperation(value = "获取连续登录提示", notes = "当用户没登录时，访问不了这个接口，也不需要访问这个接口")
    @RequestMapping(value = "/selectContinuousLoginPrompt", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<ContinuousLoginPromptVo> selectContinuousLoginPrompt(HttpServletRequest request) {
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        Integer userId = user.getId();
        String prompt = continuousLoginPromptService.selectPromptIntegrated(userId);
        ContinuousLoginPromptVo continuousLoginPromptVo = new ContinuousLoginPromptVo();
        continuousLoginPromptVo.setPrompt(prompt);
        Jedis jedis = JedisUtil.getJedis();
        Integer continuousLoginDays = Integer.parseInt(jedis.get("logincontinus:" + userId));
        continuousLoginPromptVo.setContinuousLoginDays(continuousLoginDays);
        ResponseData<ContinuousLoginPromptVo> responseData=new ResponseData<>();
        responseData.jsonFill(1,null,continuousLoginPromptVo);
        return responseData;
    }


}
