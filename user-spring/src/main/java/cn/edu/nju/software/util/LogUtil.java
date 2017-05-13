package cn.edu.nju.software.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Author: dalec
 * Created at: 16/8/19
 */
public class LogUtil {
    public static void log(String name, Integer businessId, Integer id) {
        Logger logger = LoggerFactory.getLogger(LogUtil.class);
        Marker marker = MarkerFactory.getMarker("STATISTICS");
        logger.info(marker, name + "|" + businessId + "|" + id);
    }
}
