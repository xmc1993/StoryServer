package cn.edu.nju.software.vo;

import cn.edu.nju.software.entity.Works;
import lombok.Data;

/**
 * Created by zhangsong on 2017/11/17.
 */
@Data
public class ShareWorkWithreadPlan {

    private Integer day;

    private Works works;

    private String userName;

}

