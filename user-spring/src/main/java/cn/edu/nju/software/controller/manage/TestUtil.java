package cn.edu.nju.software.controller.manage;

import java.io.File;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;

/**
* @author zs
* @version 创建时间：2017年9月10日 下午7:05:57
*/

public class TestUtil {
	public static void main(String[] args) {
		String url="http://120.27.219.173/source/works/989/ElrFC2M6waK576h1.wav";
			int index = url.indexOf("/source");
	        String result = url.substring(index + "/source".length());
	        result = "/data" + result;
			File file=new File(result);
			 Encoder encoder = new Encoder();
		        MultimediaInfo m = null;
		        try {
		            m = encoder.getInfo(file);
		            long length = m.getDuration() / 1000;
		            int hours = (int) (length / 3600);
		            int minutes = (int) ((length % 3600) / 60);
		            int seconds = (int) (length % 60);
		            StringBuilder stringBuilder = new StringBuilder();
		            stringBuilder.append(hours == 0 ? "0" : String.valueOf(hours));
		            stringBuilder.append(":");
		            stringBuilder.append(minutes == 0 ? "0" : String.valueOf(minutes));
		            stringBuilder.append(":");
		            stringBuilder.append(seconds == 0 ? "0" : String.valueOf(seconds));
		            System.out.println(stringBuilder.toString());
		        } catch (EncoderException e) {
		            e.printStackTrace();
		        }

		}
	}

