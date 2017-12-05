package cn.edu.nju.software.util;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

import java.io.File;

public class AudioUtil {
    /**
     * 格式化语音时长
     * @param path
     * @return
     */
    public static String getAudioLength(String path) {
        try {
            long length = getDuration(path);
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
        } catch (Exception e) {
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
        String audioLength = getAudioLength(tempFilePath);
        //删除临时文件
        try {
            File tempAudioFile = new File(tempFilePath);
            if (tempAudioFile != null) {
                tempAudioFile.delete();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return audioLength;
    }

    /**
     * 获得一个本地语音文件的时长
     * @param position
     * @return
     */
    public static int getDuration(String position) {
        int length = 0;
        try {
            MP3File mp3File = (MP3File) AudioFileIO.read(new File(position));
            MP3AudioHeader audioHeader = (MP3AudioHeader) mp3File.getAudioHeader();
            // 单位为秒
            length = audioHeader.getTrackLength();

            return length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }

}
