package cn.edu.nju.software.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.List;

/**
 * Created by xmc1993 on 17/2/27.
 */
public class PostUtil {
    public static CloseableHttpResponse sendPost(String url, List<NameValuePair> nvps) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpClientContext context = new HttpClientContext();
        CloseableHttpResponse response = null;
        try {
            HttpPost post = new HttpPost(url);
            if (nvps != null) {
                post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            }
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
            post.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            post.setHeader("accept", "*/*");
            post.setHeader("connection", "Keep-Alive");
            // 执行请求用execute方法，content用来帮我们附带上额外信息

            response = httpClient.execute(post, context);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }
}
