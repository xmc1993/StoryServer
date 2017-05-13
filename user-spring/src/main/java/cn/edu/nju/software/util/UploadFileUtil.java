package cn.edu.nju.software.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public final class UploadFileUtil {

    private static final Logger logger = LoggerFactory.getLogger(UploadFileUtil.class);

    private UploadFileUtil() {
    }

    /**
     * 获得服务器上存储项目文件的根目录
     *
     * @return
     */
    public static String getBaseUrl() {
        return System.getenv().get("STORY_HOME") != null ? System.getenv().get("STORY_HOME") : "/data";
    }

    /**
     * 获取文件的后缀
     * @param fileName
     * @return
     */
    public static  String getSuffix(String fileName){
        if (StringUtil.isEmpty(fileName)){
            return null;
        }
        String res = fileName.substring(fileName.lastIndexOf(".") + 1);
        return res;
    }


    /**
     * 将action下的临时文件拷贝到项目文件目录下
     *
     * @param uploadFile
     * @param desPath
     * @param fileName
     * @return
     */
    public static boolean mvFile(MultipartFile uploadFile, String desPath, String fileName) {
        File desFile = new File(new File(desPath), fileName);// 根据parent抽象路径名和child路径名字符串创建一个新File实例。
        if (!desFile.getParentFile().exists()) {// 判断路径"/image/#{businessId}"是否存在
            desFile.getParentFile().mkdirs();
            // 如果不存在，则创建此路径将文件保存到硬盘上，因为action运行结束后，临时文件就会自动被删除
        }
        try {
            if (!desFile.exists()) {
                desFile.createNewFile();
            }
            uploadFile.transferTo(desFile);
            desFile.setExecutable(true, false);
            desFile.setReadable(true, false);
            desFile.setWritable(true, false);
        } catch (IOException e) {
            logger.error("文件上传失败！");
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        System.out.println(getSuffix("32131"));
    }
}
