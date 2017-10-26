package cn.edu.nju.software.service;

import java.util.Map;

public interface WxOAuthService {

    /**
     * 获取公众号 Js-Sdk 的accessToken
     * @return
     */
    String getAccessToken();

    /**
     * 获得 jsapi_ticket
     * @return
     */
    String getTicket();

    /**
     * 获得签名信息
     * @param url
     * @return
     */
    Map<String, String> getSignature(String url);

}
