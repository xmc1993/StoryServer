package cn.edu.nju.software.util;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;

public class AudioUtil {
    /**
     * 获得一个音频文件的长度
     * @param file
     * @return
     */
    public static String getAudioLength(File file) {
        Encoder encoder = new Encoder();
        MultimediaInfo multimediaInfo;
        try {
            multimediaInfo = encoder.getInfo(file);
            long length = multimediaInfo.getDuration() / 1000;
            int hours = (int) (length / 3600);
            int minutes = (int) ((length % 3600) / 60);
            int seconds = (int) (length % 60);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(hours == 0 ? "0" : String.valueOf(hours));
            stringBuilder.append(":");
            stringBuilder.append(minutes == 0 ? "0" : String.valueOf(minutes));
            stringBuilder.append(":");
            stringBuilder.append(seconds == 0 ? "0" : String.valueOf(seconds));
            String timeInfo = stringBuilder.toString();
            return timeInfo;
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得一个url资源对应音频的长度
     * @param url
     * @return
     */
    public static String getAudioLengthFromUrl(String url) {
        String tempFilePath = DownloadUtil.getTempFile(url);
        if (tempFilePath == null){
            return null;
        }
        File tempAudioFile = new File(url);
        String audioLength = getAudioLength(tempAudioFile);
        //删除临时文件
        if (tempAudioFile != null){
            tempAudioFile.delete();
        }
        return audioLength;
    }
}
