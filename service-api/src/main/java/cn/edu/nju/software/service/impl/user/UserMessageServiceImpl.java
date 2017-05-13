package cn.edu.nju.software.service.impl.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.edu.nju.software.service.user.UserMessageService;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@Service
public class UserMessageServiceImpl implements UserMessageService {
	private final static String header = "验证码：";
	private final static String tailer = "，请勿转发。";
	private final static String username = "18658895709";
    private static final String secret = "fe6e14729d1e4a428696aaeddec52e22";

	@Override
	public boolean sendMessage(String phoneNumber, String code) {
        String url = "http://sms.shumitech.com/api/send?" +
                "username=" + username + "&" +
                "user_secret=" + secret;
        return sendMessage(url, phoneNumber, code);
	}

	@Override
	public boolean sendMessageByBusiness(String username, String secret, String phoneNumber, String code) {
        if (username == null || secret == null) {
            return sendMessage(phoneNumber, code);
        }
        String url = "http://sms.shumitech.com/api/send?" +
                "username=" + username + "&" +
                "user_secret=" + secret;
        return sendMessage(url, phoneNumber, code);
	}

    private boolean sendMessage(String url, String phoneNumber, String code) {
        String result = "";
        BufferedReader in;
        StringBuilder sb = new StringBuilder(url);
        sb.append("&mobile=");
        sb.append(phoneNumber);
        sb.append("&content=");
        try {
            sb.append(URLEncoder.encode(header, "UTF-8"));
            sb.append(code);
            sb.append(URLEncoder.encode(tailer, "UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String urlNameStr = sb.toString();
        try {
            URL realUrl = new URL(urlNameStr);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        String line;
        try {
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
            JSONObject jsonObject = JSON.parseObject(result);
            if (jsonObject.getIntValue("code") == 0) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

}
