package cn.edu.nju.software.util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


/**
 * Created by Kt on 2017/6/27.
 */
public class AntZipUtil {
    // //////////////////////////解压缩////////////////////////////////////////
    /**
     * 调用org.apache.tools.zip实现解压缩，支持目录嵌套和中文名
     * 也可以使用java.util.zip不过如果是中文的话，解压缩的时候文件名字会是乱码。原因是解压缩软件的编码格式跟java.util.zip.ZipInputStream的编码字符集(固定是UTF-8)不同
     *
     * @param zipFileName
     *            要解压缩的文件
     * @param outputDirectory
     *            要解压到的目录
     * @throws Exception
     */
    public static boolean unZip(String zipFileName, String outputDirectory) {
        boolean flag = false;
        try {
            org.apache.tools.zip.ZipFile zipFile = new org.apache.tools.zip.ZipFile(
                    zipFileName);
            java.util.Enumeration e = zipFile.getEntries();
            org.apache.tools.zip.ZipEntry zipEntry = null;
            createDirectory(outputDirectory, "");
            while (e.hasMoreElements()) {
                zipEntry = (org.apache.tools.zip.ZipEntry) e.nextElement();
                if (zipEntry.isDirectory()) {
                    String name = zipEntry.getName();
                    name = name.substring(0, name.length() - 1);
                    File f = new File(outputDirectory + File.separator + name);
                    f.mkdir();
                    f.setExecutable(true,false);
                    f.setReadable(true,false);
                } else {
                    String fileName = zipEntry.getName();
                    fileName = fileName.replace('\\', '/');
                    if (fileName.indexOf("/") != -1) {
                        createDirectory(outputDirectory, fileName.substring(0,
                                fileName.lastIndexOf("/")));
                        fileName = fileName.substring(
                                fileName.lastIndexOf("/") + 1, fileName
                                        .length());
                    }

                    File f = new File(outputDirectory + File.separator
                            + zipEntry.getName());

                    f.createNewFile();
                    f.setReadable(true,false);
                    f.setExecutable(true,false);
                    InputStream in = zipFile.getInputStream(zipEntry);
                    FileOutputStream out = new FileOutputStream(f);

                    byte[] by = new byte[1024];
                    int c;
                    while ((c = in.read(by)) != -1) {
                        out.write(by, 0, c);
                    }
                    out.close();
                    in.close();
                }
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    /**
     * 创建目录
     *
     * @param directory
     *            父目录
     * @param subDirectory
     *            子目录
     */
    private static void createDirectory(String directory, String subDirectory) {
        String dir[];
        File fl = new File(directory);
        try {
            if (subDirectory == "" && fl.exists() != true){
                fl.mkdir();
                fl.setExecutable(true,false);
                fl.setReadable(true,false);
            }
            else if (subDirectory != "") {
                dir = subDirectory.replace('\\', '/').split("/");
                for (int i = 0; i < dir.length; i++) {
                    File subFile = new File(directory + File.separator + dir[i]);
                    if (subFile.exists() == false){
                        subFile.mkdir();
                        subFile.setExecutable(true,false);
                        subFile.setReadable(true,false);
                    }

                    directory += File.separator + dir[i];
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // /////////////////////////////////////

}
