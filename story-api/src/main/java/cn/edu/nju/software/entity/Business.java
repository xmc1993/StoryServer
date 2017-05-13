package cn.edu.nju.software.entity;

import java.io.Serializable;
import java.util.Date;

/**
 */
public class Business implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4166473876462325421L;
	private Integer id;
	private String username;
	private String realname;
	private String password;
	private String appId;
	private String appName;
	private String mobile;
	private String telephone;
	private String location;
	private String logo;
	private String token;
	private String accessKey;
	private String secretKey;
	private String imageBucket;
	private String videoBucket;
	private String pdfBucket;
	private String messageUsername;
    private String messageSecret;
	private String landingPage;// 启动页
	private String videoDomain;
	private String imageDomain;
	private String pdfDomain;
	private String liveDomain;
	private String liveBucket;
	private Integer validation = 0;
	private Integer isActive = 0;// 是否能够使用
	private Date createTime = new Date();
	private Date updateTime = new Date();
	private String website;// 客户的域名

    //Add on 2016/7/22
    private String txAppCenterUrl;
    private String appStoreUrl;

    //Add on 2016/11/02 for admin
    private String linkman;
    private Date startTime;
    private Date deadline;
    
    //Add on 2016/12/17
    private String rongCloudAppKey;//融云的appkey
	private String rongCloudAppppSecret ;//融云匹配上面key的secret
	
	//音频
	private String audioBucket;
	private String audioDomain;

    //Add on 2016/02/27
    private String messagePassword;//书迷短信服务的密码 采用Base64进行加密 可使用Base64Util类来加密和解密
    private String iosAppkey;
    private String iosAppMasterSecret;
    private String androidAppkey;
    private String androidAppMasterSecret;

    private String weChatAppId;
    private String weChatSecret;
    private String wxAppletAppId;//微信小程序appId
    private String wxAppletSecret;//微信小程序secret
    private String mchId;//微信支付的商户Id
    private String mchKey;//微信支付的商户Key

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getImageBucket() {
        return imageBucket;
    }

    public void setImageBucket(String imageBucket) {
        this.imageBucket = imageBucket;
    }

    public String getVideoBucket() {
        return videoBucket;
    }

    public void setVideoBucket(String videoBucket) {
        this.videoBucket = videoBucket;
    }

    public String getMessageUsername() {
        return messageUsername;
    }

    public void setMessageUsername(String messageUsername) {
        this.messageUsername = messageUsername;
    }

    public String getMessageSecret() {
        return messageSecret;
    }

    public void setMessageSecret(String messageSecret) {
        this.messageSecret = messageSecret;
    }

    public String getLandingPage() {
        return landingPage;
    }

    public void setLandingPage(String landingPage) {
        this.landingPage = landingPage;
    }

    public String getVideoDomain() {
        return videoDomain;
    }

    public void setVideoDomain(String videoDomain) {
        this.videoDomain = videoDomain;
    }

    public String getImageDomain() {
        return imageDomain;
    }

    public void setImageDomain(String imageDomain) {
        this.imageDomain = imageDomain;
    }

    public Integer getValidation() {
        return validation;
    }

    public void setValidation(Integer validation) {
        this.validation = validation;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTxAppCenterUrl() {
        return txAppCenterUrl;
    }

    public void setTxAppCenterUrl(String txAppCenterUrl) {
        this.txAppCenterUrl = txAppCenterUrl;
    }

    public String getAppStoreUrl() {
        return appStoreUrl;
    }

    public void setAppStoreUrl(String appStoreUrl) {
        this.appStoreUrl = appStoreUrl;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
	public String getPdfBucket() {
		return pdfBucket;
	}

	public void setPdfBucket(String pdfBucket) {
		this.pdfBucket = pdfBucket;
	}

	public String getPdfDomain() {
		return pdfDomain;
	}

	public void setPdfDomain(String pdfDomain) {
		this.pdfDomain = pdfDomain;
	}

    public String getMessagePassword() {
        return messagePassword;
    }

    public void setMessagePassword(String messagePassword) {
        this.messagePassword = messagePassword;
    }

    public String getIosAppMasterSecret() {
        return iosAppMasterSecret;
    }

    public void setIosAppMasterSecret(String iosAppMasterSecret) {
        this.iosAppMasterSecret = iosAppMasterSecret;
    }

    public String getIosAppkey() {
        return iosAppkey;
    }

    public void setIosAppkey(String iosAppkey) {
        this.iosAppkey = iosAppkey;
    }

    public String getAndroidAppkey() {
        return androidAppkey;
    }

    public void setAndroidAppkey(String androidAppkey) {
        this.androidAppkey = androidAppkey;
    }

    public String getAndroidAppMasterSecret() {
        return androidAppMasterSecret;
    }

    public void setAndroidAppMasterSecret(String androidAppMasterSecret) {
        this.androidAppMasterSecret = androidAppMasterSecret;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Business other = (Business) obj;
		if (mobile == null) {
			if (other.mobile != null) return false;
		}
		else if (!mobile.equals(other.mobile)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "Business [id=" + id + ", username=" + username + ", realname=" + realname + ", password=" + password
				+ ", appId=" + appId + ", appName=" + appName + ", mobile=" + mobile + ", telephone=" + telephone
				+ ", location=" + location + ", logo=" + logo + ", token=" + token + ", accessKey=" + accessKey
				+ ", secretKey=" + secretKey + ", imageBucket=" + imageBucket + ", videoBucket=" + videoBucket
				+ ", landingPage=" + landingPage + ", videoDomain=" + videoDomain + ", imageDomain=" + imageDomain
				+ ", validation=" + validation + ", isActive=" + isActive + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", website=" + website + "]";
	}

	public String getRongCloudAppKey() {
		return rongCloudAppKey;
	}

	public void setRongCloudAppKey(String rongCloudAppKey) {
		this.rongCloudAppKey = rongCloudAppKey;
	}

	public String getRongCloudAppppSecret() {
		return rongCloudAppppSecret;
	}

	public void setRongCloudAppppSecret(String rongCloudAppppSecret) {
		this.rongCloudAppppSecret = rongCloudAppppSecret;
	}

	public String getLiveDomain() {
		return liveDomain;
	}

	public void setLiveDomain(String liveDomain) {
		this.liveDomain = liveDomain;
	}

	public String getLiveBucket() {
		return liveBucket;
	}

	public void setLiveBucket(String liveBucket) {
		this.liveBucket = liveBucket;
	}

	public String getAudioBucket() {
		return audioBucket;
	}

	public void setAudioBucket(String audioBucket) {
		this.audioBucket = audioBucket;
	}

	public String getAudioDomain() {
		return audioDomain;
	}

	public void setAudioDomain(String audioDomain) {
		this.audioDomain = audioDomain;
	}

    public String getWeChatAppId() {
        return weChatAppId;
    }

    public void setWeChatAppId(String weChatAppId) {
        this.weChatAppId = weChatAppId;
    }

    public String getWeChatSecret() {
        return weChatSecret;
    }

    public void setWeChatSecret(String weChatSecret) {
        this.weChatSecret = weChatSecret;
    }

    public String getWxAppletAppId() {
        return wxAppletAppId;
    }

    public void setWxAppletAppId(String wxAppletAppId) {
        this.wxAppletAppId = wxAppletAppId;
    }

    public String getWxAppletSecret() {
        return wxAppletSecret;
    }

    public void setWxAppletSecret(String wxAppletSecret) {
        this.wxAppletSecret = wxAppletSecret;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }
}
