package cn.edu.nju.software.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by xmc1993 on 2017/4/11.
 */
public class DownloadUtil {
    private static final Logger logger = LoggerFactory.getLogger(DownloadUtil.class);

//    public static void main(String[] args) throws Exception {
//        //test
//        download("http://ui.51bi.com/opt/siteimg/images/fanbei0923/Mid_07.jpg", "51bi.gif","/Users/xmc1993");
//    }

    public static void download(String urlString, String filename, String savePath) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为5s
        con.setConnectTimeout(5 * 1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath() + "/" + filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        sf.setExecutable(true, false);
        sf.setReadable(true, false);
        sf.setWritable(true, false);
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }

    /**
     * 下载得到临时文件
     *
     * @param urlString
     * @return
     */
    public static String getTempFile(String urlString) {
        if (urlString == null) {
            return null;
        }
        long currentTimeMillis = System.currentTimeMillis();
        String fileName = getFileName(urlString);
        fileName = currentTimeMillis + fileName;

        try {
            download(urlString, fileName, "/tmp/");
        } catch (Exception e) {
            logger.error("临时文件下载失败!");
            e.printStackTrace();
            return null;
        }
        return "/tmp/" + fileName;
    }

    /**
     * 获得资源uri中的文件名
     *
     * @param uri
     * @return
     */
    private static String getFileName(String uri) {
        if (uri == null) return null;
        String[] segments = uri.split("/");
        return segments[segments.length - 1];
    }
}
