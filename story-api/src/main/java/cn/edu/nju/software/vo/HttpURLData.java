package cn.edu.nju.software.vo;

import org.springframework.stereotype.Component;

/**
 * Created by fenggang on 10/11/16.
 */
@Component
public class HttpURLData {

    public String getHttpurl(int businessId) {
        switch (businessId){
            case 1 : return shikeUrl;
            case 2 : return xinzhuUrl;
            default: return shikeUrl;
        }
    }

    public String getXinzhuUrl() {
        return xinzhuUrl;
    }

    public void setXinzhuUrl(String xinzhuUrl) {
        this.xinzhuUrl = xinzhuUrl;
    }

    public String getShikeUrl() {
        return shikeUrl;
    }

    public void setShikeUrl(String shikeUrl) {
        this.shikeUrl = shikeUrl;
    }

    private String xinzhuUrl = "http://www.sinture.com/xinzhu";
    private String shikeUrl = "http://www.classeshop.com/xinzhu";
}
