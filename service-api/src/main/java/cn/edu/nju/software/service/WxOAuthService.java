package cn.edu.nju.software.service;

import java.util.Map;

public interface WxOAuthService {

    String getAccessToken();

    String getTicket();

    Map<String, String> getSignature(String url);
}
