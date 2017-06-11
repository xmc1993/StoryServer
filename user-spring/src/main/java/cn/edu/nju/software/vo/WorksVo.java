package cn.edu.nju.software.vo;

import cn.edu.nju.software.entity.Works;
import lombok.Data;

/**
 * Created by xmc1993 on 2017/5/14.
 */
@Data
public class WorksVo extends Works{
    private boolean like = false;
}
