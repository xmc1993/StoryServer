package cn.edu.nju.software.util;

import java.io.File;

public class OSSUtil {
    /**
     * 将普通的url资源转存到oss服务器
     *
     * @param url
     * @return
     */
    public static String urlToOss(String url) {
        if (url == null) return null;
        String tempFilePath = DownloadUtil.getTempFile(url);
        return localPathToOss(tempFilePath);
    }

    /**
     * 将本地文件转存至oss
     * @param localPath
     * @return
     */
    public static String localPathToOss(String localPath) {
        //文件转存oss
        OSSClientUtil oss = new OSSClientUtil();
        String result = oss.uploadFile2Oss(localPath);
        oss.destory();
        //删除临时文件
        try {
            File tempFile = new File(localPath);
            tempFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
