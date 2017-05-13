package cn.edu.nju.software.vo.response;

/**
 * Created by xmc1993 on 2017/4/20.
 */
public class WxLoginResponseVo {
    private Integer id;
    private String accessToken;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
