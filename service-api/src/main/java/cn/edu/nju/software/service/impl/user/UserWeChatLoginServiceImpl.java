package cn.edu.nju.software.service.impl.user;

import cn.edu.nju.software.service.user.UserWeChatLoginService;
import cn.edu.nju.software.vo.WeChatOAuthVo;
import cn.edu.nju.software.vo.WeChatUserInfoVo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Created by xmc1993 on 17/3/23.
 */
@Service
public class UserWeChatLoginServiceImpl implements UserWeChatLoginService {

    private static final Logger logger = LoggerFactory.getLogger(UserWeChatLoginServiceImpl.class);
    private final static String WE_CHAT_OAUTH_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";//第三方app OAuth地址
    private final static String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";
    private final static String WX_APPLET_OAUTH_URL = "https://api.weixin.qq.com/sns/jscode2session";//微信小程序 OAuth地址

    @Override
    public WeChatOAuthVo getAccessToken(String appId, String secret, String grantType, String code) {
        String url = WE_CHAT_OAUTH_URL + "?appid=" + appId + "&secret=" + secret + "&grant_type=" + grantType + "&code=" + code;

        URI uri = URI.create(url);
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        WeChatOAuthVo authVo = new WeChatOAuthVo();

        HttpResponse response;
        try {
            response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();

                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
                StringBuilder sb = new StringBuilder();

                for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                    sb.append(temp);
                }

                JSONObject object = new JSONObject(sb.toString().trim());

                String accessToken;
                try {
                    accessToken = object.getString("access_token");
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("第三方微信登录授权失败");
                    logger.error(object.toString());
                    return null;
                }
                authVo.setAccessToken(accessToken);
                authVo.setExpiresIn(object.getInt("expires_in"));
                authVo.setRefreshToken(object.getString("refresh_token"));
                authVo.setOpenId(object.getString("openid"));
                authVo.setScope(object.getString("scope"));
                authVo.setUnionId(object.getString("unionid"));

                return authVo;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }


    @Override
    public WeChatUserInfoVo getUserInfo(String accessToken, String openId) {

        String uri = USER_INFO_URL + "?access_token=" + accessToken + "&openid=" + openId;
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(URI.create(uri));
        try {
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                StringBuilder builder = new StringBuilder();
                for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                    builder.append(temp);
                }
                JSONObject object = new JSONObject(builder.toString().trim());

                String unionId;
                try {
                    unionId = object.getString("unionid");
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("第三方微信登录获取微信用户信息失败");
                    logger.error(object.toString());
                    return null;
                }

                WeChatUserInfoVo weChatUserInfoVo = new WeChatUserInfoVo();
                weChatUserInfoVo.setUnionId(unionId);
                weChatUserInfoVo.setNickName(object.getString("nickname"));
                weChatUserInfoVo.setCity(object.getString("city"));
                weChatUserInfoVo.setProvince(object.getString("province"));
                weChatUserInfoVo.setCountry(object.getString("country"));
                weChatUserInfoVo.setSex(object.getInt("sex"));
                weChatUserInfoVo.setOpenId(object.getString("openid"));
                weChatUserInfoVo.setHeadImgUrl(object.getString("headimgurl"));
                return weChatUserInfoVo;
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
