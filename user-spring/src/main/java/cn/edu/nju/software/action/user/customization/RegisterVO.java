package cn.edu.nju.software.action.user.customization;

import java.util.Objects;

/**
 * Author: dalec
 * Created at: 16/8/25
 */
public class RegisterVO {
    private String name;
    private String city_1;
    private String city_2;
    private String school;
    private String college;
    private String graduateTime;
    private String email;
    private String adv_desire;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity_1() {
        return city_1;
    }

    public void setCity_1(String city_1) {
        this.city_1 = city_1;
    }

    public String getCity_2() {
        return city_2;
    }

    public void setCity_2(String city_2) {
        this.city_2 = city_2;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getGraduateTime() {
        return graduateTime;
    }

    public void setGraduateTime(String graduateTime) {
        this.graduateTime = graduateTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdv_desire() {
        return adv_desire;
    }

    public void setAdv_desire(String adv_desire) {
        this.adv_desire = adv_desire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterVO that = (RegisterVO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(city_1, that.city_1) &&
                Objects.equals(city_2, that.city_2) &&
                Objects.equals(school, that.school) &&
                Objects.equals(college, that.college) &&
                Objects.equals(graduateTime, that.graduateTime) &&
                Objects.equals(email, that.email) &&
                Objects.equals(adv_desire, that.adv_desire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city_1, city_2, school, college, graduateTime, email, adv_desire);
    }

    @Override
    public String toString() {
        return "RegisterVO{" +
                "name='" + name + '\'' +
                ", city_1='" + city_1 + '\'' +
                ", city_2='" + city_2 + '\'' +
                ", school='" + school + '\'' +
                ", college='" + college + '\'' +
                ", graduateTime='" + graduateTime + '\'' +
                ", email='" + email + '\'' +
                ", adv_desire='" + adv_desire + '\'' +
                '}';
    }
}
