package cn.edu.nju.software.util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;


import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * Created by Kt on 2017/6/26.
 */
public class UnZipUtil {

    public static synchronized void unzip(String zipFileName, String extPlace) throws Exception{
        unZipFiles(zipFileName, extPlace);
    }
    /**
     * 解压zip格式的压缩文件到指定位置
     * @param zipFileName 压缩文件
     * @param extPlace 解压目录
     * @throws Exception
     */

    public static boolean unZipFiles(String zipFileName, String extPlace) throws Exception {
        System.setProperty("sun.zip.encoding", System.getProperty("sun.jnu.encoding"));
        try {
            (new File(extPlace)).mkdirs();
            File f = new File(zipFileName);
            ZipFile zipFile = new ZipFile(zipFileName,"GBK");  //处理中文文件名乱码的问题
            if((!f.exists()) && (f.length() <= 0)) {
                throw new Exception("要解压的文件不存在!");
            }
            String strPath, gbkPath, strtemp;
            File tempFile = new File(extPlace);
            //管理权限加入 可能有问题
            tempFile.setReadable(true);
            tempFile.setExecutable(true);
            strPath = tempFile.getAbsolutePath();
            Enumeration<?> e = zipFile.getEntries();
            while(e.hasMoreElements()){
                ZipEntry zipEnt = (ZipEntry) e.nextElement();
                gbkPath=zipEnt.getName();
                if(zipEnt.isDirectory()){
                    strtemp = strPath + File.separator + gbkPath;
                    File dir = new File(strtemp);
                    dir.mkdirs();
                    //管理权限加入 可能有问题
                    dir.setReadable(true);
                    dir.setExecutable(true);
                    continue;
                } else {
                    //读写文件
                    InputStream is = zipFile.getInputStream(zipEnt);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    gbkPath=zipEnt.getName();
                    strtemp = strPath + File.separator + gbkPath;
                    //建目录
                    String strsubdir = gbkPath;
                    for(int i = 0; i < strsubdir.length(); i++) {
                        if(strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {
                            String temp = strPath + File.separator + strsubdir.substring(0, i);
                            File subdir = new File(temp);
                            if(!subdir.exists())
                                subdir.mkdir();
                            //管理权限加入 可能有问题
                            subdir.setReadable(true);
                            subdir.setExecutable(true);
                        }
                    }
                    FileOutputStream fos = new FileOutputStream(strtemp);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    int c;
                    while((c = bis.read()) != -1) {
                        bos.write((byte) c);
                    }
                    bos.close();
                    fos.close();
                }
            }
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void unZip(String sourceZip,String destDir) throws Exception{

        try{

            Project p = new Project();

            Expand e = new Expand();

            e.setProject(p);

            e.setSrc(new File(sourceZip));

            e.setOverwrite(false);

            e.setDest(new File(destDir));

            /*

            ant下的zip工具默认压缩编码为UTF-8编码，

            而winRAR软件压缩是用的windows默认的GBK或者GB2312编码

            所以解压缩时要制定编码格式

            */

            e.setEncoding("UTF-8");  //根据linux系统的实际编码设置

            e.execute();

        }catch(Exception e){

            throw e;

        }

    }
}
