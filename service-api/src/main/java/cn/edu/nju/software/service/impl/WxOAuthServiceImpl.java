package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.service.WxOAuthService;
import cn.edu.nju.software.util.JedisUtil;
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
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Map;

@Service
public class WxOAuthServiceImpl implements WxOAuthService {
    private static Logger logger = LoggerFactory.getLogger(WxOAuthServiceImpl.class);
    private final static String JS_SDK_ACCESS_TOKEN_REDIS_KEY = "JS_SDK_ACCESS_TOKEN_REDIS_KEY";
    private final static String JS_API_TICKET_REDIS_KEY = "JS_API_TICKET";

    private final static String FETCH_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    private final static String FETCH_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

    private final static String APP_ID = "";
    private final static String APP_SECRET = "";

    @Autowired
    private RequestConfig requestConfig;
    @Autowired
    private CloseableHttpClient httpClient;

    @Override
    public String getAccessToken() {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            byte[] bytes = jedis.get(JS_SDK_ACCESS_TOKEN_REDIS_KEY.getBytes());
            if (bytes == null) {
                String accessToken = getAccessTokenFromWxApi(APP_ID, APP_SECRET);
                if (accessToken != null) {
                    jedis.set(JS_SDK_ACCESS_TOKEN_REDIS_KEY.getBytes(), accessToken.getBytes());
                    jedis.expire(JS_SDK_ACCESS_TOKEN_REDIS_KEY.getBytes(), 7170);//默认7200但-30s 保证token的有效性
                }
                return accessToken;
            }
            return String.valueOf(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("WxOAuthService -> get AccessToken failed ...\n");
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    private String getAccessTokenFromWxApi(String appId, String appSecret) {
        String url = FETCH_ACCESS_TOKEN_URL + "?grant_type=client_credential" + "&appid=" + appId + "&secret=" + appSecret;

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
                    logger.error("js sdk获取accessToken失败");
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
    public String getTicket() {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            byte[] bytes = jedis.get(JS_API_TICKET_REDIS_KEY.getBytes());
            if (bytes == null) {
                String accessToken = getAccessToken();
                if (accessToken == null) {
                    return null;
                }
                String ticket = getTicketFromWxApi(accessToken);
                if (ticket != null) {
                    jedis.set(JS_API_TICKET_REDIS_KEY.getBytes(), ticket.getBytes());
                    jedis.expire(JS_API_TICKET_REDIS_KEY.getBytes(), 7170);//默认7200但-30s 保证ticket的有效性
                }
                return ticket;
            }
            return String.valueOf(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("WxOAuthService -> get ticket failed ...\n");
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    private String getTicketFromWxApi(String accessToken) {
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
                        logger.error("js api获取ticket失败。\n");
                        logger.error(object.toString());
                        return null;
                    }
                    ticket = object.getString("ticket");
                    return ticket;
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("js api获取ticket失败。\n");
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
