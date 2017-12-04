package cn.edu.nju.software.util;

public class OSSUtil {
    /**
     * 将普通的url资源转存到oss服务器
     *
     * @param url
     * @return
     */
    public static String urlToOss(String url) {
        if (url == null) return null;
        OSSClientUtil oss = new OSSClientUtil();
        String name = oss.uploadFile2OSS(url);
        return oss.getUrl(name);

    }

    public static String urlToOss2(String url) {
        if (url == null) return null;
        String tempFile = DownloadUtil.getTempFile(url);

        OSSClientUtil oss = new OSSClientUtil();
        oss.uploadImg2Oss(tempFile);
        return oss.getUrl(tempFile);
    }

    public static void main(String[] args) {
        String result = urlToOss2("http://www.warmtale.com/source/cover/GLr5uNpdBkUDetcX.jpg");
        System.out.println(result);
    }
}
