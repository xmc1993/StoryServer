package cn.edu.nju.software.controller.oAuth;

import cn.edu.nju.software.entity.Business;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.WxOAuthService;
import cn.edu.nju.software.vo.response.JsSdkResponseVo;
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
import java.util.Map;

@Api("tag controller")
@Controller
@RequestMapping("/mp")
public class WxOAuthController {
    @Autowired
    private WxOAuthService wxOAuthService;
    @Autowired
    private Business business;

    @ApiOperation(value = "获取Js-Sdk的签名")
    @RequestMapping(value = "/getJsSdkSignature", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<JsSdkResponseVo> getJsSdkSignature(
            @ApiParam("需要生成签名的网页URL,由前端生成") @RequestParam("url") String url,
            HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        ResponseData<JsSdkResponseVo> responseData = new ResponseData();
        Map<String, String> signature = wxOAuthService.getSignature(url);
        if (signature == null) {
            responseData.jsonFill(2, "生成签名失败。", null);
            return responseData;
        }
        JsSdkResponseVo jsSdkResponseVo = new JsSdkResponseVo();
        jsSdkResponseVo.setNonceStr(signature.get("nonceStr"));
        jsSdkResponseVo.setSignature(signature.get("signature"));
        jsSdkResponseVo.setTimestamp(signature.get("timestamp"));
        jsSdkResponseVo.setAppId(business.getWxPublicAccountAppId());
        responseData.jsonFill(1, null, jsSdkResponseVo);
        return responseData;
    }

}
