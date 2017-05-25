package cn.edu.nju.software.service.user;

import cn.edu.nju.software.vo.WeChatOAuthVo;
import cn.edu.nju.software.vo.WeChatUserInfoVo;

/**
 * Created by xmc1993 on 17/3/23.
 */
public interface UserWeChatLoginService {

    /**
     * 使用授权码从OAuth服务器获得accessToken
     * @param code
     * @return
     */
    WeChatOAuthVo getAccessToken(String appId, String secret, String grantType, String code);

    /**
     *
     * @param accessToken
     * @param openId
     */
    WeChatUserInfoVo getUserInfo(String accessToken, String openId);


}
