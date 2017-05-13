package cn.edu.nju.software.vo;

/**
 * Created by xmc1993 on 2017/5/14.
 */
public class UserVo extends UserBaseVo {
    //预留字段
    private String mobile;//手机号
    private String company;//公司
    private String city;//城市
    private String email;//邮箱

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
