package cn.edu.nju.software.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

/**
 * Created by xmc1993 on 17/2/27.
 */
public class GetUtil {

    public static String sendGet(String url, Map<String, String> properties){
        String result = "";
        BufferedReader in = null;
        URLConnection connection;

        try {
            URL realUrl = new URL(url);
            connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //设置额外的属性
            Set<String> keys = properties.keySet();
            for (String key : keys) {
                connection.setRequestProperty(key, properties.get(key));
            }
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String line;
        try {
            while ((line = in.readLine()) != null) {
                result += (line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
