package cn.edu.nju.software.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/17.
 */
public class Const {
    public static final Integer DEFAULT_OFFSET = 0;
    public static final Integer DEFAULT_LIMIT = 10;

    public static final Integer WARM_COMPANY_BADGE_ID = 15;//温馨陪伴
    public static final Integer PABULUN_BADGE_ID = 16;//精神食粮
    public static final Integer MAGIC_KNIGHT_BADGE_ID = 17;//魔法骑士
    public static final Integer PATRIOT_BADGE_ID = 18;//小小爱国者
    public static final Integer FANTASY_STORY_TAG_ID = 100051;//奇幻故事的标签ID

    public static void main(String[] args) {
        Date now = new Date();

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String format = sf.format(date);
        System.out.println("20171001".equals(format));
        System.out.println("20170902".equals(format));
        System.out.println(format);
    }
}
