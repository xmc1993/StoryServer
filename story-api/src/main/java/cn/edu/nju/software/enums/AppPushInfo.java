package cn.edu.nju.software.enums;

/**
 * Created by xmc1993 on 16/10/12.
 */
public enum AppPushInfo {
    XINZHU("com.xinzhu.sinture", "57089300e0f55a7bb50009ae", "vn53ymftd7q4zwk3hwo0ispxicca9lb7",
            "579744ece0f55ad249003e7c", "fhorzhmttw2n7vjkmno4aqngk3pbouj8", 2),
    SHIKE("com.classeshop.train", "5756ff1267e58edc3f001b98", "pi1mtikczsmtymljtjlcakf1sob13lzz",
            "575d6d0c67e58e9bf3002147", "hnwtadpuy4extkkayxglelprwrvnvvv2", 1),
    LONGDAO("com.xinzhu.longdao", "578490a367e58ed4ad002334", "w2bbkonvzxb4n88ps3eyoihk5dksxx5t",
            "5786521c67e58eae000037ee", "mcpszfqnkthpkzjpudym38p8y0lzym8n", 4),
    GUANJIA("com.xinzhu.guanjia", "578491e1e0f55ad8e600259b", "qiiaexgw9dbykq4yxa040lsed0fxkhpz",
            "5786523be0f55a28860024f8", "c5enkhinog5o6axr7pux26vxkydcbuwa", 3),
    HOU("com.xinzhu.universitynews", "587ce46a04e2058145001ddc", "otaunhy76k0gdsobtjiwbwibn0b3bjgo",
                    "58816bd9aed17970710004d3", "tva9ktz4spva5gfusc1okageyok8dtc6", 5),
    XIANGJIA("com.xinzhu.xiangjia", "58bd1b71717c195a6e001599", "ijwwtdxlyoibzewjhc5vdiprgmlwup2s",
            "58b527b8aed17977aa000686", "pzzl1aklqb8coauoowivbksvw65zhec3", 6);
    private String appId;
    private String iosAppkey;
    private String iosAppMasterSecret;
    private String androidAppkey;
    private String androidAppMasterSecret;
    private Integer businessId;

    AppPushInfo(String appId, String iosAppkey, String iosAppMasterSecret,
                String androidAppkey, String androidAppMasterSecret, Integer businessId){
        this.appId = appId;
        this.iosAppkey =iosAppkey;
        this.iosAppMasterSecret = iosAppMasterSecret;
        this.androidAppkey = androidAppkey;
        this.androidAppMasterSecret = androidAppMasterSecret;
        this.businessId = businessId;
    }

    public static AppPushInfo getByAppId(String appId){

        for(AppPushInfo appPushInfo : values()){
            if(appPushInfo.getAppId().equals(appId)){
                return appPushInfo;
            }
        }
        throw new IllegalArgumentException("AppPushInfo bug");
    }

    public String getIosAppkey() {
        return iosAppkey;
    }

    public String getAppId() {
        return appId;
    }

    public String getIosAppMasterSecret() {
        return iosAppMasterSecret;
    }

    public String getAndroidAppkey() {
        return androidAppkey;
    }

    public String getAndroidAppMasterSecret() {
        return androidAppMasterSecret;
    }

    public Integer getBusinessId() {
        return businessId;
    }
}
