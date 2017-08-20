package cn.edu.nju.software.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by xmc1993 on 2017/8/2.
 */
@Data
public class UploadResVo {
    private String url;
    private List<String> multiUrls;
}
