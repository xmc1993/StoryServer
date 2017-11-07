package cn.edu.nju.software.vo.response;

import lombok.Data;

@Data
public class JsSdkResponseVo {
    private String timestamp;
    private String nonceStr;
    private String signature;
    private String appId;
}
