package cn.edu.nju.software.vo.response;

import lombok.Data;

import java.util.List;

/**
 * Created by xmc1993 on 2017/4/20.
 */
@Data
public class LoginResponseVo {
    private Integer id;
    private String accessToken;
    private List<Integer> powerCodeList;

}
