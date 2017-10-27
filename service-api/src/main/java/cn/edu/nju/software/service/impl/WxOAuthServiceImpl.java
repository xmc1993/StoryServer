package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.entity.Business;
import cn.edu.nju.software.service.WxOAuthService;
import cn.edu.nju.software.util.JsSdkUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Map;

@Service
public class WxOAuthServiceImpl implements WxOAuthService {
    private static Logger logger = LoggerFactory.getLogger(WxOAuthServiceImpl.class);
    private final static String FETCH_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    private final static String FETCH_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

    @Autowired
    private RequestConfig requestConfig;
    @Autowired
    private CloseableHttpClient httpClient;
    @Autowired
    private Business business;

    @Override
    @Cacheable(value = "wxAccessToken", key = "'JS_SDK_ACCESS_TOKEN_REDIS_KEY'")
    public String getAccessToken() {
        String url = FETCH_ACCESS_TOKEN_URL + "?grant_type=client_credential" + "&appid=" + business.getWxPublicAccountAppId() + "&secret=" + business.getWxPublicAccountSecret();
        URI uri = URI.create(url);
        HttpGet get = new HttpGet(uri);
        get.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
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
                    logger.error("WxOAuthServiceImpl: js sdk获取accessToken失败");
                    logger.error(object.toString());
                    return null;
                }
                return accessToken;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            get.releaseConnection();
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    @Override
    @Cacheable(value = "wxAccessToken", key = "'JS_API_TICKET_REDIS_KEY'")
    public String getTicket() {
        String accessToken = getAccessToken();
        if (accessToken == null) {
            return null;
        }
        String url = FETCH_TICKET_URL + "?type=jsapi" + "&access_token=" + accessToken;
        URI uri = URI.create(url);
        HttpGet get = new HttpGet(uri);
        get.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                    sb.append(temp);
                }
                JSONObject object = new JSONObject(sb.toString().trim());
                String ticket;
                try {
                    int errCode = object.getInt("errcode");
                    if (errCode != 0) {
                        logger.error("WxOAuthServiceImpl: js api获取ticket失败。\n");
                        logger.error(object.toString());
                        return null;
                    }
                    ticket = object.getString("ticket");
                    return ticket;
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("WxOAuthServiceImpl: js api获取ticket失败。\n");
                    logger.error(object.toString());
                    return null;
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            get.releaseConnection();
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    @Override
    public Map<String, String> getSignature(String url) {
        String ticket = getTicket();
        if (ticket == null) {
            return null;
        }
        Map<String, String> sign = JsSdkUtil.sign(ticket, url);
        return sign;
    }
}
